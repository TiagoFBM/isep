package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Invariants;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.machinemanagement.repositories.MachineRepository;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.RawMaterial;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.production.services.ImportFileToBytesService;

import java.util.logging.Logger;

public class ProductionLineCRUDSmokeTester {

    private static final Logger LOGGER = Logger.getLogger(ProductionLineCRUDSmokeTester.class.getName());
    private final MachineRepository repoMachines = PersistenceContext.repositories().machines();
    private final ProductionLineRepository repoProductionLines = PersistenceContext.repositories().productionLine();

    public void testProductionLines() {
        Machine m1 = repoMachines.save(new Machine("123", (short) 23, "aaaaaa123", "asdasd", "asdasdh", Calendars.of(2000, 10, 10), "asdlkasd"));
        Machine m2 = repoMachines.save(new Machine("1235", (short) 83, "aaaaaa123123", "asdasd", "asdasdh", Calendars.of(2000, 10, 10), "asdlkasd"));

        //save
        ProductionLine pl1 = new ProductionLine("jhghfhdkd13", "askjdhasd");
        ProductionLine pl2 = new ProductionLine("asdsdfjskhdf", "askjdhasd");
        pl1.addMachine(m1);
        pl2.addMachine(m2);
        repoProductionLines.save(pl1);
        repoProductionLines.save(pl2);
        LOGGER.info("»»» created production lines");

        //findAll
        final Iterable<ProductionLine> l= repoProductionLines.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all production lines");

        //count
        final long n = repoProductionLines.count();
        LOGGER.info("»»» # production lines = " + n);

        //ofIdentity
        final ProductionLine plfin1 = repoProductionLines.ofIdentity(Designation.valueOf("jhghfhdkd13")).orElseThrow(IllegalStateException::new);
        final ProductionLine plfin2 = repoProductionLines.ofIdentity(Designation.valueOf("asdsdfjskhdf")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found production lines with identity");

        // containsOfIdentity
        final boolean hasId1 = repoProductionLines.containsOfIdentity(plfin1.identity());
        final boolean hasId2 = repoProductionLines.containsOfIdentity(plfin2.identity());
        Invariants.ensure(hasId1);
        Invariants.ensure(hasId2);
        LOGGER.info("»»» contains production lines of identity");

        // contains
        final boolean has1 = repoProductionLines.contains(plfin1);
        final boolean has2 = repoProductionLines.contains(plfin2);
        Invariants.ensure(has1);
        Invariants.ensure(has2);
        LOGGER.info("»»» contains production lines");

        // delete
        repoProductionLines.delete(plfin1);
        repoMachines.delete(m1);
        LOGGER.info("»»» delete production line");

        // deleteOfIdentity
        repoProductionLines.deleteOfIdentity(plfin2.identity());
        repoMachines.deleteOfIdentity(m2.identity());
        LOGGER.info("»»» delete production line of identity");

        // size
        final long n2 = repoProductionLines.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # production line = " + n2);
    }
}
