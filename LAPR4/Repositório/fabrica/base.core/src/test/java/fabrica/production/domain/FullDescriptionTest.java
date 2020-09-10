package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FullDescriptionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFullDescriptionEmpty() {
        String code = "";
        FullDescription.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFullDescriptionNull() {
        FullDescription.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFullDescriptionWhiteSpace() {
        String code = " ";
        FullDescription.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericWithMoreThen70Chars() {
        String code = "ojdjaojneijdwhfkoiwdnwqudderhtgfdsdwfegsrdhtfjhdgsfadewfgrsdtyhrgefdbiqddq";
        FullDescription.valueOf(code);
    }

    @Test
    public void testCreateAlfanumericWithLessThen70Chars() {
        String code = "owfgrsdtyhrgefdbiqddq";
        FullDescription.valueOf(code);
    }

    @Test
    public void testCompare1() {
        String code1 = "descricao";
        String code2 = "descricao";
        FullDescription.valueOf(code1);
        FullDescription.valueOf(code2);
        Assert.assertEquals(0, code1.compareTo(code2));
    }

    @Test
    public void testCompare2() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        FullDescription code2 = FullDescription.valueOf(strCode2);
        Assert.assertEquals(5, code1.compareTo(code2));
    }

    @Test
    public void testCompare3() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        FullDescription code2 = FullDescription.valueOf(strCode2);
        Assert.assertEquals(-5, code2.compareTo(code1));
    }

    @Test
    public void testEqualsSameObject() {
        String strCode1 = "descricao";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        Assert.assertEquals(code1, code1);
    }

    @Test
    public void testEqualsNull() {
        String strCode1 = "descricao";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        Assert.assertNotEquals(null, code1);
    }

    @Test
    public void testEqualsDiferentClass() {
        String strCode1 = "12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(FullDescription.valueOf("descricao"), code1);
    }

    @Test
    public void testEqualsDiferentContent() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        FullDescription code2 = FullDescription.valueOf(strCode2);
        Assert.assertNotEquals(code1, code2);
    }

    @Test
    public void testToStringTrue() {
        String strCode1 = "descricao";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        Assert.assertEquals(code1.toString(),strCode1);
    }

    @Test
    public void testToStringFalse() {
        String strCode1 = "descricao";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        Assert.assertNotEquals(code1.toString(),"123");
    }

    @Test
    public void hashCodeTrue() {
        String strCode1 = "descricao";
        String strCode2 = "descricao";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        FullDescription code2 = FullDescription.valueOf(strCode2);
        Assert.assertEquals(code1.hashCode(),code2.hashCode());
    }

    @Test
    public void hashCodeFalse() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        FullDescription code1 = FullDescription.valueOf(strCode1);
        FullDescription code2 = FullDescription.valueOf(strCode2);
        Assert.assertNotEquals(code1.hashCode(),code2.hashCode());
    }


}