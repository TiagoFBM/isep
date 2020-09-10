package fabrica.factoryfloor.machinemanagement.domain;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.time.util.Calendars;
import fabrica.production.domain.AlfanumericCode;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class MachineTest {

    private static final String internalCode = "DD4";
    private static final short identificationNumber = 123;
    private static final String serialNumber = "ABC1234";
    private static final String manufacturer = "Manuf123";
    private static final String model = "model321";
    private static final Calendar installationDate = Calendars.of(2012,12, 12);
    private static final String operation = "operation987";

    @Test
    public void testCreateMachine() {
        new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        assertTrue(true);
    }

    @Test
    public void testSameAs1() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        assertTrue(m1.sameAs(m2));
    }

    @Test
    public void testSameAs2() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Designation d = Designation.valueOf("ola");
        assertFalse(m1.sameAs(d));
    }

    @Test
    public void testSameAs3() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        assertTrue(m1.sameAs(m1));
    }

    @Test
    public void testSameAs4() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, "serialNumber", manufacturer, model, installationDate, operation);
        assertFalse(m1.sameAs(m2));
    }

    @Test
    public void testSameAs5() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, "manufacturer", model, installationDate, operation);
        assertFalse(m1.sameAs(m2));
    }

    @Test
    public void testSameAs6() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, "model", installationDate, operation);
        assertFalse(m1.sameAs(m2));
    }

    @Test
    public void testSameAs7() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, Calendars.of(2000,7,10), operation);
        assertFalse(m1.sameAs(m2));
    }

    @Test
    public void testSameAs8() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, "operation");
        assertFalse(m1.sameAs(m2));
    }

    @Test
    public void testCompareTo1() {
        Machine m1 = new Machine(internalCode + "A", identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber,  serialNumber, manufacturer, model, installationDate, operation);
        int expected = 1;
        int result = m1.compareTo(m2.identity());
        assertEquals(expected, result);
    }

    @Test
    public void testCompareTo2() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        int expected = 0;
        int result = m1.compareTo(m2.identity());
        assertEquals(expected, result);
    }

    @Test
    public void testCompareTo3() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        Machine m2 = new Machine(internalCode + "A", identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        int expected = -1;
        int result = m1.compareTo(m2.identity());
        assertEquals(expected, result);
    }

    @Test
    public void testHasIdentity1() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        AlfanumericCode id1 = AlfanumericCode.valueOf(internalCode);
        assertTrue(m1.hasIdentity(id1));
    }

    @Test
    public void testHasIdentity2() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        AlfanumericCode id1 = AlfanumericCode.valueOf("A" + internalCode);
        assertFalse(m1.hasIdentity(id1));
    }

    @Test
    public void testToString() {
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        String expected = "Machine DD4:\n" +
                "  Identification Number: 123\n" +
                "  Serial Number: ABC1234\n" +
                "  Manufacturer: Manuf123\n" +
                "  Model: model321\n" +
                "  Installation Date: 2012/12/12\n" +
                "  Operation: operation987\n" +
                "  Configuration Files: []\n" +
                "  Machine Status: Machine inactive.";
        String result = m1.toString();
        assertEquals(expected, result);
    }

}