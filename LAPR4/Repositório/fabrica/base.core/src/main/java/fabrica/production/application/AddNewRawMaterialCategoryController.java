package fabrica.production.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.RawMaterialCategory;
import fabrica.production.repositories.RawMaterialCategoryRepository;
import fabrica.usermanagement.domain.BaseRoles;

public class AddNewRawMaterialCategoryController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialCategoryRepository rawMaterialCategoryRepository = PersistenceContext.repositories().rawMaterialCategories();

    public RawMaterialCategory registerRawMaterialCategory(String categoryID, String briefDescription){

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        RawMaterialCategory rmc = new RawMaterialCategory(categoryID,briefDescription);
        return this.rawMaterialCategoryRepository.save(rmc);
    }
}
