package fabrica.factoryfloor.machinemanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.domain.ProtocolIdentificationNumber;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.production.domain.AlfanumericCode;

import javax.crypto.Mac;
import java.util.Collection;
import java.util.List;

public interface MachineRepository extends DomainRepository<AlfanumericCode, Machine> {
    List<Machine> findUnassignedMachines();

    List<Machine> findAll();

    Machine findMachineByProtocolNumber(ProtocolIdentificationNumber protocolID);

    List<Machine>findAllMachinesFromProductionLine(String pl);
}
