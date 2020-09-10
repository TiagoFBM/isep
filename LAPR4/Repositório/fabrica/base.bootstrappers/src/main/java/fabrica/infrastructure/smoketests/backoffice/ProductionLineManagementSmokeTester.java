package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class ProductionLineManagementSmokeTester implements Action {

    final ProductionLineCRUDSmokeTester productionLineSmokeTester = new ProductionLineCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductionLineCRUD();
        return true;
    }

    private void testProductionLineCRUD() {
        productionLineSmokeTester.testProductionLines();
    }
}
