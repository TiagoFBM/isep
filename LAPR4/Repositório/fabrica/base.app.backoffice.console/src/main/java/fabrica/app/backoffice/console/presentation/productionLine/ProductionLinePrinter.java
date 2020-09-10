package fabrica.app.backoffice.console.presentation.productionLine;

import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import eapli.framework.visitor.Visitor;

public class ProductionLinePrinter implements Visitor<ProductionLine> {

    public void visit(final ProductionLine visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
