/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.app.backoffice.console.presentation.errornotificationmanagement;

import fabrica.errornotificationmanagement.domain.ErrorNotification;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class ErrorNotificationPrinter implements Visitor<ErrorNotification> {

    @Override
    public void visit(ErrorNotification visitee) {
        System.out.println(visitee.toString());
    }
    
}
