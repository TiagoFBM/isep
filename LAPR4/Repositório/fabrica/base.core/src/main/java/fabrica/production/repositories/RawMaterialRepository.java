package fabrica.production.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.RawMaterial;

import java.util.List;

public interface RawMaterialRepository extends DomainRepository<AlfanumericCode, RawMaterial> {
    List<RawMaterial> findAll();
}
