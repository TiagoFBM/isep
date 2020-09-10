
package fabrica.app.backoffice.console.presentation.productionorder;

import fabrica.productordersmanagement.aplication.ListProductionOrdersPerStateController;
import fabrica.productordersmanagement.domain.ProductionOrder;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.io.util.Console;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class ListProductionOrdersPerStateUI extends AbstractListUI<ProductionOrder> {

    final ListProductionOrdersPerStateController theController = new ListProductionOrdersPerStateController();

    @Override
    protected Iterable<ProductionOrder> elements() {
        System.out.println("Write the state of the production orders to be shown");
        String state=Console.readLine("State");
        return this.theController.productionOrdersPerState(state);
    }

    @Override
    protected Visitor<ProductionOrder> elementPrinter() {
        return new ProductionOrderPrinter();
    }

    @Override
    protected String elementName() {
        return "Production Order";
    }

    @Override
    protected String listHeader() {
        return "Production Orders per state";
    }

    @Override
    protected String emptyMessage() {
        return "null";
    }

    @Override
    public String headline() {
        return "null";
    }
}
