package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

import java.io.IOException;

public class RawMaterialManagementSmokeTester implements Action {

    final RawMaterialCRUDSmokeTester rawMaterialSmokeTester = new RawMaterialCRUDSmokeTester();

    @Override
    public boolean execute() {
        try {
            testRawMaterialCRUD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void testRawMaterialCRUD() throws IOException {
        rawMaterialSmokeTester.testRawMaterials();
    }
}
