/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package fabrica.app.other.console;

import fabrica.app.common.console.presentation.authz.LoginAction;
import fabrica.app.other.console.presentation.MainMenu;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.usermanagement.domain.BasePasswordPolicy;
import fabrica.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

/**
 *
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public final class OtherApp {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private OtherApp() {
    }

    public static void main(final String[] args) {
        System.out.println("=====================================");
        System.out.println("Base POS");
        System.out.println("(C) 2016, 2017, 2018");
        System.out.println("=====================================");

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());

        // login and go to main menu
        /*
        if (new LoginAction(BaseRoles.CASHIER).execute()) {
            final MainMenu menu = new MainMenu();
            menu.mainLoop();
        }
         */

        // exiting the application, closing all threads
        System.exit(0);
    }
}
