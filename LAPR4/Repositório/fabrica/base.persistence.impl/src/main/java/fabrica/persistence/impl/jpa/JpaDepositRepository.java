package fabrica.persistence.impl.jpa;

import eapli.framework.general.domain.model.Designation;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.depositmanagement.repositories.DepositRepository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Tiago Sousa
 */
public class JpaDepositRepository extends BasepaRepositoryBase<Deposit, Designation,Designation> implements DepositRepository {
    public JpaDepositRepository() {
        super("internalCode");
    }

    @Override
    public List<Deposit> findAll(){
        final TypedQuery<Deposit> query = super.createQuery("SELECT d FROM Deposit d", Deposit.class);
        return query.getResultList();
    }
}
