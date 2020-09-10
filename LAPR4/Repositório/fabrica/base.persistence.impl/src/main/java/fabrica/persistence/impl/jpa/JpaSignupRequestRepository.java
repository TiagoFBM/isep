package fabrica.persistence.impl.jpa;

import fabrica.Application;
import fabrica.clientusermanagement.domain.SignupRequest;
import fabrica.clientusermanagement.repositories.SignupRequestRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
class JpaSignupRequestRepository extends JpaAutoTxRepository<SignupRequest, Username, Username>
        implements SignupRequestRepository {

    public JpaSignupRequestRepository(TransactionalContext autoTx) {
        super(autoTx, "username");
    }

    public JpaSignupRequestRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "username");
    }

    @Override
    public Iterable<SignupRequest> pendingSignupRequests() {
        return match(
                "e.approvalStatus=eapli.base.clientusermanagement.domain"
                        + ".ApprovalStatus.PENDING");
    }
}