package fabrica.scm.messagesmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.production.domain.AlfanumericCode;
import fabrica.scm.messagesmanagement.domain.*;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends DomainRepository<Long, Message>{
    List<Message> findUnprocessedTypeMessagesFromProductionLine(Class type, String plInternalCode);
    List<Message> findUnprocessedTypeMessagesFromProductionLine(Class type, String plInternalCode, Date startTime, Date endTime);
    ActivityStartMessage findInitialActivityStartMessageFromProductionLine(String plInternalCode, Date currentSendDate);
    ConsumptionMessage findLastConsumptionMessageFromDeposit(Date initialDate, Date currentDate, Deposit depositToSearch);
    //Integer sumChargebackedValues
    boolean getAlreadyExistingMessage(AlfanumericCode machineID, Date time, Class msgClass);
}
