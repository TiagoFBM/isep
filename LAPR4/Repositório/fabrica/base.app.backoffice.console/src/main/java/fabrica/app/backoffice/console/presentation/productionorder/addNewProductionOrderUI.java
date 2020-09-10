package fabrica.app.backoffice.console.presentation.productionorder;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.io.util.Console;
import fabrica.production.domain.Product;
import fabrica.production.domain.Units;
import fabrica.productordersmanagement.aplication.AddProductionOrderController;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;


public class addNewProductionOrderUI extends AbstractUI {

    private final AddProductionOrderController controller = new AddProductionOrderController();

    @Override
    protected boolean doShow() {
        String response;
        List<String> orders = new LinkedList<>();
        Iterable<Product> products = this.controller.products();
        SelectWidget<Product> selector = new SelectWidget<>("Products", products);
        if (!products.iterator().hasNext()){
            System.out.println("There are no products in the system\n");
            return false;
        }

        selector.show();
        if (selector.selectedOption()== 0){
            return false;
        }
        Product p = selector.selectedElement();
        if (!p.hasProductionSheet()){
            System.out.println("The product you choose don´t have a production sheet");
        }

        Calendar emissionDate;
        Calendar previsionDate;

        final String productionOrderCode = Console.readLine("Production Order Code: ");
        do{
             emissionDate = Calendar.getInstance();
             previsionDate = Console.readCalendar("Prevision Date (DD-MM-YYYY) -> Must be greater than today: ");

        }while (previsionDate ==null || previsionDate.before(emissionDate));
        
        final int quantity = Console.readInteger("Quantity: ");

        List<Units> list = new ArrayList<>(Arrays.asList(Units.values()));
        SelectWidget<Units> select = new SelectWidget<>("Objects to export", list);

        Units unity;

        do{
            select.show();
            unity = select.selectedElement();
        }while (unity==null);

        do{
            final String idOrder = Console.readNonEmptyLine("Request ID: ", "The request id can´t be empty!");
            orders.add(idOrder);
            response = Console.readLine("Do you want to add another request? (Y/N)");
        }while (response.equalsIgnoreCase("Y"));
        try {
            controller.registerProductionOrder(productionOrderCode, emissionDate, previsionDate, p, quantity, unity.toString(),orders);
            System.out.println("Product Order added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating the production order - " + e.getMessage());
        } catch (IntegrityViolationException | ConstraintViolationException e) {
            System.out.println("\n=== The production order with that code already exists in the system. ===");
            return false;
        }
        return false;
    }

    @Override
    public String headline() {
        return "Add new production order";
    }


}
