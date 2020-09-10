package fabrica.persistence.impl.inmemory;

import fabrica.clientusermanagement.repositories.ClientUserRepository;
import fabrica.clientusermanagement.repositories.SignupRequestRepository;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.infrastructure.bootstrapers.BaseBootstrapper;
import fabrica.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.production.repositories.ProductRepository;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.FailureRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;
import fabrica.scm.messagesmanagement.repositories.RequestConfigFileRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return null;
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

    @Override
    public ProductRepository products() {
        return null;
    }

    @Override
    public RawMaterialRepository rawMaterials() {
        return null;
    }

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    @Override
    public DepositRepository deposit() {
        return null;
    }

    @Override
    public RawMaterialCategoryRepository rawMaterialCategories() {
        return null;
    }

    @Override
    public MachineRepository machines() {
        return null;
    }

    @Override
    public ProductionOrderRepository productionsOrders() {
        return null;
    }

    @Override
    public MessageRepository messages() {
        return null;
    }

    @Override
    public FailureRepository failures() {
        return null;
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return null;
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

    @Override
    public ProductionLineRepository productionLine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductionOrderRepository productionOrder() {
        return null;
    }

    public ErrorNotificationRepository errornotification() {
        return null;
    }

    @Override
    public RequestConfigFileRepository requestConfigFile() {
        return null;
    }
}
