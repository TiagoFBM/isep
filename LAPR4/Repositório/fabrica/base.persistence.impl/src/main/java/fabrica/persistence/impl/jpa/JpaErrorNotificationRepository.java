/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.persistence.impl.jpa;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.domain.StateEnum;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Tiago Sousa
 */
public class JpaErrorNotificationRepository extends BasepaRepositoryBase<ErrorNotification,Long,Long> implements ErrorNotificationRepository {
    
    public JpaErrorNotificationRepository() {
        super("id");
    }

    public Iterable<ErrorNotification> findByTime(){
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e From ErrorNotification e where e.errorNotificationState = :state order by e.dateTime", ErrorNotification.class);
        query.setParameter("state", StateEnum.ARCHIVED);
        return query.getResultList();
    }

    public Iterable<ErrorNotification> findByMachine(Machine machine){
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e from ErrorNotification e where e.machine = :machine and e.errorNotificationState = :state",ErrorNotification.class);
        query.setParameter("state", StateEnum.ARCHIVED);
        query.setParameter("machine",machine);
        return query.getResultList();
    }

    @Override
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e from ErrorNotification e where e.errorNotificationState = :state",ErrorNotification.class);
        query.setParameter("state", StateEnum.UNTREATED);
        return query.getResultList();
    }

    @Override
    public List<ErrorNotification> findOrderByMachineUntreatedMessages(Machine machine) {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery("SELECT e FROM ErrorNotification e where e.machine= :machine and e.errorNotificationState = :state", ErrorNotification.class);
        query.setParameter("machine", machine);
        query.setParameter("state", StateEnum.UNTREATED);
        return query.getResultList();
    }

    @Override
    public List<ErrorNotification> findOrderByErrorTypeUntreatedMessages(ErrorType errorType) {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery("SELECT e FROM ErrorNotification e where e.errorNotificationType = :errorType and e.errorNotificationState = :state", ErrorNotification.class);
        query.setParameter("errorType", errorType);
        query.setParameter("state", StateEnum.UNTREATED);
        return query.getResultList();
    }

    @Override
    public List<ErrorNotification> findAllArchivedErrorsByDate(Calendar beginDate, Calendar endDate) {

        final Query query = super.entityManager().createNativeQuery("SELECT E.* FROM ErrorNotification E WHERE E.DATETIME BETWEEN :initD AND :findD AND E.errorNotificationState = :state", ErrorNotification.class);
        query.setParameter("initD", beginDate);
        query.setParameter("findD", endDate);
        query.setParameter("state", StateEnum.ARCHIVED);
        return query.getResultList();
    }


    @Override
    public List<ErrorNotification> findAllArchivedErrorsWithErrorType(String type) {
        final Query query = super.entityManager().createNativeQuery("SELECT E.* FROM ErrorNotification E WHERE E.errorNotificationType = :t AND E.errorNotificationState = :state", ErrorNotification.class);
        query.setParameter("t", type);
        query.setParameter("state", StateEnum.ARCHIVED);
        return query.getResultList();
    }


}