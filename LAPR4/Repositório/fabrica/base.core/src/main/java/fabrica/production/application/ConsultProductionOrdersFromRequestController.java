package fabrica.production.application;

import fabrica.production.services.ListProductionOrdersService;
import fabrica.productordersmanagement.domain.ProductionOrder;

import java.util.ArrayList;
import java.util.List;

public class ConsultProductionOrdersFromRequestController {

    private final ListProductionOrdersService listProductionOrdersService = new ListProductionOrdersService();

    public List<ProductionOrder> allProductionOrdersFromRequest(String id){
        return new ArrayList<>(listProductionOrdersService.allProductionOrdersFromRequest(id));
    }
}
