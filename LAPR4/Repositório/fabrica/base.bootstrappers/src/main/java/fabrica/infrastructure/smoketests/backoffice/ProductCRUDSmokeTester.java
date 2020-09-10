package fabrica.infrastructure.smoketests.backoffice;

import eapli.framework.validations.Invariants;

import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.repositories.ProductRepository;

import java.util.logging.Logger;

public class ProductCRUDSmokeTester {

    private static final Logger LOGGER = Logger.getLogger(ProductCRUDSmokeTester.class.getName());
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public void testProducts() {
        //save
        productRepository.save(new Product("aBC123ASD", "abc1", "material", "longmaterial", "Litters", "category"));
        productRepository.save(new Product("GADSDF123", "abc2", "material2",  "longmaterial2", "KILOGRAMS", "category2"));
        LOGGER.info(" created products");

        //findall
        final Iterable<Product> l = productRepository.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all products");

        //count
        final long n = productRepository.count();
        LOGGER.info("»»» # products = " + n);

        //ofIdentity
        AlfanumericCode af = AlfanumericCode.valueOf("aBC123ASD");
        final Product p1 = productRepository.ofIdentity(AlfanumericCode.valueOf("aBC123ASD")).orElseThrow(IllegalStateException::new);
        final Product p2 = productRepository.ofIdentity(AlfanumericCode.valueOf("GADSDF123")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found products with identity");

        // containsOfIdentity
        final boolean id1 = productRepository.containsOfIdentity(p1.identity());
        final boolean id2 = productRepository.containsOfIdentity(p2.identity());
        Invariants.ensure(id1);
        Invariants.ensure(id2);
        LOGGER.info("»»» contains product of identity");

        // contains
        final boolean has1 = productRepository.contains(p1);
        final boolean has2 = productRepository.contains(p2);
        Invariants.ensure(has1);
        Invariants.ensure(has2);
        LOGGER.info("»»» contains product");

        // delete
        productRepository.delete(p1);
        LOGGER.info("»»» delete product");

        // deleteOfIdentity
        productRepository.deleteOfIdentity(p2.identity());
        LOGGER.info("»»» delete product of identity");

        // size
        final long n2 = productRepository.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # product = " + n2);
    }
}
