package fabrica.factoryfloor.machinemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.Calendar;

public class AddNewMachineController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MachineRepository machineRepository = PersistenceContext.repositories().machines();

    public Machine registerMachine(String internalCode, short identificationNumber, String serialNumber, String manufacturer, String model, Calendar installationDate, String operation) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        Machine m = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        return this.machineRepository.save(m);
    }
}
