package fabrica.infrastructure.bootstrapers.factoryfloor;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.application.SpecifyNewProductionLineController;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductionLineBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ProductionLineBootstrapper.class);
    final SpecifyNewProductionLineController controller = new SpecifyNewProductionLineController();

    @Override
    public boolean execute() {
        String internalCode1 = "LP_001";
        String internalCode2 = "LP_002";
        String internalCode3 = "LP_003";

        String desc1 = "Linha de produção nº1 - criação de moldes";
        String desc2 = "Linha de produção nº2 - criação de placas de ferro";
        String desc3 = "Linha de produção nº3 - criação de rolhas";

        Iterable<Machine> machines = controller.getMachines();
        List<Machine> machinesList = new ArrayList<>();
        machines.forEach(machinesList::add);

        List<Machine> machines1 = new ArrayList<>();
        machines1.add(machinesList.get(2));
        machines1.add(machinesList.get(3));
        List<Machine> machines2 = new ArrayList<>();
        machines2.add(machinesList.get(0));
        machines2.add(machinesList.get(4));
        List<Machine> machines3 = new ArrayList<>();
        machines3.add(machinesList.get(1));

        register(internalCode1, desc1, machines1);
        register(internalCode2, desc2, machines2);
        register(internalCode3, desc3, machines3);

        return true;
    }

    public void register(String code, String desc, List<Machine> machinesList) {

        try {
            ProductionLine pl = new ProductionLine(code, desc);
            for (Machine m : machinesList) {
                pl.addMachine(m);
            }
            controller.save(pl);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", code);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
