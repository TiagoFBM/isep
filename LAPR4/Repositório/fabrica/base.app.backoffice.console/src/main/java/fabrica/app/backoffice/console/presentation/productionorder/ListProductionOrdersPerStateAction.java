/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.app.backoffice.console.presentation.productionorder;

import eapli.framework.actions.Action;

/**
 *
 * @author Utilizador
 */
public class ListProductionOrdersPerStateAction implements Action {

    @Override
    public boolean execute() {
        return new ListProductionOrdersPerStateUI().show();
    }
}
