package fabrica.app.backoffice.console.presentation.productionLine;

import fabrica.factoryfloor.productionlinemanagement.application.SpecifyNewProductionLineController;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.app.backoffice.console.presentation.machine.MachinePrinter;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;

public class SpecifyNewProductionLineUI extends AbstractUI {
    private final SpecifyNewProductionLineController controller = new SpecifyNewProductionLineController();

    //protected Controller controller() { return this.controller; }

    @Override
    protected boolean doShow() {
        final String code= Console.readLine("Production line code:");
        final String description= Console.readLine("Production line description:");
        ProductionLine pl = this.controller.specifyNewProductionLine(code, description);
        final Iterable<Machine> machines = controller.getMachines();
        int choice;
        do {
            choice=Console.readInteger("Add new machine (1); Exit(0)");
            if (choice==1) {
                final SelectWidget<Machine> selector = new SelectWidget<>("Machines:", machines,
                        new MachinePrinter());
                selector.show();
                final Machine machine = selector.selectedElement();

                if(machine != null) {
                    if (!controller.addMachine(pl, machine)) System.out.println("Machine already in line.");
                }
            }
        } while (choice!=0);
        if (pl.hasMachines()) {
            controller.save(pl);
            System.out.println("Production line added");
            return true;
        } else {
            System.out.println("Unable to add the production line - there were no machines added.");
        }
        try{
        }catch(@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Code already in use");
        }catch(final IllegalArgumentException e) {
            System.out.println("Invalid code or description for deposit");
        }catch(Exception e) {
            System.out.println("Error adding new deposit");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Specify new production line";
    }
}
