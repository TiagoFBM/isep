package fabrica.production.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.RawMaterial;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListRawMaterialService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();

    public List<RawMaterial> allRawMaterials() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return rawMaterialRepository.findAll();
    }
}
