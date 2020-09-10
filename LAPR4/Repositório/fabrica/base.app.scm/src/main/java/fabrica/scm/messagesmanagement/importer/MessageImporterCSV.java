package fabrica.scm.messagesmanagement.importer;

public class MessageImporterCSV implements MessageImporter {

    @Override
    public String[] splitMessage(String filename) {
        return filename.split(separator());
    }

    @Override
    public String separator() {
        return ";";
    }
}
