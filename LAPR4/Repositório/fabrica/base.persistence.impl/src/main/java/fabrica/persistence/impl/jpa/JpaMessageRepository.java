package fabrica.persistence.impl.jpa;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.AlfanumericCode;
import fabrica.scm.messagesmanagement.domain.*;
import fabrica.scm.messagesmanagement.repositories.MessageRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JpaMessageRepository extends BasepaRepositoryBase<Message, Long, Long> implements MessageRepository {

    public JpaMessageRepository() {
        super("id");
    }

    @Override
    public List<Message> findUnprocessedTypeMessagesFromProductionLine(Class type, String plInternalCode) {
        final Query query = super.entityManager().createNativeQuery("SELECT m.* From " + type.getSimpleName() + " m where m.messagestate != 0 AND m.machine_pk IN (SELECT plm.machines_pk FROM productionline_machine plm WHERE plm.productionline_pk = (select pl.pk FROM productionline pl WHERE pl.internalcode = :plInternalCode))", type);
        query.setParameter("plInternalCode", plInternalCode);
        return query.getResultList();
    }

    @Override
    public List<Message> findUnprocessedTypeMessagesFromProductionLine(Class type, String plInternalCode, Date startTime, Date endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        final Query query = super.entityManager().createNativeQuery("SELECT m.* From " + type.getSimpleName() + " m where m.messagestate != 0 AND m.senddatetime >= :startTime AND m.senddatetime <= :endTime AND m.machine_pk IN (SELECT plm.machines_pk FROM productionline_machine plm WHERE plm.productionline_pk = (select pl.pk FROM productionline pl WHERE pl.internalcode = :plInternalCode))", type);
        query.setParameter("plInternalCode", plInternalCode);
        query.setParameter("startTime", format.format(startTime));
        query.setParameter("endTime", format.format(endTime));
        return query.getResultList();
    }

    @Override
    public ActivityStartMessage findInitialActivityStartMessageFromProductionLine(String plInternalCode, Date currentSendDate) {
        final Query query = super.entityManager().createNativeQuery("SELECT asm.* FROM activitystartmessage asm WHERE asm.senddatetime < :lastSendDateTime AND asm.machine_pk IN (SELECT plm.machines_pk FROM productionline_machine plm WHERE plm.productionline_pk = (SELECT pk FROM productionline WHERE internalcode = :plInternalCode)) AND productionorder_pk IS NOT NULL ORDER BY asm.SENDDATETIME DESC LIMIT 1", ActivityStartMessage.class);
        query.setParameter("plInternalCode", plInternalCode);
        query.setParameter("lastSendDateTime", currentSendDate.toString());
        return (ActivityStartMessage) query.getResultList().get(0);
    }

    @Override
    public ConsumptionMessage findLastConsumptionMessageFromDeposit(Date initialDate, Date currentDate, Deposit depositToSearch) {
        final Query query = super.entityManager().createNativeQuery("SELECT cm.* From consumptionmessage cm WHERE cm.sendDateTime BETWEEN :startDateTime AND :currentDateTime AND cm.DEPOSIT_PK = (SELECT pk From deposit where internalCode = :currentDeposit) ORDER BY cm.SENDDATETIME DESC LIMIT 1", ConsumptionMessage.class);
        query.setParameter("startDateTime", initialDate.toString());
        query.setParameter("currentDateTime", currentDate.toString());
        query.setParameter("currentDeposit", depositToSearch.identity().toString());
        return (ConsumptionMessage) query.getResultList().get(0);
    }

    @Override
    public boolean getAlreadyExistingMessage(AlfanumericCode machineID, Date time, Class msgClass) {
        final Query query = super.entityManager().createNativeQuery("SELECT m.* FROM " + msgClass.getSimpleName() + " m where m.machine_pk = (Select ma.pk from machine ma where ma.code  = :mc) AND m.senddatetime = :timeToWatch", msgClass);
        query.setParameter("mc", machineID.toString());
        query.setParameter("timeToWatch", time.toInstant());
        return query.getResultList().isEmpty();
    }


}
