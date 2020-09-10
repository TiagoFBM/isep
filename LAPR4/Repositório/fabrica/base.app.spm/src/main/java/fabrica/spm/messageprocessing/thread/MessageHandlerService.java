package fabrica.spm.messageprocessing.thread;

import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.domain.*;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MessageHandlerService implements Runnable {
    MessageRepository mRepo = PersistenceContext.repositories().messages();

    private final String plInternalCode;
    private Date startTime = null;
    private Date endTime = null;



    public MessageHandlerService(String plInternalCode) {
        this.plInternalCode = plInternalCode;
    }

    public MessageHandlerService(String plInternalCode, Date startTime, Date endTime) {
        this.plInternalCode = plInternalCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void run() {
        List<Message> messageList;
        if (startTime == null) {
            messageList = obtainMessagesFromProductionLine(plInternalCode);
        } else {
            messageList = obtainMessagesFromProductionLine(plInternalCode, startTime, endTime);
        }
        for (Message m : messageList) {
            if (m.hasErrorState()) {
                System.out.println("The message has as an error, can't process messages any further.");
                break;
            }
            boolean success = m.processMessage(plInternalCode);
            if (!success) {
                break;
            }
        }
    }

    private List<Message> obtainMessagesFromProductionLine(String plInternalCode) {
        List<Message> list = new LinkedList<>();
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityEndMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityResumptionMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityStartMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ChargebackMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ConsumptionMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ForcedStopMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ProductionDeliveryMessage.class, plInternalCode));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ProductionMessage.class, plInternalCode));
        list.sort(Comparator.comparing(Message::obtainDate));
        return list;
    }

    private List<Message> obtainMessagesFromProductionLine(String plInternalCode, Date startTime, Date endTime) {
        List<Message> list = new LinkedList<>();
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityEndMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityResumptionMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ActivityStartMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ChargebackMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ConsumptionMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ForcedStopMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ProductionDeliveryMessage.class, plInternalCode, startTime, endTime));
        list.addAll(mRepo.findUnprocessedTypeMessagesFromProductionLine(ProductionMessage.class, plInternalCode, startTime, endTime));
        list.sort(Comparator.comparing(Message::obtainDate));
        return list;
    }
}
