package fabrica.persistence.impl.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fabrica.Application;
import fabrica.clientusermanagement.domain.ClientUser;
import fabrica.clientusermanagement.domain.MecanographicNumber;
import fabrica.clientusermanagement.repositories.ClientUserRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
class JpaClientUserRepository
        extends JpaAutoTxRepository<ClientUser, MecanographicNumber, MecanographicNumber>
        implements ClientUserRepository {

    public JpaClientUserRepository(TransactionalContext autoTx) {
        super(autoTx, "mecanographicNumber");
    }

    public JpaClientUserRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "mecanographicNumber");
    }

    @Override
    public Optional<ClientUser> findByUsername(Username name) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return matchOne("e.systemUser.username=:name", params);
    }

    @Override
    public Optional<ClientUser> findByMecanographicNumber(MecanographicNumber number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        return matchOne("e.mecanographicNumber=:number", params);
    }

    @Override
    public Iterable<ClientUser> findAllActive() {
        return match("e.systemUser.active = true");
    }
}
