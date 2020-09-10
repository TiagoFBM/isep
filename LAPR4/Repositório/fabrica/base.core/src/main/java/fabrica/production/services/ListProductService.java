package fabrica.production.services;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.Product;
import fabrica.production.repositories.ProductRepository;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListProductService {

    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<Product> allProducts() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return productRepository.findAll();
    }

    public List<Product> allProductsWithoutProductionSheet(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        return productRepository.findAllWithoutProductionSheet();
    }
}
