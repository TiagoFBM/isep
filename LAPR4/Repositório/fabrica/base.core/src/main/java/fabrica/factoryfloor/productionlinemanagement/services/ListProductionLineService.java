package fabrica.factoryfloor.productionlinemanagement.services;

import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.Calendar;
import java.util.List;

public class ListProductionLineService {

    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final ProductionLineRepository repo= PersistenceContext.repositories().productionLine();

    /**
     * All Raw Materials
     *
     * @return all raw materials
     */
    public List<ProductionLine> allProductionLines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER);
        return this.repo.findAll();
    }

    public List<ProductionLine> allProductionLinesByInstalationDate(Calendar instalationDate) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER);
        return this.repo.findAllProductionLineByInstalationDate(instalationDate);

    }
}
