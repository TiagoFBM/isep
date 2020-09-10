package fabrica.production.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.*;
import fabrica.production.repositories.ProductRepository;
import fabrica.usermanagement.domain.BaseRoles;

public class AddNewProductController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public Product registerProduct(String fabricationCode, String comercialCode, String briefDescription, String fullDescription, String unit, String productCategory) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        Product p = new Product(fabricationCode,comercialCode,briefDescription,fullDescription,unit,productCategory);
        return this.productRepository.save(p);
    }
}
