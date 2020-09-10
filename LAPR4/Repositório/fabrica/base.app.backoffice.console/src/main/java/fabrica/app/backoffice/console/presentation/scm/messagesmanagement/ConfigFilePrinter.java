package fabrica.app.backoffice.console.presentation.scm.messagesmanagement;

import eapli.framework.visitor.Visitor;
import fabrica.factoryfloor.machinemanagement.domain.ConfigurationFile;

public class ConfigFilePrinter implements Visitor<ConfigurationFile> {

    @Override
    public void visit(ConfigurationFile visitee) {
        System.out.printf("%-15s%n", visitee.toString());
    }

}
