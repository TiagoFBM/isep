package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

public class AlfanumericCodeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericCodeEmpty() {
        String code = "";
        AlfanumericCode.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericCodeNull() {
        AlfanumericCode.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericCodeWhiteSpace() {
        String code = " ";
        AlfanumericCode.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAlfanumericWithMoreThen15Chars() {
        String code = "5129732863298327";
        AlfanumericCode.valueOf(code);
    }

    @Test
    public void testCreateAlfanumericWithLessThen15Chars() {
        String code = "51298327";
        AlfanumericCode.valueOf(code);
    }

    @Test
    public void testCompare1() {
        String code1 = "P12345";
        String code2 = "P12345";
        AlfanumericCode.valueOf(code1);
        AlfanumericCode.valueOf(code2);
        Assert.assertEquals(0, code1.compareTo(code2));
    }

    @Test
    public void testCompare2() {
        String strCode1 = "P12345";
        String strCode2 = "1234";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        AlfanumericCode code2 = AlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(63, code1.compareTo(code2));
    }

    @Test
    public void testCompare3() {
        String strCode1 = "P12345";
        String strCode2 = "1234";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        AlfanumericCode code2 = AlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(-63, code2.compareTo(code1));
    }

    @Test
    public void testEqualsSameObject() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertEquals(code1, code1);
    }

    @Test
    public void testEqualsNull() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(null, code1);
    }

    @Test
    public void testEqualsDiferentClass() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(BriefDescription.valueOf("abc"), code1);
    }

    @Test
    public void testEqualsDiferentContent() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(BriefDescription.valueOf("abc"), code1);
    }

    @Test
    public void testToStringTrue() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertEquals(code1.toString(),strCode1);
    }

    @Test
    public void testToStringFalse() {
        String strCode1 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(code1.toString(),"123");
    }

    @Test
    public void hashCodeTrue() {
        String strCode1 = "P12345";
        String strCode2 = "P12345";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        AlfanumericCode code2 = AlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(code1.hashCode(),code2.hashCode());
    }

    @Test
    public void hashCodeFalse() {
        String strCode1 = "P12345";
        String strCode2 = "123";
        AlfanumericCode code1 = AlfanumericCode.valueOf(strCode1);
        AlfanumericCode code2 = AlfanumericCode.valueOf(strCode2);
        Assert.assertNotEquals(code1.hashCode(),code2.hashCode());
    }

}
