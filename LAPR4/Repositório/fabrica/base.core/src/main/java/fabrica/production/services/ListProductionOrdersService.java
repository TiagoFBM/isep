package fabrica.production.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.Calendar;
import java.util.List;

public class ListProductionOrdersService {

    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionsOrders();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<ProductionOrder> allProductionOrdersFromRequest(String requestID){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return productionOrderRepository.findAllProductionOrdersFromRequest(requestID);
    }

    public List<ProductionOrder> allProductionOrders() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return productionOrderRepository.findAll();
    }

    public List<ProductionOrder> allProductionOrdersBetweenDates(Calendar init, Calendar finish){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return productionOrderRepository.findAllBetweenDates(init, finish);
    }
}
