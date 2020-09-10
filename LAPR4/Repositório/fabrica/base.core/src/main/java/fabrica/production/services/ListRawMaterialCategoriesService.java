package fabrica.production.services;

import fabrica.production.domain.RawMaterialCategory;
import fabrica.production.repositories.RawMaterialCategoryRepository;

import fabrica.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class ListRawMaterialCategoriesService {
    private final RawMaterialCategoryRepository categoriesRepository = PersistenceContext.repositories().rawMaterialCategories();

    public List<RawMaterialCategory> allCategories() {return categoriesRepository.findAll();}
}
