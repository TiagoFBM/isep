package fabrica.production.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.production.domain.RawMaterialCategory;
import fabrica.production.domain.ShortAlfanumericCode;

import java.util.List;

public interface RawMaterialCategoryRepository extends DomainRepository<ShortAlfanumericCode, RawMaterialCategory> {

    List<RawMaterialCategory> findAll();
}
