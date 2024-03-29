/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.productordersmanagement.aplication;

import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Utilizador
 */
public class ListProductionOrdersPerStateService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderRepository repo = PersistenceContext.repositories().productionOrder();

    /**
     * All production orders in a state
     *
     * @param state
     * @return all productions orders in a state
     */
    public Iterable<ProductionOrder> productionOrdersPerState(String state) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return this.repo.getProductionOrdersWithState(state);
    }
}
