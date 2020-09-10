/**
 *
 */
package fabrica.infrastructure.persistence;

import fabrica.clientusermanagement.repositories.ClientUserRepository;

import fabrica.clientusermanagement.repositories.SignupRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.production.repositories.ProductRepository;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.scm.messagesmanagement.repositories.FailureRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;
import fabrica.scm.messagesmanagement.repositories.RequestConfigFileRepository;

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the
     * repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();

    ProductRepository products();

    RawMaterialRepository rawMaterials();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    DepositRepository deposit();

    RawMaterialCategoryRepository rawMaterialCategories();

    MachineRepository machines();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionLineRepository productionLine();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionOrderRepository productionOrder();

    ProductionOrderRepository productionsOrders();

    MessageRepository messages();

    FailureRepository failures();
    
    /**
     * Error notification repository
     * 
     * @return 
     */
    ErrorNotificationRepository errornotification();

    RequestConfigFileRepository requestConfigFile();
}
