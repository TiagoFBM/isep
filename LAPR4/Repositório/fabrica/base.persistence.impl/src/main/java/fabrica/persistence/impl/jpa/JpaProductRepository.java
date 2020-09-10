package fabrica.persistence.impl.jpa;

import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.repositories.ProductRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaProductRepository extends BasepaRepositoryBase<Product, Long, AlfanumericCode>
        implements ProductRepository {

    public JpaProductRepository() {
        super("fabricationCode");
    }

    @Override
    public List<Product> findAll(){
        final TypedQuery<Product> query = super.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Product findByComercialCode(AlfanumericCode comercialCode) {
        try {
            final TypedQuery<Product> query = super.createQuery("SELECT p FROM Product p WHERE p.comercialCode = :cc", Product.class);
            query.setParameter("cc", comercialCode);
            return query.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Product> findAllWithoutProductionSheet() {
        final TypedQuery<Product> query = super.createQuery("SELECT p FROM Product p WHERE p.productionSheet = null", Product.class);
        return query.getResultList();
    }
}