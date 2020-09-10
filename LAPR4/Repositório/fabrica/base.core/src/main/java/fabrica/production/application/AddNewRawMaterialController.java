package fabrica.production.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.builder.RawMaterialBuilder;
import fabrica.production.domain.*;
import fabrica.production.repositories.RawMaterialRepository;
import fabrica.production.services.ImportFileToBytesService;
import fabrica.production.services.ListRawMaterialCategoriesService;
import fabrica.usermanagement.domain.BaseRoles;

import java.io.IOException;


public class AddNewRawMaterialController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();
    private final ListRawMaterialCategoriesService svc = new ListRawMaterialCategoriesService();
    private final ImportFileToBytesService svcBytes = new ImportFileToBytesService();

    public RawMaterial registerRawMaterial (String code, String description, RawMaterialCategory category, String nameFile, String pathFile) throws IOException {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);

        DataSheet ds = new DataSheet(svcBytes.transformToBytes(nameFile, pathFile), svcBytes.obtainFileFormat(nameFile));

        final RawMaterialBuilder builder = new RawMaterialBuilder();
        builder.withCode(code);
        builder.withDescription(description);
        builder.withCategory(category);
        builder.withDataSheet(ds);
        RawMaterial rm = builder.build();
        return this.rawMaterialRepository.save(rm);
    }

    public Iterable<RawMaterialCategory> rmCategories() {
        return this.svc.allCategories();
    }
}
