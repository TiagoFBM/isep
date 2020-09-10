package fabrica.app.backoffice.console.presentation.productionLine;

import fabrica.factoryfloor.productionlinemanagement.application.ListProductionLineController;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListProductionLineUI extends AbstractListUI<ProductionLine> {

    final ListProductionLineController theController = new ListProductionLineController();

    @Override
    protected Iterable<ProductionLine> elements() {
        return this.theController.allProductionLines();
    }

    @Override
    protected Visitor<ProductionLine> elementPrinter() {
        return new ProductionLinePrinter();
    }

    @Override
    protected String elementName() {
        return "Production line";
    }

    @Override
    protected String listHeader() {
        return "Production lines";
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
