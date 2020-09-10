package fabrica.persistence.impl.jpa;

import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.scm.sendconfigfilemanagement.aplication.domain.RequestConfigFile;
import fabrica.scm.messagesmanagement.repositories.RequestConfigFileRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaSendConfigRepository extends BasepaRepositoryBase<RequestConfigFile, Long, ProtocolIdentificationNumber>
        implements RequestConfigFileRepository{

    public JpaSendConfigRepository(){
        super("protocolID");
    }

    @Override
    public List<RequestConfigFile> findAll(){
        final TypedQuery<RequestConfigFile> query = super.createQuery("SELECT r FROM RequestConfigFile r", RequestConfigFile.class);
        return query.getResultList();
    }

    @Override
    public List<RequestConfigFile> findNewRequests() {
        final Query query = super.entityManager().createNativeQuery("SELECT r.* FROM REQUESTCONFIGFILE r WHERE r.STATE  = 0", RequestConfigFile.class);
        return query.getResultList();
    }

    @Override
    public List<RequestConfigFile> verifyConfigFile(ProtocolIdentificationNumber protocolID) {
        final Query query = super.entityManager().createNativeQuery("SELECT r.* FROM REQUESTCONFIGFILE r WHERE r.IDENTIFICATIONNUMBER  = :id AND r.STATE  = 0", RequestConfigFile.class);
        query.setParameter("id", protocolID.toString());
        return query.getResultList();
    }

}
