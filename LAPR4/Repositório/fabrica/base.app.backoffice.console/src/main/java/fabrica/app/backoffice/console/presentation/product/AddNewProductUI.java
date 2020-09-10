package fabrica.app.backoffice.console.presentation.product;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.production.application.AddNewProductController;
import fabrica.production.domain.*;
import org.hibernate.exception.ConstraintViolationException;
import java.util.Arrays;

public class AddNewProductUI extends AbstractUI {

    private final AddNewProductController controller = new AddNewProductController();

    @Override
    protected boolean doShow() {

        String fabricationCode = Console.readLine("Fabrication Code: ");
        String comercialCode = Console.readLine("Comercial Code: ");
        String briefDescription = Console.readLine("Brief Description: ");
        String fullDescription = Console.readLine("Full Description: ");
        String productCategory = Console.readLine("Product Category: ");
        Units unit = null;

        SelectWidget<Units> unidades = new SelectWidget<>("-- Possible Units --", Arrays.asList(Units.values()));
        unidades.show();
        unit = unidades.selectedElement();

        if (unit!=null) {
            try {
                this.controller.registerProduct(fabricationCode, comercialCode, briefDescription, fullDescription, unit.toString(), productCategory);
            } catch (IntegrityViolationException | ConstraintViolationException e) {
                System.out.println("\n=== The product with that code already exists in the system. ===");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid data - " + e.getMessage());
                return false;
            }
            System.out.println("The product was successfully added.");
            return true;
        } else {
            System.out.println("The product wasn't added to the system.");
            return false;
        }
    }

    @Override
    public String headline() {
        return "--- Add New Product ---";
    }

}
