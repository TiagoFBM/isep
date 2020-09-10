package fabrica.factoryfloor.productionlinemanagement.repositories;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Calendar;
import java.util.List;

public interface ProductionLineRepository extends DomainRepository<Designation, ProductionLine> {

    List<ProductionLine> findAll();
    ProductionLine findProductionLineFromMachineInternalCode(String machineCode);

    List<ProductionLine> findAllProductionLineByInstalationDate(Calendar instalationDate);
}
