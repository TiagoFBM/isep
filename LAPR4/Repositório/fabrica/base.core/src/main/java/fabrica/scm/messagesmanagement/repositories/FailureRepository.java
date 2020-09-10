package fabrica.scm.messagesmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.scm.messagesmanagement.domain.Failure;
import fabrica.scm.messagesmanagement.domain.FailureType;

import java.util.Calendar;
import java.util.List;

public interface FailureRepository extends DomainRepository<Long, Failure> {


}
