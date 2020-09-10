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
public class ConsumptionMessage extends Message {

    @OneToOne
    private RawMaterial rawMaterial;

    @OneToOne
    private Product product;

    @OneToOne
    private Deposit deposit;

    private Quantity quantity;

    protected ConsumptionMessage() {
    }

    public ConsumptionMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity, Deposit deposit) {
        super(sendDate, machine);

        if(!verifyFullValidConsuptionMessage(rawMaterial, quantity,deposit)){
            throw new IllegalArgumentException("Invalid Consumption Message!");
        }

        this.rawMaterial = rawMaterial;
        this.product = null;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;
    }

    public ConsumptionMessage(String sendDate, Machine machine, Product product, int quantity, Deposit deposit) {
        super(sendDate, machine);

        if(!verifyFullValidConsuptionMessage(product, quantity,deposit)){
            throw new IllegalArgumentException("Invalid Consumption Message!");
        }

        this.rawMaterial = null;
        this.product = product;
        this.quantity = new Quantity(quantity);
        this.deposit = deposit;
    }


    public ConsumptionMessage(String sendDate, Machine machine, RawMaterial rawMaterial, int quantity) {
        super(sendDate, machine);

        if(!verifyValidConsuptionMessageWithoutDeposit(rawMaterial, quantity)){
            throw new IllegalArgumentException("Invalid Consumption Message!");
        }

        this.rawMaterial = rawMaterial;
        this.product = null;
        this.quantity = new Quantity(quantity);
        this.deposit = null;
    }

    public ConsumptionMessage(String sendDate, Machine machine, Product product, int quantity) {
        super(sendDate, machine);

        if(!verifyValidConsuptionMessageWithoutDeposit(product, quantity)){
            throw new IllegalArgumentException("Invalid Consumption Message!");
        }

        this.rawMaterial = null;
        this.product = product;
        this.quantity = new Quantity(quantity);
        this.deposit = null;
    }


    /*
    Quando a origem é um depósito, a identificação do mesmo também consta na mensagem. Caso contrário, nenhuma máquina é identificada;
    */

    private boolean verifyFullValidConsuptionMessage(RawMaterial rawMaterial, int quantity, Deposit deposit){
        return rawMaterial!=null && quantity>0 && deposit!=null;
    }

    private boolean verifyFullValidConsuptionMessage(Product product, int quantity, Deposit deposit){
        return product!=null && quantity>0 && deposit!=null;
    }

    private boolean verifyValidConsuptionMessageWithoutDeposit(RawMaterial rawMaterial, int quantity){
        return rawMaterial!=null && quantity>0;
    }

    private boolean verifyValidConsuptionMessageWithoutDeposit(Product product, int quantity){
        return product!=null && quantity>0;
    }

    public Quantity obtainQuantity() {
        return this.quantity;
    }

    public Deposit obtainDeposit() {
        return deposit;
    }

    public RawMaterial obtainRawMaterial() {
        return rawMaterial;
    }

    public Product obtainProduct() {
        return product;
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        Consumption consumption;
        Quantity q = this.obtainQuantity();
        Deposit d = this.obtainDeposit();
        RawMaterial rm = this.obtainRawMaterial();
        Product p = this.obtainProduct();

        boolean flag;
        if (rm != null) {
            flag = currentPO.obtainProduct().obtainProductionSheet().productionSheetLinesHasCode(rm.identity());
        } else {
            flag = currentPO.obtainProduct().obtainProductionSheet().productionSheetLinesHasCode(p.identity());
        }
        if (!flag) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            ErrorNotification error = new ErrorNotification(this.obtainMachine(), ErrorType.CONSUMED_PRODUCT_NOT_IN_PRODUCTION_SHEET, this.obtainDate(), this.identity(), this.getClass());
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }

        if (d != null) {
            if (rm == null) {
                consumption = new Consumption(p, q.obtainValor(), q.obtainUnity().name(), this.obtainDate(), d);
            } else {
                consumption = new Consumption(rm, q.obtainValor(), q.obtainUnity().name(), this.obtainDate(), d);
            }
        } else {
            if (rm == null) {
                consumption = new Consumption(p, q.obtainValor(), q.obtainUnity().name(), this.obtainDate());
            } else {
                consumption = new Consumption(rm, q.obtainValor(), q.obtainUnity().name(), this.obtainDate());
            }
        }
        currentPO.obtainPOReport().addConsumption(consumption);
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }
}
