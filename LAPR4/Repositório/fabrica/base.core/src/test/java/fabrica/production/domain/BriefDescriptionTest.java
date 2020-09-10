package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

public class BriefDescriptionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBriefDescriptionEmpty() {
        String code = "";
        BriefDescription.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBriefDescriptionNull() {
        BriefDescription.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBriefDescriptionWhiteSpace() {
        String code = " ";
        BriefDescription.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericWithMoreThen30Chars() {
        String code = "ojdjaojneijdwhfkoiwdnwqudbiqddq";
        BriefDescription.valueOf(code);
    }

    @Test
    public void testCreateAlfanumericWithLessThen30Chars() {
        String code = "ojdjdnwqudbiqddq";
        BriefDescription.valueOf(code);
    }

    @Test
    public void testCompare1() {
        String code1 = "descricao";
        String code2 = "descricao";
        BriefDescription.valueOf(code1);
        BriefDescription.valueOf(code2);
        Assert.assertEquals(0, code1.compareTo(code2));
    }

    @Test
    public void testCompare2() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        BriefDescription code2 = BriefDescription.valueOf(strCode2);
        Assert.assertEquals(5, code1.compareTo(code2));
    }

    @Test
    public void testCompare3() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        BriefDescription code2 = BriefDescription.valueOf(strCode2);
        Assert.assertEquals(-5, code2.compareTo(code1));
    }

    @Test
    public void testEqualsSameObject() {
        String strCode1 = "descricao";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        Assert.assertEquals(code1, code1);
    }

    @Test
    public void testEqualsNull() {
        String strCode1 = "descricao";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        Assert.assertNotEquals(null, code1);
    }

    @Test
    public void testEqualsDiferentClass() {
        String strCode1 = "12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(BriefDescription.valueOf("descricao"), code1);
    }

    @Test
    public void testEqualsDiferentContent() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        BriefDescription code2 = BriefDescription.valueOf(strCode2);
        Assert.assertNotEquals(code1, code2);
    }

    @Test
    public void testToStringTrue() {
        String strCode1 = "descricao";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        Assert.assertEquals(code1.toString(),strCode1);
    }

    @Test
    public void testToStringFalse() {
        String strCode1 = "descricao";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        Assert.assertNotEquals(code1.toString(),"123");
    }

    @Test
    public void hashCodeTrue() {
        String strCode1 = "descricao";
        String strCode2 = "descricao";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        BriefDescription code2 = BriefDescription.valueOf(strCode2);
        Assert.assertEquals(code1.hashCode(),code2.hashCode());
    }

    @Test
    public void hashCodeFalse() {
        String strCode1 = "descricao";
        String strCode2 = "desc";
        BriefDescription code1 = BriefDescription.valueOf(strCode1);
        BriefDescription code2 = BriefDescription.valueOf(strCode2);
        Assert.assertNotEquals(code1.hashCode(),code2.hashCode());
    }



}