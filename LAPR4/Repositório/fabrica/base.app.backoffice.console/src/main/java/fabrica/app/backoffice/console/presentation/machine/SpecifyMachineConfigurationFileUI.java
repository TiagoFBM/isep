package fabrica.app.backoffice.console.presentation.machine;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.factoryfloor.machinemanagement.application.SpecifyMachineConfigurationFileController;
import fabrica.factoryfloor.machinemanagement.domain.Machine;

import java.io.IOException;

public class SpecifyMachineConfigurationFileUI extends AbstractUI {
    SpecifyMachineConfigurationFileController controller = new SpecifyMachineConfigurationFileController();

    @Override
    protected boolean doShow() {


        Iterable<Machine> machines = this.controller.getMachines();
        SelectWidget<Machine> selector = new SelectWidget<>("Machines", machines, new MachinePrinter());

        if (!machines.iterator().hasNext()) {
            System.out.println("There are no machines in the system.");
            return false;
        }

        selector.show();
        final Machine machine = selector.selectedElement();
        if (machine != null) {
            String filePath = Console.readLine("File path: ");
            String fileName = Console.readLine("File name: ");
            String shortDescription = Console.readLine("Short description: ");
            Machine m = null;
            try {
                m = controller.specifyConfigurationFile(machine, filePath, fileName, shortDescription);
            } catch (IOException e) {
                System.out.println("Error finding the configuration file.");
            }
        } else {
            System.out.println("Invalid machine.");
            return false;
        }

        System.out.println("Successfully added the configuration file to the machine.");
        return true;
    }

    @Override
    public String headline() {
        return "--- Specify a machine's configuration file ---";
    }
}
