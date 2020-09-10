/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrica.persistence.impl.inmemory;

/**
 *
 * @author Utilizador
 */
/*public class InMemoryErrorNotificationRepository extends InMemoryDomainRepository<Long, ErrorNotification> implements ErrorNotificationRepository {

    static {
        InMemoryInitializer.init();
    }

    public Iterable<ErrorNotification> findByType(ErrorNotificationType type) {
        return match(e -> e.hasNotificationType(type));
    }

    @Override
    public Iterable<ErrorNotification> findByTime() {
        return findAll();
    }

    @Override
    public Iterable<ErrorNotification> findByMachine(Machine machine) {
        return match(e -> e.hasMachine(machine));
    }

    @Override
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        return match(e->e.hasState(StateEnum.UNTREATED));
    }

    @Override
    public List<ErrorNotification> findOrderByMachineUntreatedMessages(Machine machine) {
        return null;
    }

    @Override
    public List<ErrorNotification> findOrderByErrorTypeUntreatedMessages(Description errorType) {
        return null;
    }

}
*/