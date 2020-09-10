package fabrica.app.backoffice.console.presentation.deposit;

import fabrica.factoryfloor.depositmanagement.application.SpecifyNewDepositController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;

public class SpecifyNewDepositUI extends AbstractUI {

    private final SpecifyNewDepositController controller = new SpecifyNewDepositController();

    //protected Controller controller() { return this.controller; }

    @Override
    protected boolean doShow() {
        final String code= Console.readLine("Deposit code:");
        final String description= Console.readLine("Deposit description:");
        try{
            this.controller.specifyNewDeposit(code, description);
            System.out.println("Deposit added");
        }catch(@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Code already in use");
        }catch(final IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Invalid code or description for deposit");
        }catch(Exception e) {
            System.out.println("Error adding new deposit");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Specify new deposit";
    }
}
