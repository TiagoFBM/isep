package fabrica.production.domain;

import fabrica.production.services.ImportFileToBytesService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductionSheetTest {

    @Test
    public void createProductionSheetTest() {
        List<ProductionSheetLine> linhas = new ArrayList<>();
        new ProductionSheet(linhas,1,"UN");
        Assert.assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProductionSheetTestError(){
        List<ProductionSheetLine> linhas = new ArrayList<>();
        new ProductionSheet(linhas,1,"NADA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLineWithProductAlreadyExisting() {
        List<ProductionSheetLine> linhas = new ArrayList<>();
        ProductionSheet ps = new ProductionSheet(linhas,1,"UN");

        Product p2 = new Product("123","12345", "breve","completa","UN","Categoria");
        ps.addLine(p2,1,"UN");
        ps.addLine(p2,1,"UN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLineWithRawMaterialAlreadyExisting() throws IOException {
        List<ProductionSheetLine> linhas = new ArrayList<>();
        ProductionSheet ps = new ProductionSheet(linhas,1,"UN");

        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes("TesteFile.pdf","..//Files"), service.obtainFileFormat("TesteFile.pdf"));
        RawMaterial rm = new RawMaterial("1000","Produção de camisolas",category,ds);


        ps.addLine(rm,1,"UN");
        ps.addLine(rm,1,"UN");
    }

}