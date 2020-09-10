package fabrica.scm.messagesmanagement.importer;

import java.io.IOException;

public interface MessageImporter {

    String[] splitMessage(String filename);

    String separator();

}
