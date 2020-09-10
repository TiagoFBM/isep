package fabrica.factoryfloor.machinemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.services.ImportFileToBytesService;
import fabrica.usermanagement.domain.BaseRoles;

import java.io.IOException;
import java.util.List;

public class SpecifyMachineConfigurationFileController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MachineRepository machineRepository = PersistenceContext.repositories().machines();
    private final ListMachinesService listMachinesService = new ListMachinesService();

    public List<Machine> getMachines() {
        return listMachinesService.allMachines();
    }

    public Machine specifyConfigurationFile(Machine machine, String filePath, String fileName, String shortDescription) throws IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        ImportFileToBytesService svc = new ImportFileToBytesService();

        byte[] content;
        try {
            content = svc.transformToBytes(fileName, filePath);
        } catch (IOException e) {
            throw new IOException("Invalid file");
        }
        ConfigurationFile cf = ConfigurationFile.valueOf(content, shortDescription);
        Machine m = searchMachineByIdNumber(machine.identity());
        List<ConfigurationFile> list = m.obtainConfigurationFiles();
        list.add(cf);
        m.updateConfigurationFiles(list);
        return machineRepository.save(m);
    }

    public Machine searchMachineByIdNumber(AlfanumericCode idNumber){
        if(machineRepository.ofIdentity(idNumber).isPresent()){
            return machineRepository.ofIdentity(idNumber).get();
        }
        return null;
    }
}
