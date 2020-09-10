package fabrica.factoryfloor.productionlinemanagement.application;

import eapli.framework.application.UseCaseController;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.factoryfloor.machinemanagement.application.ListMachinesService;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class SpecifyNewProductionLineController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository pLineRepository = PersistenceContext.repositories().productionLine();
    private final ListMachinesService listMachines = new ListMachinesService();

    private ProductionLine pl;

    public ProductionLine specifyNewProductionLine(final String code, final String desc) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return new ProductionLine(code, desc);
    }

    public Iterable<Machine> getMachines() {
        return listMachines.unassignedMachines();
    }

    public boolean addMachine(ProductionLine pLine, Machine machine) {
        return pLine.addMachine(machine);
    }

    public ProductionLine save(ProductionLine pl) {
        return this.pLineRepository.save(pl);
    }
}
