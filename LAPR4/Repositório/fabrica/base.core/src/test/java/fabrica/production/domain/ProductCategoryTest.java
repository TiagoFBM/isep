package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

public class ProductCategoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidProductCategory() {
        String code = "";
        ProductCategory.valueOf(code);
    }

    @Test
    public void testCompareToProductCategory1() {
        String code1 = "categoria1";
        ProductCategory pc1 = ProductCategory.valueOf(code1);
        String code2 = "categoria2";
        ProductCategory pc2 = ProductCategory.valueOf(code2);
        Assert.assertEquals(-1,pc1.compareTo(pc2));
    }

    @Test
    public void testCompareToProductCategory2() {
        String code1 = "categoria1";
        ProductCategory pc1 = ProductCategory.valueOf(code1);
        String code2 = "categoria2";
        ProductCategory pc2 = ProductCategory.valueOf(code2);
        Assert.assertEquals(1,pc2.compareTo(pc1));
    }

    @Test
    public void testCompareToProductCategory3() {
        String code1 = "categoria1";
        ProductCategory pc1 = ProductCategory.valueOf(code1);
        String code2 = "categoria1";
        ProductCategory pc2 = ProductCategory.valueOf(code2);
        Assert.assertEquals(0,pc2.compareTo(pc1));
    }

}
