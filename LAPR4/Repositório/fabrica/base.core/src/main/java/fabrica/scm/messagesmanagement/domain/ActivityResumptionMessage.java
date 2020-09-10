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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class ActivityResumptionMessage extends Message{

    public ActivityResumptionMessage() {
    }

    public ActivityResumptionMessage(String sendDate, Machine machine) {
        super(sendDate, machine);
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MachineRepository machineRepository = PersistenceContext.repositories().machines();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();
        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());

        Machine m = machineRepository.ofIdentity(this.obtainMachine().identity()).get();

        if (!m.hasForceStopped()) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            ErrorNotification error = new ErrorNotification(this.obtainMachine(), ErrorType.RESUMPTION_WITHOUT_FORCED_STOP, this.obtainDate(), this.identity(), this.getClass());
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }

        m.updateMachineStatusToActive();
        machineRepository.save(m);

        currentPO.obtainState().setExecuting();
        currentPO.obtainPOReport().addMachineTime(new MachineTime(this.obtainMachine(), this.obtainDate(), MachineStatus.RESUMPTION));
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }


}
