package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class ProductManagementSmokeTester implements Action {
    final ProductCRUDSmokeTester productCRUDSmokeTester = new ProductCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductCRUD();
        return true;
    }

    private void testProductCRUD() {
        productCRUDSmokeTester.testProducts();
    }

}
