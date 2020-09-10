package fabrica.factoryfloor.machinemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListMachinesService {

    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final MachineRepository repo= PersistenceContext.repositories().machines();

    /**
     * All Raw Materials
     *
     * @return all raw materials
     */
    public Iterable<Machine> unassignedMachines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER);
        return this.repo.findUnassignedMachines();
    }

    public List<Machine> allMachines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return repo.findAll();
    }

}
