package fabrica.production.application;

import fabrica.errorexporter.ErrorExporter;
import fabrica.infrastructure.persistence.PersistenceContext;
import fabrica.production.domain.AlfanumericCode;
import fabrica.production.domain.Product;
import fabrica.production.importer.*;
import fabrica.production.repositories.ProductRepository;

import java.io.IOException;
import java.util.List;

public class ProductImporterController {

    private final ProductImporterFactory importerFactory = new ProductImporterFactory();
    private final ProductImporterService productImporterService = new ProductImporterService();
    private final ProductRepository repo = PersistenceContext.repositories().products();

    private int imported=0;
    private int withErrors=0;
    private int updated=0;
    private int keept=0;

    public List<Product> importProducts(String filename,String errorFile, FileFormat format) throws IOException {
        final ProductImporter importer = importerFactory.build(format);
        final ErrorExporter errorExporter = new ErrorExporter(errorFile,format);
        List<Product> p =  productImporterService.importer(filename, importer,errorExporter);
        imported = productImporterService.getNumberImported();
        withErrors = productImporterService.getNumberWithErrors();
        return p;
    }

    public Product searchRepeatedProduct(AlfanumericCode fabricationCode, AlfanumericCode comercialCode){
        if(repo.ofIdentity(fabricationCode).isPresent()){
            return repo.ofIdentity(fabricationCode).get();
        } else if(repo.findByComercialCode(comercialCode)!=null){
            return repo.findByComercialCode(comercialCode);
        }
        return null;
    }

    public Product updateProduct(Product p){
        this.updated++;
        return repo.save(p);
    }

    public void keepProduct(){
        this.keept++;
    }

    public String importationReport(){
        return String.format("Imported Products: %d%nUpdated Products: %d%nKept Products: %d%nProducts With Errors: %d%n",this.imported,this.updated,this.keept,this.withErrors);
    }


}
