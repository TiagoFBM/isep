package fabrica.app.bootstrap;

import fabrica.app.common.console.BaseApplication;
import fabrica.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import fabrica.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import fabrica.clientusermanagement.domain.events.SignupAcceptedEvent;
import fabrica.infrastructure.bootstrapers.BaseBootstrapper;
import fabrica.infrastructure.bootstrapers.demo.BaseDemoBootstrapper;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.infrastructure.smoketests.BaseDemoSmokeTester;
import fabrica.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;
import fabrica.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.util.Arrays;

/**
 * Base Bootstrapping data app
 *
 */
@SuppressWarnings("squid:S106")
public final class BaseBootstrap extends BaseApplication {
    /**
     * avoid instantiation of this class.
     */
    private BaseBootstrap() {
    }

    private boolean isToBootstrapDemoData;
    private boolean isToRunSampleE2E;

    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        new BaseBootstrap().run(args);
    }


    @Override
    protected void doMain(final String[] args) {
        handleArgs(args);

        System.out.println("\n\n------- MASTER DATA -------");
        new BaseBootstrapper().execute();

        if (isToBootstrapDemoData) {
            System.out.println("\n\n------- DEMO DATA -------");
            new BaseDemoBootstrapper().execute();
        }
        if (isToRunSampleE2E) {
            System.out.println("\n\n------- BASIC SCENARIO -------");
            new BaseDemoSmokeTester().execute();
        }
    }

    private void handleArgs(final String[] args) {
        isToRunSampleE2E = Arrays.contains(args, "-smoke:basic");
        if (isToRunSampleE2E) {
            isToBootstrapDemoData = true;
        } else {
            isToBootstrapDemoData = Arrays.contains(args, "-bootstrap:demo");
        }
    }

    @Override
    protected String appTitle() {
        return "Bootstrapping Base data ";
    }

    @Override
    protected String appGoodbye() {
        return "Bootstrap data done.";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewUserRegisteredFromSignupWatchDog(), NewUserRegisteredFromSignupEvent.class);
        dispatcher.subscribe(new SignupAcceptedWatchDog(), SignupAcceptedEvent.class);
    }
}
