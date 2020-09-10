/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.errornotificationmanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.scm.messagesmanagement.domain.Failure;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Tiago Sousa
 */
public interface ErrorNotificationRepository extends DomainRepository<Long, ErrorNotification>{
    Iterable<ErrorNotification> findByTime();
    Iterable<ErrorNotification> findByMachine(Machine machines);
    Iterable<ErrorNotification> getUntreatedErrorNotifications();
    List<ErrorNotification> findOrderByMachineUntreatedMessages(Machine machine);
    List<ErrorNotification> findOrderByErrorTypeUntreatedMessages(ErrorType errorType);

    List<ErrorNotification> findAllArchivedErrorsByDate(Calendar beginDate, Calendar endDate);
    List<ErrorNotification> findAllArchivedErrorsWithErrorType(String type);
}