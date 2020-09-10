package fabrica.production.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;

import java.util.List;

public interface ProductRepository extends DomainRepository<AlfanumericCode, Product> {

    List<Product> findAll();

    Product findByComercialCode(AlfanumericCode comercialCode);

    List<Product> findAllWithoutProductionSheet();

}
