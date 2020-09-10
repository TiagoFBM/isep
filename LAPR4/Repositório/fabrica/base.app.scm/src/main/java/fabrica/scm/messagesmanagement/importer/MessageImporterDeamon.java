package fabrica.scm.messagesmanagement.importer;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageImporterDeamon {

    public void importMessagesDeamon(String pathFile) {
        Thread thread;

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            Path path = Paths.get(pathFile);

            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            while (true) {
                List<Thread> threads = new ArrayList<>();
                WatchKey key = watchService.take();

                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    Path context = (Path) watchEvent.context();
                    String file = context.toFile().toString();

                    if (file.charAt(0) != '.') {
                        MessageImporterService service = new MessageImporterService(file,new MessageImporterCSV());
                        thread = new Thread(service);
                        threads.add(thread);
                        thread.start();
                        System.out.printf("The system has openned file: %s.\n", file);
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
                waitForThreads(threads);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForThreads(List<Thread> threads) throws InterruptedException {
        for (Iterator<Thread> it = threads.iterator(); it.hasNext(); ){
            Thread thread = it.next();
            thread.join();
            System.out.print("The system closed the file.\n");
        }
    }


}
