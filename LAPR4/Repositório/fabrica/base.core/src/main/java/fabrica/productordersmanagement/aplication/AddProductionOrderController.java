package fabrica.productordersmanagement.aplication;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.Product;
import fabrica.production.services.ListProductService;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.usermanagement.domain.BaseRoles;

import java.util.Calendar;
import java.util.List;

public class AddProductionOrderController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderRepository poRepository = PersistenceContext.repositories().productionsOrders();
    private final ListProductService productService = new ListProductService();

    public ProductionOrder registerProductionOrder(String idCode, Calendar emissionDate, Calendar previsionDate, Product p, int quant, String unity, List<String> orders){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        ProductionOrder po = new ProductionOrder(idCode,emissionDate,previsionDate,p,quant,unity, orders);
        return poRepository.save(po);
    }

    public List<Product> products(){
        return this.productService.allProducts();
    }

}
