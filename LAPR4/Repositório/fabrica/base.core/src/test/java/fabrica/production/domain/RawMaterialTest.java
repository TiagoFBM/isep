package fabrica.production.domain;

import fabrica.production.services.ImportFileToBytesService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class RawMaterialTest {

    private final static String rawMaterialCode = "1000";
    private final static String rawMaterialCodeEmpty = "";
    private final static String rawMaterialCodeMore = "RM10000000000000";
    private final static String descriptionRawMaterial = "Produção de camisolas";
    private final static String descriptionRawMaterialEmpty = "";
    private final static String descriptionRawMaterialMore = "Produção de camisolas e meias para serem exportadas para todo o mundo. ";
    private final static String nameFile = "exemplo.pdf";
    private final static String pathFile = "..//Files";

    @Test
    public void testCreateRawMaterial() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithCodeNull() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(null,descriptionRawMaterial,category,ds);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithCodeWithMoreCharacters() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCodeMore,descriptionRawMaterial,category,ds);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithCodeEmpty() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCodeEmpty,descriptionRawMaterialEmpty,category,ds);
    }

    @Test
    public void testObtainRawMaterialCode() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertEquals(rm.identity(),AlfanumericCode.valueOf(rawMaterialCode));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithDescriptionNull() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCode,null,category,ds);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithDescriptionEmpty() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCode,descriptionRawMaterialEmpty,category,ds);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithDescriptionMore() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCode,descriptionRawMaterialMore,category,ds);
    }

    @Test
    public void testObtainRawMaterialDescription1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertTrue((rm.obtainDescription().equals(FullDescription.valueOf(descriptionRawMaterial))));
    }

    @Test
    public void testObtainRawMaterialDescription2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertFalse((rm.obtainDescription().equals(FullDescription.valueOf("Produção de calças"))));
    }

    @Test
    public void testUpdateRawMaterialDescription1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        rm.updateDescription("Produção de camisolas");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testUpdateRawMaterialDescription2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        rm.updateDescription("");
    }

    @Test
    public void testCreateRawMaterialWithCategoryNull() throws IOException {
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        new RawMaterial(rawMaterialCode, descriptionRawMaterial, null, ds);
    }

    @Test
    public void testObtainCategory() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertEquals(rm.obtainCategory(), category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialWithDataSheetNull(){
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        new RawMaterial(rawMaterialCode,descriptionRawMaterial,category, null);
    }

    @Test
    public void testObtainDataSheet() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertEquals(rm.obtainDataSheet(), ds.obtainBytes());
    }

    @Test
    public void testSameAs1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        RawMaterial rm2 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertTrue(rm1.sameAs(rm2));
    }
    @Test
    public void testSameAs2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        RawMaterial rm2 = new RawMaterial("1001","Produção de meias",category,ds);
        Assert.assertFalse(rm1.sameAs(rm2));
    }

    @Test
    public void testHasIdentity1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        AlfanumericCode codeTest = AlfanumericCode.valueOf("1000");
        Assert.assertTrue(rm1.hasIdentity(codeTest));
    }

    @Test
    public void testHasIdentity2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        AlfanumericCode codeTest = AlfanumericCode.valueOf("1001");
        Assert.assertFalse(rm1.hasIdentity(codeTest));
    }

    @Test
    public void testTestEquals1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertEquals(rm1,rm1);
    }

    @Test
    public void testTestEquals2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        RawMaterial rm2 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertEquals(rm1,rm2);
    }

    @Test
    public void testTestEquals3() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        RawMaterial rm2 = new RawMaterial("1357",descriptionRawMaterial,category,ds);
        Assert.assertNotEquals(rm1,rm2);
    }

    @Test
    public void testTestEquals4() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertNotEquals(rm1,null);
    }

    @Test
    public void compareTo1() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        RawMaterial rm2 = new RawMaterial("1010",descriptionRawMaterial,category,ds);
        Assert.assertTrue(rm1.identity().compareTo(rm2.identity()) != 0);
    }

    @Test
    public void compareTo2() throws IOException {
        RawMaterialCategory category = new RawMaterialCategory("1234","Algodão");
        ImportFileToBytesService service = new ImportFileToBytesService();
        DataSheet ds = new DataSheet(service.transformToBytes(nameFile,pathFile), service.obtainFileFormat(nameFile));
        RawMaterial rm1 = new RawMaterial(rawMaterialCode,descriptionRawMaterial,category,ds);
        Assert.assertTrue(rm1.compareTo(rm1.identity()) == 0);
    }


}