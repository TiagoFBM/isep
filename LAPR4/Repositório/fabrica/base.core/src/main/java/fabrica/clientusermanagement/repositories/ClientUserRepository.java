package fabrica.clientusermanagement.repositories;

import java.util.Optional;

import fabrica.clientusermanagement.domain.ClientUser;
import fabrica.clientusermanagement.domain.MecanographicNumber;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public interface ClientUserRepository
        extends DomainRepository<MecanographicNumber, ClientUser> {

    /**
     * returns the client user (utente) whose username is given
     *
     * @param name
     *            the username to search for
     * @return
     */
    Optional<ClientUser> findByUsername(Username name);

    /**
     * returns the client user (utente) with the given mecanographic number
     *
     * @param number
     * @return
     */
    default Optional<ClientUser> findByMecanographicNumber(MecanographicNumber number) {
        return ofIdentity(number);
    }

    public Iterable<ClientUser> findAllActive();
}
