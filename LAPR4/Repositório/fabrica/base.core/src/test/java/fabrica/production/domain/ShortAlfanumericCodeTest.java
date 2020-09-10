package fabrica.production.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortAlfanumericCodeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateShortAlfanumericCodeEmpty() {
        String code = "";
        ShortAlfanumericCode.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateShortAlfanumericCodeNull() {
        ShortAlfanumericCode.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateShortAlfanumericCodeWhiteSpace() {
        String code = " ";
        ShortAlfanumericCode.valueOf(code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateShortAlfanumericWithMoreThen10Chars() {
        String code = "81238593191";
        ShortAlfanumericCode.valueOf(code);
    }

    public void testCreateShortAlfanumericWithLessThen10Chars() {
        String code = "1239483";
        ShortAlfanumericCode.valueOf(code);
    }

    @Test
    public void testCompare1() {
        String code1 = "12345";
        String code2 = "12345";
        ShortAlfanumericCode.valueOf(code1);
        ShortAlfanumericCode.valueOf(code2);
        Assert.assertEquals(0, code1.compareTo(code2));
    }

    @Test
    public void testCompare2() {
        String strCode1 = "12345";
        String strCode2 = "1234";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        ShortAlfanumericCode code2 = ShortAlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(1, code1.compareTo(code2));
    }

    @Test
    public void testCompare3() {
        String strCode1 = "12345";
        String strCode2 = "1234";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        ShortAlfanumericCode code2 = ShortAlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(-1, code2.compareTo(code1));
    }

    @Test
    public void testEqualsSameObject() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertEquals(code1, code1);
    }

    @Test
    public void testEqualsNull() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(null, code1);
    }

    @Test
    public void testEqualsDiferentClass() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(BriefDescription.valueOf("abc"), code1);
    }

    @Test
    public void testEqualsDiferentContent() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(BriefDescription.valueOf("abc"), code1);
    }

    @Test
    public void testToStringTrue() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertEquals(code1.toString(),strCode1);
    }

    @Test
    public void testToStringFalse() {
        String strCode1 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        Assert.assertNotEquals(code1.toString(),"123");
    }

    @Test
    public void hashCodeTrue() {
        String strCode1 = "12345";
        String strCode2 = "12345";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        ShortAlfanumericCode code2 = ShortAlfanumericCode.valueOf(strCode2);
        Assert.assertEquals(code1.hashCode(),code2.hashCode());
    }

    @Test
    public void hashCodeFalse() {
        String strCode1 = "12345";
        String strCode2 = "123";
        ShortAlfanumericCode code1 = ShortAlfanumericCode.valueOf(strCode1);
        ShortAlfanumericCode code2 = ShortAlfanumericCode.valueOf(strCode2);
        Assert.assertNotEquals(code1.hashCode(),code2.hashCode());
    }

}