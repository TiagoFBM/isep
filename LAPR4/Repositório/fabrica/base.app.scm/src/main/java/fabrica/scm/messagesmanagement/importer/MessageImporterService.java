package fabrica.scm.messagesmanagement.importer;

import fabrica.errorexporter.ErrorExporter;

import java.io.*;
import java.util.Scanner;

public class MessageImporterService implements Runnable {

    private final MessageHandler handler = new MessageHandler();
    private final String filename;
    private final MessageImporter importer;

    public MessageImporterService(String filename, MessageImporter importer) {
        this.filename = filename;
        this.importer = importer;
    }

    @Override
    public void run() {
        try {
            File actualFile = new File("Files/UnverifiedMessages/" + filename);
            File errorFile = new File("Files/TreatedMessages/" + filename + "_Erros");
            ErrorExporter errorExporter = new ErrorExporter(errorFile);
            PrintWriter printTreatedMessages = new PrintWriter(new FileWriter("Files/TreatedMessages/" + filename));

            Scanner reader = new Scanner(actualFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                handler.handleMessage(line, importer, errorExporter, printTreatedMessages);
            }

            printTreatedMessages.close();

            reader.close();
            errorExporter.closeExporter();

            if (!actualFile.delete()) {
                System.out.println("The system couldn't delete file " + actualFile);
            }

            if (countLinesInFile(errorFile) <= 2) {
                if (!errorFile.delete()) {
                    System.out.println("The system couldn't delete file " + errorFile);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countLinesInFile(File file) throws FileNotFoundException {
        int count=0;
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            count++;
        }
        reader.close();
        return count;
    }

}