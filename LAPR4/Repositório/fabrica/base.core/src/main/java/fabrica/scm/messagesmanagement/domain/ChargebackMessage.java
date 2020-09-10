package fabrica.scm.messagesmanagement.domain;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.Product;
import fabrica.production.domain.Quantity;
import fabrica.production.domain.RawMaterial;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class ChargebackMessage extends Message {

    @OneToOne
    private RawMaterial rawMaterial;

    @OneToOne
    private Product product;

    @OneToOne
    private Deposit deposit;

    private Quantity quantity;

    protected ChargebackMessage() {
    }

    public ChargebackMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity, Deposit deposit) {
        super(sendDate, machine);

        if (!verifuValidChargebackMessage(rawMaterial, quantity, deposit)){
            throw new IllegalArgumentException("Chargeback Message Invalid!");
        }

        this.rawMaterial = rawMaterial;
        this.product = null;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;
    }

    public ChargebackMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        super(sendDate, machine);

        if (!verifuValidChargebackMessage(product, quantity, deposit)){
            throw new IllegalArgumentException("Chargeback Message Invalid!");
        }

        this.rawMaterial = null;
        this.product = product;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;

    }

    /*
    A matéria-prima e a respetiva quantidade bem como a identificação do depósito constam sempre na mensagem.
    */

    private boolean verifuValidChargebackMessage(RawMaterial rawMaterial, int quantity, Deposit deposit){
        return rawMaterial!=null && quantity>0 && deposit!=null;
    }

    private boolean verifuValidChargebackMessage(Product product, int quantity, Deposit deposit){
        return product!=null && quantity>0 && deposit!=null;
    }

    public RawMaterial obtainRawMaterial() {
        return this.rawMaterial;
    }

    public Product obtainProduct() {
        return this.product;
    }

    public Deposit obtainDeposit() {
        return this.deposit;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        Product p = this.obtainProduct();
        Quantity q = this.obtainQuantity();
        ErrorNotification error;
        Chargeback cb;
        if ((error = verifyMessage(currentPO)) != null) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }
        if (p != null) {
            cb = new Chargeback(p, this.obtainDeposit(), q.obtainValor(), q.obtainUnity().name());
        } else {
            cb = new Chargeback(this.obtainRawMaterial(), this.obtainDeposit(), q.obtainValor(), q.obtainUnity().name());
        }

        currentPO.obtainPOReport().addChargeback(cb);
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }

    private ErrorNotification verifyMessage(ProductionOrder currentPO) {
        ErrorNotification error = null;
        if (currentPO.obtainPOReport().calculateChargebackConsumptionDifference(this.obtainQuantity()) < 0) {
            error = new ErrorNotification(this.obtainMachine(), ErrorType.CHARGEBACK_MORE_THAN_CONSUMPTION, this.obtainDate(), this.identity(), this.getClass());
        }

        return error;
    }

}
