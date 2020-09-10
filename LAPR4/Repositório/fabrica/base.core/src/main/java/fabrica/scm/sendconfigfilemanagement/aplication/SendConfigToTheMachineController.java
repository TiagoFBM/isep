package fabrica.scm.sendconfigfilemanagement.aplication;

import fabrica.factoryfloor.machinemanagement.application.ListMachinesService;
import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.sendconfigfilemanagement.aplication.domain.RequestConfigFile;
import fabrica.scm.messagesmanagement.repositories.RequestConfigFileRepository;

import java.util.List;

public class SendConfigToTheMachineController {



    private ListMachinesService service = new ListMachinesService();
    private final RequestConfigFileRepository repository = PersistenceContext.repositories().requestConfigFile();

    public List<Machine> getMachines(){
        return service.allMachines();
    }

    public RequestConfigFile sendConfigFile(ProtocolIdentificationNumber machine , ConfigurationFile file) {

        RequestConfigFile request = new RequestConfigFile(machine, file);

        return repository.save(request);
    }

    public boolean verificationConfigFile(ProtocolIdentificationNumber protocolID){
        return repository.verifyConfigFile(protocolID).isEmpty();
    }
}
