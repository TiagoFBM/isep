package fabrica.factoryfloor.depositmanagement.application;

import eapli.framework.application.UseCaseController;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Tiago Sousa
 */
@UseCaseController
public class SpecifyNewDepositController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final DepositRepository repository = PersistenceContext.repositories().deposit();

    /**
     * Controller to specify a new deposit
     *
     * @param code: identification code
     * @param desc: description
     * @return new deposit
     */
    public Deposit specifyNewDeposit(String code, String desc) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        final Deposit deposit= new Deposit(code, desc);
        return this.repository.save(deposit);
    }
}
