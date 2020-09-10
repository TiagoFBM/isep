package fabrica.persistence.impl.jpa;

import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.RawMaterialCategory;
import fabrica.production.domain.ShortAlfanumericCode;
import fabrica.production.repositories.RawMaterialCategoryRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class JpaRawMaterialCategoryRepository extends BasepaRepositoryBase<RawMaterialCategory, Long, ShortAlfanumericCode> implements RawMaterialCategoryRepository {


    public JpaRawMaterialCategoryRepository(){
        super("categoryID");
    }

    @Override
    public List<RawMaterialCategory> findAll(){
        final TypedQuery<RawMaterialCategory> query = super.createQuery("SELECT rmc FROM RawMaterialCategory rmc", RawMaterialCategory.class);
        return query.getResultList();
    }
}
