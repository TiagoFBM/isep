/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.errornotificationmanagement.application;

import eapli.framework.application.UseCaseController;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.infrastructure.persistence.PersistenceContext;

/**
 *
 * @author Tiago Sousa
 */
@UseCaseController
public class ArchiveErrorNotificationController {
    
    private ErrorNotificationRepository repository= PersistenceContext.repositories().errornotification();
    
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        return repository.getUntreatedErrorNotifications();   
    }
    
    public void archiveErrorNotification(ErrorNotification errorNotification) {
        errorNotification.archive();
        repository.save(errorNotification);
    }
}
