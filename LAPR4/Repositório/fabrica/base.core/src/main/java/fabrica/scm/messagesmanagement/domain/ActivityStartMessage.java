package fabrica.scm.messagesmanagement.domain;

import eapli.framework.general.domain.model.Designation;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.MachineStatus;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class ActivityStartMessage extends Message{

    @ManyToOne
    private ProductionOrder productionOrder;

    protected ActivityStartMessage() {
    }

    public ActivityStartMessage(String sendDate, Machine machine) {
        super(sendDate, machine);
    }

    public ActivityStartMessage(String sendDate, Machine machine, ProductionOrder productionOrder) {
        super(sendDate, machine);

        if(!verifyProductionOrderStartMessage(productionOrder)){
            throw new IllegalArgumentException("Activity Start Message Invalid!");
        }

        this.productionOrder = productionOrder;
    }

    public ProductionOrder obtainProductionOrder() {
        return this.productionOrder;
    }

    /*
    Neste caso o contexto, é sempre uma ordem de produção. A informação referente ao contexto nem sempre consta na mensagem enviada pela máquina em causa;
    */

    private boolean verifyProductionOrderStartMessage(ProductionOrder productionOrder){
        return productionOrder!=null;
    }

    public boolean verifyProductionOrderStartMessage() { return productionOrder != null; }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        ProductionLineRepository plRepo = PersistenceContext.repositories().productionLine();
        MachineRepository machineRepository = PersistenceContext.repositories().machines();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();

        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        if (this.verifyProductionOrderStartMessage()) {
            if (this.productionOrder.obtainProduct().obtainProductionSheet() == null) {
                ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
                ErrorNotification error = new ErrorNotification(this.obtainMachine(), ErrorType.PRODUCTION_ORDER_WITHOUT_SHEET, this.obtainDate(), this.identity(), this.getClass());
                errorRepo.save(error);
                this.updateStateToError();
                messageRepository.save(this);
                return false;
            }
            currentPO.obtainState().setExecuting();
            currentPO.updateProductionLine(plRepo.ofIdentity(Designation.valueOf(plInternalCode)).get());
            currentPO.startProductionOrder();
        }


        Machine m = machineRepository.ofIdentity(this.obtainMachine().identity()).get();
        m.updateMachineStatusToActive();
        machineRepository.save(m);
        currentPO.obtainPOReport().addMachineTime(new MachineTime(this.obtainMachine(), this.obtainDate(), MachineStatus.ACTIVE));
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }

}
