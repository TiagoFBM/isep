package fabrica.production.application;

import fabrica.production.domain.Product;
import fabrica.production.services.ListProductService;


import java.util.ArrayList;
import java.util.List;

public class SearchProductsWithoutProductionSheetController {

    private final ListProductService listProductService = new ListProductService();

    public List<Product> allProductsWithoutSheetOfProduction(){

        return new ArrayList<>(listProductService.allProductsWithoutProductionSheet());
    }
}
