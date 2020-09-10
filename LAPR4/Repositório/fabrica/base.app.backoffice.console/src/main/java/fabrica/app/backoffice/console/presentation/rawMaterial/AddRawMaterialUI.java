package fabrica.app.backoffice.console.presentation.rawMaterial;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.production.application.AddNewRawMaterialController;
import fabrica.production.domain.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddRawMaterialUI extends AbstractUI {
    private final AddNewRawMaterialController controller = new AddNewRawMaterialController();
    private final AddRawMaterialCategoryUI interfaceCategory = new AddRawMaterialCategoryUI();
    @Override
    protected boolean doShow() {
        String response;
        boolean flag = false;
        Iterable<RawMaterialCategory> categories = this.controller.rmCategories();
        SelectWidget<RawMaterialCategory> selector = new SelectWidget<>("Raw Material Categories", categories, new RawMaterialCategoriesPrinter());
        if (!categories.iterator().hasNext()){
            System.out.println("Raw Material Categories don´t exist\n");
            System.out.println("=========================================================");
            do{
                response = Console.readLine("Do you want to add one? (y/n)");
            }while (!(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")));
            if (response.equalsIgnoreCase("y")){
                System.out.println("You will be redirected to the add categories feature\n");
                System.out.println("========================================================");
                interfaceCategory.doShow();
                categories = this.controller.rmCategories();
                selector = new SelectWidget<>("Raw Material Categories", categories, new RawMaterialCategoriesPrinter());
                selector.show();
                flag = true;
            }else{
                System.out.println("You must choose a category to create a raw material\n");
                return false;
            }
        }
        if (flag !=true){
            selector.show();

        }
        final RawMaterialCategory category = selector.selectedElement();
        if (category != null){
            final String rawMaterialCode = Console.readLine("Raw Material Code: ");
            final String fullDescription = Console.readLine("Description of the raw material: ");
            final String nameFile = Console.readLine("Nome do ficheiro(with extension)");
            final String pathFile = Console.readLine("Caminho do ficheiro");

            try {
                this.controller.registerRawMaterial(rawMaterialCode, fullDescription, category, nameFile, pathFile);

            } catch (IllegalArgumentException e){
                System.out.println("There was an error creating the raw material - " + e.getMessage());
                return false;
            }catch (IntegrityViolationException | ConstraintViolationException e) {
                System.out.println("\n=== The raw material with that code already exists in the system. ===");
                return false;
            } catch (IOException e) {
                System.out.println("File must be valid");
                return false;
            }
        }else{
            System.out.println("You must choose a valid category\n\n");
            return false;
        }
        System.out.println("Raw Material added successfully");
        return true;
    }

    @Override
    public String headline() {
        return "Add new raw material";
    }

}
