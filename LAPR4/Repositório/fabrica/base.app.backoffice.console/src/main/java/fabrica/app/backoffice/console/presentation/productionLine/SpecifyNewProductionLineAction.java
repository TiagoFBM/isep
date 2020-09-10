package fabrica.app.backoffice.console.presentation.productionLine;

import eapli.framework.actions.Action;

public class SpecifyNewProductionLineAction implements Action {

    @Override
    public boolean execute() {
        return new SpecifyNewProductionLineUI().show();
    }
}
