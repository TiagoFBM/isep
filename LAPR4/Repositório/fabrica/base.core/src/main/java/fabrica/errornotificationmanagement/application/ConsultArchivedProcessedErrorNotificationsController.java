package fabrica.errornotificationmanagement.application;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;

public class ConsultArchivedProcessedErrorNotificationsController {

    private ErrorNotificationRepository repository = PersistenceContext.repositories().errornotification();
    private MachineRepository machineRepository = PersistenceContext.repositories().machines();

    public Iterable<Machine> findMachines(){
        return machineRepository.findAll();
    }

    public Iterable<ErrorNotification> listErrorNotificationsByMachine(Machine machine){
        return repository.findByMachine(machine);
    }

    public Iterable<ErrorNotification> listErrorNotificationsByTime(){
        return repository.findByTime();
    }

}
