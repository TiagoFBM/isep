package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class MachineManagementSmokeTester implements Action {
    final MachineCRUDSmokeTester machineSmokeTester = new MachineCRUDSmokeTester();

    @Override
    public boolean execute() {
        testMachineCRUD();
        return true;
    }

    private void testMachineCRUD() {
        machineSmokeTester.testMachines();
    }
}
