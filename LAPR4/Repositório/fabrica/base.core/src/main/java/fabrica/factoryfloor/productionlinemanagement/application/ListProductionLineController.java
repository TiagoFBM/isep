package fabrica.factoryfloor.productionlinemanagement.application;

import fabrica.factoryfloor.productionlinemanagement.domain.ProductionLine;
import fabrica.factoryfloor.productionlinemanagement.services.ListProductionLineService;

public class ListProductionLineController {

    final ListProductionLineService svc= new ListProductionLineService();

    /**
     * All raw materials
     * @return all raw materials
     */
    public Iterable<ProductionLine> allProductionLines(){
        return this.svc.allProductionLines();
    }
}
