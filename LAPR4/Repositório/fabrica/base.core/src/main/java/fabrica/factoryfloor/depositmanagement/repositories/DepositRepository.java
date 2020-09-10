package fabrica.factoryfloor.depositmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;

import java.util.List;

/**
 *
 * @author Tiago Sousa (1150736)
 */
public interface DepositRepository extends DomainRepository<Designation, Deposit> {
    List<Deposit> findAll();
}
