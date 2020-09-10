package fabrica.errornotificationmanagement.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.scm.messagesmanagement.domain.Failure;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.Calendar;
import java.util.List;

public class QueryArchivedErrorsService {
    private final ErrorNotificationRepository repository = PersistenceContext.repositories().errornotification();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<ErrorNotification> allArchivedErrorsByDate(Calendar beginDate, Calendar endDate){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return repository.findAllArchivedErrorsByDate(beginDate, endDate);
    }

    public List<ErrorNotification> allArchivedErrorsWithErrorType(String type){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return repository.findAllArchivedErrorsWithErrorType(type);
    }


}