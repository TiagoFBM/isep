package fabrica.scm.messagesmanagement.domain;

import eapli.framework.strings.util.StringPredicates;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class ProductionMessage extends Message {

    @OneToOne
    private Product product;

    private Quantity quantity;

    private AlfanumericCode lotID;

    protected ProductionMessage() {
    }

    public ProductionMessage(String sendDate, Machine machine, Product product, int quantity, String lotID) {
        super(sendDate, machine);

        if(!verifyFullValidProductionMessage(product, quantity, lotID)){
            throw new IllegalArgumentException("Invalid Production Message!");
        }

        this.product = product;
        this.quantity = new Quantity(quantity);
        this.lotID = AlfanumericCode.valueOf(lotID);
    }

    public ProductionMessage(String sendDate, Machine machine, Product product, int quantity) {
        super(sendDate, machine);

        if(!verifValidProductionMessageWhithoutRequest(product, quantity)){
            throw new IllegalArgumentException("Invalid Production Message!");
        }

        this.product = product;
        this.quantity = new Quantity(quantity);
        this.lotID = null;
    }

    /*
    O produto e a respetiva quantidade constam sempre na mensagem. Opcionalmente, pode constar informação sobre o lote;
    */

    private boolean verifyFullValidProductionMessage(Product product, int quantity, String lotID){
        return product!=null && quantity>0 && !StringPredicates.isNullOrEmpty(lotID);
    }

    private boolean verifValidProductionMessageWhithoutRequest(Product product, int quantity){
        return product!=null && quantity>0;
    }

    public AlfanumericCode obtainAllotment() {
        return lotID;
    }

    public Quantity obtainQuantity() {
        return quantity;
    }

    public Product obtainProduct() {
        return product;
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        Product p = this.obtainProduct();
        ErrorNotification error;
        if ((error = verifyMessage(currentPO, p)) != null) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }
        Production pd;
        AlfanumericCode allotment = this.obtainAllotment();
        Quantity quantity = this.obtainQuantity();
        if (allotment != null) {
            pd = new Production(this.obtainProduct(), quantity.obtainValor(), quantity.obtainUnity().name(), allotment.toString());
        } else {
            pd = new Production(this.obtainProduct(), quantity.obtainValor(), quantity.obtainUnity().name());
        }
        currentPO.obtainPOReport().addProduction(pd);
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }

    private ErrorNotification verifyMessage(ProductionOrder currentPO, Product p) {
        ErrorNotification error = null;
        if (currentPO.obtainProduct().obtainProductionSheet().productionSheetLinesHasCode(p.identity())) {
            error = new ErrorNotification(this.obtainMachine(), ErrorType.PRODUCED_PRODUCT_NOT_IN_PRODUCTION_SHEET, this.obtainDate(), this.identity(), this.getClass());
        } else if (currentPO.obtainPOReport().hasConsumptions()) {
            error = new ErrorNotification(this.obtainMachine(), ErrorType.PRODUCTION_WITHOUT_CONSUMPTION, this.obtainDate(), this.identity(), this.getClass());
        }
        return error;
    }
}
