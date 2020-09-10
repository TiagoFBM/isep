/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.errornotificationmanagement.application;

import eapli.framework.general.domain.model.Description;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.infrastructure.persistence.PersistenceContext;

import java.util.Date;

/**
 *
 * @author Utilizador
 */
public class CreateErrorNotificationController {
    
    private ErrorNotificationRepository repository= PersistenceContext.repositories().errornotification();

    public ErrorNotification registerErrorNotification(Machine machine, ErrorType errorNotificationType, Date dateTime, Long messageID, Class<?> type) {
        ErrorNotification errorNotification = new ErrorNotification(machine, errorNotificationType, dateTime, messageID, type);
        return this.repository.save(errorNotification);
    }   
    
}
