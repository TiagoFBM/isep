package fabrica.spm;

import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import fabrica.app.common.console.BaseApplication;
import fabrica.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import fabrica.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import fabrica.clientusermanagement.domain.events.SignupAcceptedEvent;
import fabrica.spm.messageprocessing.application.ProcessAvailableMessagesController;
import fabrica.spm.presentation.ProcessAvailableMessagesUI;
import fabrica.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;

public class BaseSPM extends BaseApplication {
    public static void main(String[] args) {
        new BaseSPM().run(args);
    }

    @Override
    protected void doMain(String[] args) {
        new ProcessAvailableMessagesUI().show();
    }

    @Override
    protected String appTitle() {
        return "Message Processment Service";
    }

    @Override
    protected String appGoodbye() {
        return "Message Processment Service";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(),
                NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}
