package fabrica.scm.messagesmanagement.presentation;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import fabrica.scm.messagesmanagement.application.MessagesImporterController;

public class MessageImporterUI extends AbstractUI {

    MessagesImporterController controller = new MessagesImporterController();

    @Override
    protected boolean doShow() {
        String path = Console.readNonEmptyLine("Insert path to file: ","Please insert a valid path.");
        try {
            controller.importMessages(path);
        } catch (InterruptedException e) {
            System.out.println("An error was found. Please check the log.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * The title of the "window"
     *
     * @return the title of the window
     */
    @Override
    public String headline() {
        return "Message Importer";
    }
}
