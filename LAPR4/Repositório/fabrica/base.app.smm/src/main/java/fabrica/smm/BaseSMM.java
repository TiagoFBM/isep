package fabrica.smm;

import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import fabrica.app.common.console.BaseApplication;
import fabrica.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import fabrica.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import fabrica.clientusermanagement.domain.events.SignupAcceptedEvent;
import fabrica.smm.presentation.MonitorProductionLineUI;
import fabrica.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;

public final class BaseSMM extends BaseApplication {

    public static void main(final String[] args) {
        new BaseSMM().run(args);
    }

    @Override
    protected void doMain(String[] args) {
        new MonitorProductionLineUI().show();
    }

    @Override
    protected String appTitle() {
        return "Machine Monitoring System";
    }

    @Override
    protected String appGoodbye() {
        return "Machine Monitoring System";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(),
                NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}
