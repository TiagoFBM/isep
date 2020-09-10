package fabrica.persistence.impl.jpa;

import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.domain.RawMaterial;
import fabrica.production.repositories.RawMaterialRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class JpaRawMaterialRepository extends BasepaRepositoryBase<RawMaterial, Long, AlfanumericCode>
        implements RawMaterialRepository {

    public JpaRawMaterialRepository() {
        super("code");
    }

    @Override
    public List<RawMaterial> findAll(){
        final TypedQuery<RawMaterial> query = super.createQuery("SELECT r FROM RawMaterial r", RawMaterial.class);
        return query.getResultList();
    }
}