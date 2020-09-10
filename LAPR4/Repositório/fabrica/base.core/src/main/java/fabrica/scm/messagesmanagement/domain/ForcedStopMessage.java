package fabrica.scm.messagesmanagement.domain;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.strings.util.StringPredicates;
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
public class ForcedStopMessage extends Message{

    private Designation codErro;

    protected ForcedStopMessage(){
    }

    public ForcedStopMessage(String sendDate, Machine machine, String codErro) {
        super(sendDate, machine);
        if(!verifyValidForcedStopMessage(codErro)){
            throw new IllegalArgumentException("Invalid Error Code!");
        }
        this.codErro = Designation.valueOf(codErro);
    }

    private boolean verifyValidForcedStopMessage(String codErro){
        return !StringPredicates.isNullOrEmpty(codErro);
    }

    @Override
    public boolean processMessage(String plInternalCode) {
        ProductionOrderRepository poRepo = PersistenceContext.repositories().productionsOrders();
        MachineRepository machineRepository = PersistenceContext.repositories().machines();
        MessageRepository messageRepository = PersistenceContext.repositories().messages();

        Machine m = machineRepository.ofIdentity(this.obtainMachine().identity()).get();
        if (!m.isActive()) {
            ErrorNotificationRepository errorRepo = PersistenceContext.repositories().errornotification();
            ErrorNotification error = new ErrorNotification(this.obtainMachine(), ErrorType.FORCED_STOP_WITHOUT_START, this.obtainDate(), this.identity(), this.getClass());
            errorRepo.save(error);
            this.updateStateToError();
            messageRepository.save(this);
            return false;
        }

        m.updateMachineStatusToForcedStop();
        machineRepository.save(m);

        ProductionOrder currentPO = poRepo.getLastProcessesProductionOrder(plInternalCode, this.obtainDate());
        currentPO.obtainState().setSuspended();
        currentPO.obtainPOReport().addMachineTime(new MachineTime(this.obtainMachine(), this.obtainDate(), MachineStatus.FORCED_STOP));
        poRepo.save(currentPO);
        this.updateStateToProcessed();
        messageRepository.save(this);
        return true;
    }
}
