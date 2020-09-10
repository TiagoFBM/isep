package fabrica.scm.messagesmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.scm.sendconfigfilemanagement.aplication.domain.RequestConfigFile;

import java.util.List;

public interface RequestConfigFileRepository extends DomainRepository<ProtocolIdentificationNumber, RequestConfigFile> {
    List<RequestConfigFile> findAll();
    List<RequestConfigFile> findNewRequests();
    List<RequestConfigFile> verifyConfigFile(ProtocolIdentificationNumber protocolID);
}
