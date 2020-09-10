package fabrica.persistence.impl.jpa;

import fabrica.scm.messagesmanagement.domain.Failure;
import fabrica.scm.messagesmanagement.repositories.FailureRepository;

public class JpaFailureRepository extends BasepaRepositoryBase<Failure, Long, Long> implements FailureRepository {

    public JpaFailureRepository() {
        super("failureID");
    }




}
