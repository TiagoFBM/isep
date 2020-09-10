package fabrica.productordersmanagement.aplication;

import eapli.framework.application.UseCaseController;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class ChangeProductionOrderStateController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderRepository repository = PersistenceContext.repositories().productionOrder();

    public ChangeProductionOrderStateController() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
    }

    public Iterable<ProductionOrder> getPendingOrSuspended() {
        return repository.getPendingOrSuspended();
    }

    public boolean changeState(ProductionOrder prodOrder) {
        if (prodOrder.obtainState().isPending()) prodOrder.obtainState().setSuspended();
        else prodOrder.obtainState().setPending();
        final TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();
        ctx.beginTransaction();
        repository.save(prodOrder);
        ctx.commit();
        return true;
    }

}
