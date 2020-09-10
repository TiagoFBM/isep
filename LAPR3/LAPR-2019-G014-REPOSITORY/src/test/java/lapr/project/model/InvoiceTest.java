package lapr.project.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test Invoice Class.
 *
 * @author ines-
 */
class InvoiceTest {

    private static RegisterInvoice reg;
    private static Invoice inv1;
    private static java.sql.Date initial;
    private static java.sql.Date initial2;

    @BeforeAll
    static void InvoiceTest() {
        int year = 2019;
        int month = 12;
        int day = 12;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        initial = new java.sql.Date(cal.getTimeInMillis());

        year = 2019;
        month = 12;
        day = 14;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        initial2 = new java.sql.Date(cal.getTimeInMillis());

        reg = new RegisterInvoice();
        inv1 = reg.newInvoice("Manuel", "sc01", initial,initial, "a", "local", 0.0);
    }

    /**
     * Test of method setUsername
     */
    @Test
    void setUsername() {
        String username = "Maria";

        inv1.setUsername(username);

        assertEquals(username, inv1.getUsername());
    }

    /**
     * Test of method setDescriptionVehicle
     */
    @Test
    void setDescriptionVehicle() {
        String desc = "sc01";

        inv1.setDescriptionVehicle(desc);

        assertEquals(desc, inv1.getDescriptionVehicle());
    }

    /**
     * Test of method setDateTimeBegin
     */
    @Test
    void setDateTimeBegin() {
        java.sql.Date desc = new Date(Calendar.getInstance().getTimeInMillis());

        inv1.setDateTimeBegin(desc);

        assertEquals(desc, inv1.getDateTimeBegin());
    }

    /**
     * Test of method setDateTimeFinal
     */
    @Test
    void setDateTimeFinal() {
        java.sql.Date desc = new Date(Calendar.getInstance().getTimeInMillis());

        inv1.setDateTimeFinal(desc);

        assertEquals(desc, inv1.getDateTimeFinal());
    }

    /**
     * Test of method setSource
     */
    @Test
    void setSource() {
        String source = "0";

        inv1.setSource(source);

        assertEquals(source, inv1.getSource());
    }

    /**
     * Test of method setDestiny
     */
    @Test
    void setDestiny() {
        String dest = "local";

        inv1.setDestiny(dest);

        assertEquals(dest, inv1.getDestiny());
    }

    /**
     * Test of method setTotalCost
     */
    @Test
    void setTotalCost() {
        double cost = 0;

        inv1.setTotalCost(cost);

        assertEquals(cost, inv1.getTotalCost());
    }

    /**
     * Test of method equals if two invoices are equals.
     */
    @Test
    void equals1() {
        Assert.assertTrue(inv1.equals(inv1));
    }

    /**
     * Test of method equals if two invoices are equals.
     */
    @Test
    void equals2() {
        Assert.assertTrue(inv1.equals(reg.newInvoice("Manuel", "sc01", initial,initial, "a", "local", 0)));
    }


    /**
     * Test of method equals if object is null.
     */
    @Test
    void equals4() {
        Assert.assertFalse(inv1 == null);
    }

    /**
     * Test of method equals if two objects have diferent classes.
     */
    @Test
    void equals5() {
        Bicycle v2 = new Bicycle("", 0, 0, 0, 0);
        Assert.assertFalse(inv1.equals(v2));
    }

    /**
     * Test of method equals if two invoices have diferent totalCost.
     */
    @Test
    void equals6() {
        Invoice inv2 = new Invoice("Manuel", "sc01", initial2, initial2, "a", "local", 10);
        Assert.assertFalse(inv1.equals(inv2));
    }

    /**
     * Test of method equals if two invoices have diferent username.
     */
    @Test
    void equals7() {
        Invoice inv2 = new Invoice("Manuela", "sc01", initial2, initial2, "a", "local", 0);
        Assert.assertFalse(inv1.equals(inv2));
    }

    /**
     * Test of method equals if two invoices have diferent descriptionVehicle.
     */
    @Test
    void equals8() {
        Invoice inv2 = new Invoice("Manuel", "sof01", initial2,initial2, "a", "local", 0);
        Assert.assertFalse(inv1.equals(inv2));
    }

    @Test
    void equals9(){
        Invoice instance = new Invoice("Manuel", "sc01", initial,initial, "a", "local", 0.0);
        Assert.assertTrue(instance.equals(inv1));
    }

    /**
     * Test of method hashCode.
     */
    @Test
    void hashCodeTest() {
        Assert.assertEquals(inv1.hashCode(), inv1.hashCode());
    }

    /**
     * Test of method toString.
     */
    @Test
    void toStringTest() {
        Invoice instance = new Invoice("Manuel", "sc01", initial, initial, "0", "local", 0);
        String expected = "descriptionVehicle='sc01', dateTimeBegin='2019-12-12', dateTimeFinal='2019-12-12', source='0', destiny='local', totalCost=0.0";
        String actual = instance.toString();
        Assert.assertEquals(expected.trim(), actual.trim());
    }

}
