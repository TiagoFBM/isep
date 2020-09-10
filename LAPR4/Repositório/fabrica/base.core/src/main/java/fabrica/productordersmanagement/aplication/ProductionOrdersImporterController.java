package fabrica.productordersmanagement.aplication;

import fabrica.errorexporter.ErrorExporter;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.importer.*;
import fabrica.productordersmanagement.domain.ProductionOrder;
import fabrica.productordersmanagement.importer.ProductionOrderImporter;
import fabrica.productordersmanagement.importer.ProductionOrderImporterFactory;
import fabrica.productordersmanagement.importer.ProductionOrderImporterService;
import fabrica.productordersmanagement.repositories.ProductionOrderRepository;
import java.io.IOException;
import java.util.List;

public class ProductionOrdersImporterController {

    private final ProductionOrderImporterFactory importerFactory = new ProductionOrderImporterFactory();
    private final ProductionOrderImporterService importerService = new ProductionOrderImporterService();
    private final ProductionOrderRepository repo = PersistenceContext.repositories().productionsOrders();

    private int imported=0;
    private int withErrors=0;
    private int updated=0;
    private int keept=0;

    public List<ProductionOrder> importProductionOrders(String filename, String errorFile, FileFormat format) throws IOException {
        final ProductionOrderImporter importer = importerFactory.build(format);
        final ErrorExporter errorExporter = new ErrorExporter(errorFile,format);
        List<ProductionOrder> orders =  importerService.importer(filename, importer,errorExporter);
        imported = importerService.getNumberImported();
        withErrors = importerService.getNumberWithErrors();
        return orders;
    }

    public ProductionOrder searchRepeatedProductionOrder(AlfanumericCode orderID){
        if(repo.ofIdentity(orderID).isPresent()){
            return repo.ofIdentity(orderID).get();
        }
        return null;
    }

    public ProductionOrder updateProductionOrder(ProductionOrder p){
        this.updated++;
        return repo.save(p);
    }

    public void keepProductionOrder(){
        this.keept++;
    }

    public String importationReport(){
        return String.format("Imported Production Orders: %d%nUpdated Production Orders: %d%nKept Production Orders: %d%nProduction Orders With Errors: %d%n",this.imported,this.updated,this.keept,this.withErrors);
    }

}
