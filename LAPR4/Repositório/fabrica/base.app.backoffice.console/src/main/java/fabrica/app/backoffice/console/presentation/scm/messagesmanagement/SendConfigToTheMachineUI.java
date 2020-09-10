package fabrica.app.backoffice.console.presentation.scm.messagesmanagement;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import fabrica.app.backoffice.console.presentation.machine.MachinePrinter;
import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.scm.sendconfigfilemanagement.aplication.SendConfigToTheMachineController;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class SendConfigToTheMachineUI extends AbstractUI {


    private SendConfigToTheMachineController controller = new SendConfigToTheMachineController();

    @Override
    protected boolean doShow() {
        List<Machine> machines = controller.getMachines();
        if (machines.isEmpty()){
            return false;
        }
        SelectWidget<Machine> selector = new SelectWidget("Machines", machines, new MachinePrinter());
        selector.show();

        if (selector.selectedOption()==0){
            return false;
        }

        Machine machine = selector.selectedElement();

        if (!controller.verificationConfigFile(machine.obtainIdentificationNumber())){
            System.out.println("There is a configuration file in the queue to be processed in that machine. Try again later");
            return false;
        }

        if (machine.obtainConfigurationFiles().isEmpty()){
            System.out.println("The machine you select doesn´t have a configuration file");
            return false;
        }

        SelectWidget<ConfigurationFile> selectorConfigurationFile = new SelectWidget<>("ConfigurationFiles", machine.obtainConfigurationFiles(), new ConfigFilePrinter());

        selectorConfigurationFile.show();

        if (selectorConfigurationFile.selectedOption()==0){
            return false;
        }

        ConfigurationFile file = selectorConfigurationFile.selectedElement();

        try{
            controller.sendConfigFile(machine.obtainIdentificationNumber(), file);
            System.out.println("Config file request created successfully");
            return true;
        }catch(IntegrityViolationException | ConstraintViolationException e) {
            System.out.println("\n=== The request file with that code already exists in the system. ===");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating the file request - " + e.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Send a config file to the Machine";
    }
}
