package fabrica.scm.messagesmanagement;

import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import fabrica.app.common.console.BaseApplication;
import fabrica.app.common.console.presentation.authz.LoginUI;
import fabrica.app.other.console.presentation.MainMenu;
import fabrica.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import fabrica.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import fabrica.clientusermanagement.domain.events.SignupAcceptedEvent;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.application.MessagesImporterController;
import fabrica.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;

public class BaseSCM extends BaseApplication {

    private static final MessagesImporterController controller = new MessagesImporterController();

    private BaseSCM() {
    }

    public static void main(final String[] args) {
        new BaseSCM().run(args);
    }

    @Override
    protected void doMain(final String[] args) {
        // go to main menu
        final MainMenuSCM menu = new MainMenuSCM();
        menu.mainLoop();
    }

    @Override
    protected String appTitle() {
        return "Base Communication System with Machines";
    }

    @Override
    protected String appGoodbye() {
        return "Base Communication System with Machines";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(),
                NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}