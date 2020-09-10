package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.general.domain.model.Designation;
import org.junit.Test;

import static org.junit.Assert.*;

public class SerialNumberTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCreateEmptySerialNumber() {
        SerialNumber.valueOf("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullSerialNumber() {
        SerialNumber.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSerialNumberWithLessThanSixChars() {
        SerialNumber.valueOf("lol");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSerialNumberWithMoreThanTwentyChars() {
        SerialNumber.valueOf("asdfghjklçpoiuytrewq12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSerialNumberWithOnlyWhiteSpaces() {
        SerialNumber.valueOf("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSerialNumberWithTwoWords() {
        SerialNumber.valueOf("serial number");
    }

    @Test
    public void testEquals0() {
        SerialNumber sn1 = SerialNumber.valueOf("AAA111");
        SerialNumber sn2 = SerialNumber.valueOf("AAA111");
        assertEquals(sn1, sn2);
        assertEquals(sn1.hashCode(), sn2.hashCode());
    }

    @Test
    public void testEquals1() {
        SerialNumber sn1 = SerialNumber.valueOf("AAA111");
        assertEquals(sn1, sn1);
    }

    @Test
    public void testEquals2() {
        SerialNumber sn1 = SerialNumber.valueOf("AAA111");
        Designation d1 = Designation.valueOf("lmfao");
        assertNotEquals(sn1, d1);
        assertNotEquals(sn1.hashCode(), d1.hashCode());
    }

    @Test
    public void testToString() {
        SerialNumber sn1 = SerialNumber.valueOf("AAA111");
        String expected = "AAA111";
        assertEquals(expected, sn1.toString());
    }
}