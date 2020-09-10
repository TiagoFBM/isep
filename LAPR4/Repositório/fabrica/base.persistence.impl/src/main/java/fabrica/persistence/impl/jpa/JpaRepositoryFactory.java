package fabrica.persistence.impl.jpa;

import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.Application;
import fabrica.clientusermanagement.repositories.SignupRequestRepository;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
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
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ProductRepository products() {
        return new JpaProductRepository();
    }

    @Override
    public RawMaterialRepository rawMaterials() {
        return new JpaRawMaterialRepository();
    }

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    @Override
    public DepositRepository deposit() {
        return new JpaDepositRepository();
    }

    @Override
    public RawMaterialCategoryRepository rawMaterialCategories() {
        return new JpaRawMaterialCategoryRepository();
    }

    @Override
    public MachineRepository machines() {
        return new JpaMachineRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public ProductionLineRepository productionLine() {
        return new JpaProductionLineRepository();
    }

    @Override
    public ProductionOrderRepository productionsOrders() {
        return new JpaProductionOrderRepository();
    }

    @Override
    public MessageRepository messages() {
        return new JpaMessageRepository();
    }

    @Override
    public FailureRepository failures() {
        return new JpaFailureRepository();
    }

    @Override
    public ProductionOrderRepository productionOrder() {
        return new JpaProductionOrderRepository();
    }

    public ErrorNotificationRepository errornotification() {
        return new JpaErrorNotificationRepository();
    }

    @Override
    public RequestConfigFileRepository requestConfigFile() {
        return new JpaSendConfigRepository();
    }
}
