/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.app.backoffice.console.presentation.errornotificationmanagement;

import fabrica.errornotificationmanagement.application.ArchiveErrorNotificationController;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 *
 * @author Tiago Sousa
 */
public class ArchiveErrorNotificationUI extends AbstractUI{
    private final ArchiveErrorNotificationController theController= new ArchiveErrorNotificationController();
    
    /*protected Controller controller() {
        return this.theController;
    }*/


    @Override
    protected boolean doShow() {
        final Iterable<ErrorNotification> errorNotifications = theController.getUntreatedErrorNotifications();
          final SelectWidget<ErrorNotification> selector = new SelectWidget<>("Error notifications: ", errorNotifications,
                new ErrorNotificationPrinter());
          selector.show();
          final ErrorNotification theErrorNotification=selector.selectedElement();
           if(theErrorNotification == null) return false;
           theController.archiveErrorNotification(theErrorNotification);
          return false;
    }

    @Override
    public String headline() {
        return "Archive error notification";
    }
    
}
