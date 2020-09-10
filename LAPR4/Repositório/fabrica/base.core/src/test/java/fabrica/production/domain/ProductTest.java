package fabrica.production.domain;

import fabrica.production.services.ImportFileToBytesService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    private static final String fabricationCode = "12345";
    private static final String comercialCode = "54321";
    private static final String briefDescription = "briefDescription";
    private static final String fullDescription = "fullDescription";
    private static final String productCategory = "productCategory";
    private static final String unit = "TONNE";

    @Test
    public void testCreateProduct() {
        new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertTrue(true);
    }

    @Test
    public void testProductWithoutProductionSheet() {
        Assert.assertFalse(new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory).hasProductionSheet());
    }

    @Test
    public void testProductWithProductionSheet() {
        Product p = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        p.createProductionSheet(5,Units.UN.toString());
        Assert.assertTrue(p.hasProductionSheet());
    }

    @Test
    public void testProductSameAsSameContent() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit, productCategory);
        Product p2 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit, productCategory);
        Assert.assertTrue(p1.sameAs(p2));
    }

    @Test
    public void testProductSameAsDiferentConcent() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product("123", "123", "breve","completa", "UN", "categoria");
        Assert.assertFalse(p1.sameAs(p2));
    }

    @Test
    public void testProductSameAsFromOtherClass() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        ProductCategory pc = ProductCategory.valueOf("categoria");
        Assert.assertFalse(p1.sameAs(pc));
    }

    @Test
    public void testProductSameAsSameObject() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertTrue(p1.sameAs(p1));
    }

    @Test
    public void testProductEquals2() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product("123","123", "breve","completa","UN", "categoria");
        Assert.assertNotEquals(p1, p2);
    }

    @Test
    public void testProductEquals3() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertEquals(p1, p2);
    }

    @Test
    public void testProductEquals4() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertEquals(p1, p1);
    }

     @Test
     public void testProductSameAs3() {
         Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit, productCategory);
         Product p2 = new Product("123","12345", "breve","completa","UN","Categoria");
         Assert.assertFalse(p1.sameAs(p2));
     }

    @Test
    public void testProductEquals1() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product("123","12345", "breve","completa","UN","Categoria");
        Assert.assertNotEquals(p1, p2);
    }

    @Test
    public void hasIdentity() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertTrue(p1.hasIdentity(AlfanumericCode.valueOf(fabricationCode)));
    }

    @Test
    public void compareToSameIdentity() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product(fabricationCode, "comercial", "desc", "descricao", "UN","categoria");
        Assert.assertEquals(0,p1.compareTo(p2.identity()));
    }

    @Test
    public void compareToDiferentIdentity() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Product p2 = new Product("123", comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertEquals(2,p1.compareTo(p2.identity()));
    }

    @Test
    public void addLineWithProductToProductionSheet() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        p1.createProductionSheet(1,"UN");
        Product p2 = new Product("123","12345", "breve","completa","UN","Categoria");
        p1.addLineToProductionSheet(p2,1,"UN");

        List<ProductionSheetLine> expected = new ArrayList<>();
        expected.add(new ProductionSheetLine(p2,1,"UN"));
        Assert.assertEquals(expected.toString(),p1.obtainProductionSheet().listAllLines().toString());
    }

    @Test
    public void addLineWithRawMaterialToProductionSheet() throws IOException {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        p1.createProductionSheet(1,"UN");


        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes("exemplo.pdf","..//Files"), service.obtainFileFormat("TesteFile.pdf"));
        RawMaterial rm = new RawMaterial("1000","Produção de camisolas",category,ds);


        p1.addLineToProductionSheet(rm,1,"UN");

        List<ProductionSheetLine> expected = new ArrayList<>();
        expected.add(new ProductionSheetLine(rm,1,"UN"));
        Assert.assertEquals(expected.toString(),p1.obtainProductionSheet().listAllLines().toString());
    }

    @Test
    public void updateBriefDescriptionTest() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        String newBriefDesc = "desc";
        p1.updateBriefDescription(newBriefDesc);
        Assert.assertEquals(newBriefDesc,p1.obtainBriefDescription().toString());
    }

    @Test
    public void updateFullDescriptionTest() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        String newFullDesc = "completa";
        p1.updateFullDescription(newFullDesc);
        Assert.assertEquals(newFullDesc,p1.obtainFullDescription().toString());
    }

    @Test
    public void updateCategoryTest() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        String categoria = "categoria";
        p1.updateCategory(categoria);
        Assert.assertEquals(categoria,p1.obtainCategory().toString());
    }

    @Test
    public void updateUnitTest() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        String unidade = "LITERS";
        p1.updateUnit(unidade);
        Assert.assertEquals(unidade,p1.obtainUnit().toString());
    }

    @Test
    public void obtainComercialCodeTest() {
        Product p1 = new Product(fabricationCode, comercialCode, briefDescription, fullDescription, unit,productCategory);
        Assert.assertEquals(AlfanumericCode.valueOf(comercialCode),p1.obtainComercialCode());
    }

}
