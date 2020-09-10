package fabrica.app.backoffice.console.presentation.product;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;
import fabrica.production.application.ConsultProductionOrdersFromRequestController;
import fabrica.productordersmanagement.domain.ProductionOrder;
import org.hibernate.exception.ConstraintViolationException;

public class ConsultProductionOrdersFromRequestUI extends AbstractUI {

    ConsultProductionOrdersFromRequestController controller = new ConsultProductionOrdersFromRequestController();

    @Override
    protected boolean doShow() {

        String requestID = Console.readLine("Resquest ID: ");

        if (requestID != null) {
            try {
                int i = 1;
                for (ProductionOrder p : controller.allProductionOrdersFromRequest(requestID)) {
                    System.out.printf("-- Production Order %d: --\n", i);
                    i++;
                    System.out.println(p.toString());
                }

            } catch (IntegrityViolationException | ConstraintViolationException e) {
                System.out.println("\n=== The request invalid. ===");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid data - " + e.getMessage());
                return false;

            }
        }else{
            System.out.println("The request ID is null");
            return false;
        }
        return true;
    }

    @Override
    public String headline() {
        return "----- Production Orders From Request -----";
    }
}
