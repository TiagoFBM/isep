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
public class ProductionDeliveryMessage extends Message {

    @OneToOne
    private Product product;

    @OneToOne
    private Deposit deposit;

    private Quantity quantity;

    private AlfanumericCode allotment;

    protected ProductionDeliveryMessage() {
    }

    public ProductionDeliveryMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit, String lotID) {
        super(sendDate, machine);

        if (!verifyFullValidProductionDeliveryMessage(product, quantity, deposit,lotID)){
            throw new IllegalArgumentException("Product Delivery Message Invalid!");
        }

        this.product = product;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;
        this.allotment = AlfanumericCode.valueOf(lotID);
    }

    public ProductionDeliveryMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        super(sendDate, machine);

        if (!verifyValidProductionDeliveryMessageWithoutDeposit(product, quantity, deposit)){
            throw new IllegalArgumentException("Product Delivery Message Invalid!");
        }

        this.product = product;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;
        this.allotment = null;
    }

    /*
    O produto e a respetiva quantidade constam sempre na mensagem. Opcionalmente, pode constar informação sobre o lote;
    */

    private boolean verifyFullValidProductionDeliveryMessage(Product product, int quantity, Deposit deposit, String lotID){
        return product!=null && quantity>0 && deposit!=null && !StringPredicates.isNullOrEmpty(lotID);
    }

    private boolean verifyValidProductionDeliveryMessageWithoutDeposit(Product product, int quantity, Deposit deposit){
        return product!=null && quantity>0 && deposit!=null;
    }

    public AlfanumericCode obtainAllotment() {
        return this.allotment;
    }

    public Product obtainProduct() {
        return this.product;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }

    public Deposit obtainDeposit() {
        return this.deposit;
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();
        ProductionDelivery pd;
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());

        AlfanumericCode allotment = this.obtainAllotment();
        Quantity quantity = this.obtainQuantity();
        ErrorNotification error;
        if ((error = verifyMessage(currentPO)) != null) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }
        if (allotment != null) {
            pd = new ProductionDelivery(this.obtainDeposit(), this.obtainProduct(), quantity.obtainValor(), quantity.obtainUnity().name(), allotment.toString());
        } else {
            pd = new ProductionDelivery(this.obtainDeposit(), this.obtainProduct(), quantity.obtainValor(), quantity.obtainUnity().name());
        }
        currentPO.obtainPOReport().addProductionDelivery(pd);
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }

    private ErrorNotification verifyMessage(ProductionOrder currentPO) {
        ErrorNotification error = null;
        if (currentPO.obtainPOReport().calculateProductionDeliveryDifference(this.obtainQuantity()) < 0) {
            error = new ErrorNotification(this.obtainMachine(), ErrorType.PRODUCTION_DELIVERY_MORE_THAN_PRODUCTION, this.obtainDate(), this.identity(), this.getClass());
        }

        return error;
    }
}
