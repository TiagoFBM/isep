package fabrica.scm.messagesmanagement.importer;

import fabrica.errorexporter.ErrorExporter;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.application.MessageParser;
import fabrica.scm.messagesmanagement.domain.Failure;
import fabrica.scm.messagesmanagement.domain.FailureType;
import fabrica.scm.messagesmanagement.domain.Message;
import fabrica.scm.messagesmanagement.exceptions.*;
import fabrica.scm.messagesmanagement.repositories.FailureRepository;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.Date;

public class MessageHandler {

    private final MessageParser parser = new MessageParser();
    private final MessageRepository messageRepository = PersistenceContext.repositories().messages();
    private final FailureRepository failureRepository = PersistenceContext.repositories().failures();

    public void handleMessage(String line, MessageImporter importer, ErrorExporter errorExporter, PrintWriter printTreatedMessages) {

        try {

            String[] dividedLine = importer.splitMessage(line);
            Message m = parser.createMessage(dividedLine);

            if (messageRepository.getAlreadyExistingMessage(m.obtainMachine().identity(), m.obtainDate(), parser.createMessage(dividedLine).getClass())) {
                messageRepository.save(m);
            } else {
                errorExporter.exportError(line);
            }

        } catch (InvalidMessageParameters ex) {
            System.out.println("Was found a message that doesn't have the needed requirements to be treated.");
            errorExporter.exportError(line);
        } catch (InvalidDepositException ex) {
            Failure fail = new Failure(new Date(), FailureType.INVALIDDEPOSIT, line);
            failureRepository.save(fail);
            errorExporter.exportError(line);
        } catch (InvalidMachineException ex) {
            Failure fail = new Failure(new Date(), FailureType.INVALIDMACHINE, line);
            failureRepository.save(fail);
            errorExporter.exportError(line);
        } catch (InvalidProductException ex) {
            Failure fail = new Failure(new Date(), FailureType.INVALIDPRODUCT, line);
            failureRepository.save(fail);
            errorExporter.exportError(line);
        } catch (InvalidProductionOrderException ex) {
            Failure fail = new Failure(new Date(), FailureType.INVALIDPRODUCTIONORDER, line);
            failureRepository.save(fail);
            errorExporter.exportError(line);
        } catch (InvalidProductRawMaterialException ex) {
            Failure fail = new Failure(new Date(), FailureType.INVALIDPRODUCTRAWMATERIAL, line);
            failureRepository.save(fail);
            errorExporter.exportError(line);
        } catch (InvalidParameterException | NumberFormatException ex) {
            errorExporter.exportError(line);
        } finally {

            if (printTreatedMessages != null) {
                printTreatedMessages.print(line);
                printTreatedMessages.println();
            }
        }

    }

}
