/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.app.backoffice.console.presentation.productionorder;

import fabrica.productordersmanagement.domain.ProductionOrder;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class ProductionOrderPrinter implements Visitor<ProductionOrder> {

    public void visit(final ProductionOrder visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
