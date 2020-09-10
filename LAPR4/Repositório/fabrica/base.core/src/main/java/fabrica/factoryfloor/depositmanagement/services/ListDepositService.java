package fabrica.factoryfloor.depositmanagement.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.Product;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListDepositService {

    private final DepositRepository depositRepository = PersistenceContext.repositories().deposit();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<Deposit> allDeposits() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return depositRepository.findAll();
    }



}
