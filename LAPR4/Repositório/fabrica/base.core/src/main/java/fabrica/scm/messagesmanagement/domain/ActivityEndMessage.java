package fabrica.scm.messagesmanagement.domain;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.MachineStatus;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;
import fabrica.spm.messageprocessment.ReportCreatorService;

import javax.persistence.*;

@Entity
public class ActivityEndMessage extends Message {

    @OneToOne
    private ProductionOrder productionOrder;

    protected ActivityEndMessage() {
    }

    public ActivityEndMessage(String sendDate, Machine machine) {
        super(sendDate, machine);
    }

    public ActivityEndMessage(String sendDate, Machine machine, ProductionOrder productionOrder) {
        super(sendDate, machine);

        if (!verifyActivityStartMessage(productionOrder)) {
            throw new IllegalArgumentException("Activity End Message Invalid!");
        }

        this.productionOrder = productionOrder;
    }

    /*
    Opcionalmente, o contexto atual consta na mensagem enviada pela máquina em causa;
    */

    private boolean verifyActivityStartMessage(ProductionOrder productionOrder) {
        return productionOrder != null;
    }

    public boolean verifyProductionOrderEndMessage() {
        return productionOrder != null;
    }

    public ProductionOrder obtainProducterOrder() { return productionOrder; }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        MachineRepository machineRepository = PersistenceContext.repositories().machines();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();

        Machine m = machineRepository.ofIdentity(this.obtainMachine().identity()).get();
        if (!m.isActive()) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            ErrorNotification error = new ErrorNotification(this.obtainMachine(), ErrorType.END_WITHOUT_START, this.obtainDate(), this.identity(), this.getClass());
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }

        if (this.verifyProductionOrderEndMessage()) {
            currentPO.obtainState().setFinished();
        }
        m.updateMachineStatusToInactive();
        machineRepository.save(m);

        currentPO.obtainPOReport().addMachineTime(new MachineTime(this.obtainMachine(), this.obtainDate(), MachineStatus.INACTIVE));
        poRepo.save(currentPO);

        if (this.verifyProductionOrderEndMessage()) {
            ReportCreatorService serv = new ReportCreatorService(currentPO);
            serv.createReport();
        }
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }
}
