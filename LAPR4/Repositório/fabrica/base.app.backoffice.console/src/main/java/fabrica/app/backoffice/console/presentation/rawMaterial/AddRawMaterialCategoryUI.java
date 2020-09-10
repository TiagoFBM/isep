package fabrica.app.backoffice.console.presentation.rawMaterial;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.io.util.Console;
import fabrica.production.application.AddNewRawMaterialCategoryController;
import fabrica.production.domain.BriefDescription;
import fabrica.production.domain.ShortAlfanumericCode;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class AddRawMaterialCategoryUI extends AbstractUI {

    private final AddNewRawMaterialCategoryController controller = new AddNewRawMaterialCategoryController();

    @Override
    protected boolean doShow() {

        String categoryID = Console.readLine("Category ID: ");
        String briefDescription = Console.readLine("Brief Description: ");

        try {
            this.controller.registerRawMaterialCategory(categoryID, briefDescription);
        } catch (IntegrityViolationException | ConstraintViolationException e) {
            System.out.println("\n=== The Raw Material Category with that code already exists in the system. ===");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid data - " + e.getMessage());
            return false;
        }
        System.out.println("The Raw Material Category was successfully added.");
        return true;

    }

    @Override
    public String headline() {
        return "--- Add New Raw Material Category ---";
    }
}
