package fabrica.app.backoffice.console.presentation.machine;

import fabrica.factoryfloor.machinemanagement.domain.Machine;
import eapli.framework.visitor.Visitor;

public class MachinePrinter implements Visitor<Machine> {

    @Override
    public void visit(final Machine visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
