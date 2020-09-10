package fabrica.scm.messagesmanagement.service;

import fabrica.errorexporter.ErrorExporter;
import fabrica.scm.messagesmanagement.importer.MessageHandler;
import fabrica.scm.messagesmanagement.importer.MessageImporterCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ExportToLogService {

    PrintWriter stream;
    private final MessageHandler handler = new MessageHandler();


    public ExportToLogService(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter("Files/UnverifiedMessages/" + filename));
    }

    public void sendMessage(String m) throws IOException {
        handler.handleMessage(m, new MessageImporterCSV(), new ErrorExporter("Erros_" + m.split(";")[0]),null);
        writeFile(m);
    }


    public void closeFile(){
        stream.close();
    }
    private void writeFile(String msg){
        stream.println();
        stream.printf(msg);
    }

}
