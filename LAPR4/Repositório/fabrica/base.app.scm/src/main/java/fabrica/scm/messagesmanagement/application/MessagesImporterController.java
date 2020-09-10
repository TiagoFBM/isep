package fabrica.scm.messagesmanagement.application;

import fabrica.scm.messagesmanagement.importer.MessageImporterDeamon;
import fabrica.scm.messagesmanagement.importer.MessageImporterThreads;

public class MessagesImporterController {

    private final MessageImporterThreads threadsService = new MessageImporterThreads();
    private final MessageImporterDeamon deamon = new MessageImporterDeamon();

    public void importMessages(String file) throws InterruptedException {
        try {
            threadsService.importMessages(file);
            System.out.println("-- DEAMON ACTIVATED --");
            deamon.importMessagesDeamon(file);
        } catch(NullPointerException | IllegalArgumentException ex){
            System.out.println("An unexpected erro ocurred, please consult the log.");
        }
    }

}
