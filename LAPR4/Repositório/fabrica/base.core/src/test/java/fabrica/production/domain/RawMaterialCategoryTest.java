package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RawMaterialCategoryTest {

    private static final String categoryId = "12345";
    private static final String briefDescription = "briefDescription";

    @Test
    public void testCreateRawMaterialCategory() {
        new RawMaterialCategory(categoryId,briefDescription);
        Assert.assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialCategoryWithNullID() {
        new RawMaterialCategory(null,briefDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialCategoryWithNullDescription() {
        new RawMaterialCategory(categoryId,null);
    }

    @Test
    public void testRawMaterialCategorySameAsFromOtherClass() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        ProductCategory pc = ProductCategory.valueOf("categoria");
        Assert.assertFalse(rmc1.sameAs(pc));
    }

    @Test
    public void testRawMaterialCategorySameAsDiferentConcent() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        RawMaterialCategory rmc2  = new RawMaterialCategory("54321","fullDescription");
        Assert.assertFalse(rmc1.sameAs(rmc2));
    }

    @Test
    public void testRawMaterialCategorySameAsSameContent() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        RawMaterialCategory rmc2  = new RawMaterialCategory(categoryId,briefDescription);
        Assert.assertTrue(rmc1.sameAs(rmc2));
    }

    @Test
    public void testRawMaterialCategorySameAsSameObject() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        Assert.assertTrue(rmc1.sameAs(rmc1));
    }

    @Test
    public void testEquals1() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        RawMaterialCategory rmc2  = new RawMaterialCategory(categoryId,briefDescription);
        Assert.assertEquals(rmc1,rmc2);
    }

    @Test
    public void testEquals2() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        Assert.assertEquals(rmc1,rmc1);
    }

    @Test
    public void testEquals3() {
        RawMaterialCategory rmc1  = new RawMaterialCategory(categoryId,briefDescription);
        RawMaterialCategory rmc2  = new RawMaterialCategory("12345","briefDescription");
        Assert.assertEquals(rmc1,rmc2);
    }


    @Test
    public void compareToSameIdentity() {
        RawMaterialCategory rmc1 = new RawMaterialCategory(categoryId, briefDescription);
        RawMaterialCategory rmc2 = new RawMaterialCategory(categoryId, "comercial");
        Assert.assertEquals(0,rmc1.compareTo(rmc2.identity()));
    }

    @Test
    public void compareToDiferentIdentity() {
        RawMaterialCategory rmc1 = new RawMaterialCategory(categoryId, briefDescription);
        RawMaterialCategory rmc2 = new RawMaterialCategory("123" ,briefDescription);
        Assert.assertEquals(2,rmc1.compareTo(rmc2.identity()));
    }

    @Test
    public void obtainBriefDescriptionTest() {
        RawMaterialCategory p1 = new RawMaterialCategory(categoryId, briefDescription);
        Assert.assertEquals(BriefDescription.valueOf(briefDescription),p1.obtainBriefDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRawMaterialCategoryNull() {
        RawMaterialCategory rmc = RawMaterialCategory.valueOf(null,briefDescription);
        Assert.assertTrue(true);
    }
}