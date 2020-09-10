package fabrica.app.backoffice.console.presentation.productionLine;

import eapli.framework.actions.Action;

public class ListProductionLineAction implements Action {

    @Override
    public boolean execute() {
        return new ListProductionLineUI().show();
    }
}
