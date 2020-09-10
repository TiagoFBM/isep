package fabrica.scm.messagesmanagement.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageImporterThreads {

    private List<String> setupThreads(String pathFile) {

        File f = new File(pathFile);
        String[] pathnames = f.list();
        List<String> validFiles = new ArrayList<>();

        for (String path : pathnames) {
            if (path.charAt(0) != '.') {
                validFiles.add(path);
            }
        }

        return validFiles;
    }

    public void importMessages(String pathFile) throws InterruptedException {
        List<String> files = setupThreads(pathFile);
        System.out.println("There are "+files.size()+" files in the directory to be imported.");
        Thread[] threadVector = new Thread[files.size()];
        int i = 0;
        for (String file : files) {
            if (file.charAt(0) != '.') {
                MessageImporterService service = new MessageImporterService(file,new MessageImporterCSV());
                threadVector[i] = new Thread(service);
                threadVector[i].start();
                System.out.printf("The system has openned file: %s.\n", file);
                i++;
            }
        }
        waitForThreads(threadVector);
    }

    private void waitForThreads(Thread[] theads) throws InterruptedException {
        for (int i = 0; i < theads.length; i++) {
            theads[i].join();
            System.out.print("The system closed the file.\n");
        }
    }

}
