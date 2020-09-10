package fabrica.app.backoffice.console.presentation.machine;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;
import fabrica.factoryfloor.machinemanagement.application.AddNewMachineController;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import java.util.Calendar;

public class AddNewMachineUI extends AbstractUI {

    private final AddNewMachineController controller = new AddNewMachineController();

    @Override
    protected boolean doShow() {
        String internalCode = Console.readLine("Internal code: ");
        short identificationNumber = readShort("Identification Number: ");
        String serialNumber = Console.readLine("Serial Number: ");
        String manufacturer = Console.readLine("Manufacturer: ");
        String model = Console.readLine("Model: ");
        Calendar installationDate = Console.readCalendar("Installation date (DD-MM-YYYY): ");
        String operation = Console.readLine("Designated operation: ");

        try {
            this.controller.registerMachine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
            System.out.println("Machine created successfully!");
            return true;
        } catch(IntegrityViolationException | ConstraintViolationException e) {
            System.out.println("\n=== The machine with that code already exists in the system. ===");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating the machine - " + e.getMessage());
        }
        return false;
    }

    public static short readShort(final String prompt) {
        do {
            try {
                final String input = Console.readLine(prompt);
                return Short.parseShort(input);
            } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    @Override
    public String headline() {
        return "--- Add New Machine ---";
    }
}
