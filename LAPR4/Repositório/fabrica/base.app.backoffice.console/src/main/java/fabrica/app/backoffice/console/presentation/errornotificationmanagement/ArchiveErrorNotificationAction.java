/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.app.backoffice.console.presentation.errornotificationmanagement;

import eapli.framework.actions.Action;

/**
 *
 * @author Utilizador
 */
public class ArchiveErrorNotificationAction implements Action {

    @Override
    public boolean execute() {
        return new ArchiveErrorNotificationUI().show();
    }

}
