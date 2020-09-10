package fabrica.errornotificationmanagement.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.errornotificationmanagement.domain.ErrorNotification;
import fabrica.errornotificationmanagement.domain.ErrorType;
import fabrica.errornotificationmanagement.repository.ErrorNotificationRepository;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.repositories.ProductionLineRepository;
import fabrica.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class ShowErrorNotificationsUntreated {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ErrorNotificationRepository repository = PersistenceContext.repositories().errornotification();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLine();

    public Iterable<ProductionLine> getAllProductionLines() {
        return productionLineRepository.findAll();
    }


    public List<ErrorNotification> findByMachineUntreatedMessagess(Machine machine) {
        return repository.findOrderByMachineUntreatedMessages(machine);
    }

    public List<ErrorNotification> findByErrorTypeUntreatedMessages(ErrorType errorType) {
        return repository.findOrderByErrorTypeUntreatedMessages(errorType);
    }
}
