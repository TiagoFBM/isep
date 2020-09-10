package lapr.project.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class RentTest {

    private static RegisterRent reg;
    private static Rent r1;
    private static java.sql.Date initial;

    @BeforeAll
    static void rentTest(){
        reg = new RegisterRent();
        int year = 2019;
        int month = 12;
        int day = 30;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        initial = new java.sql.Date(cal.getTimeInMillis());

        r1 = reg.newRent(1, "EPT050", "Point1", initial,initial,"User1", 10, 10 );
    }

    @Test
    void setDescriptionRent() {
        int desc = 1;

        r1.setDescriptionRent(desc);

        assertEquals(desc,r1.getDescriptionRent());
    }

    @Test
    void setDescriptionVehicle() {
        String desc = "ola";

        r1.setDescriptionVehicle(desc);

        assertEquals(desc,r1.getDescriptionVehicle());
    }

    @Test
    void setDescriptionPoint() {
        String desc = "ola";

        r1.setDescriptionPoint(desc);

        assertEquals(desc,r1.getDescriptionPoint());
    }

    @Test
    void setDateTimeBegin() {
        Date desc = new Date(Calendar.getInstance().getTimeInMillis());

        r1.setDateTimeBegin(desc);

        assertEquals(desc,r1.getDateTimeBegin());
    }

    @Test
    void setDateHourLocal() {
        Date desc = new Date(Calendar.getInstance().getTimeInMillis());

        r1.setDateHourLocal(desc);

        assertEquals(desc,r1.getDateHourLocal());
    }

    @Test
    void setUsername() {
        String desc = "ola";

        r1.setUsername(desc);

        assertEquals(desc,r1.getUsername());
    }

    @Test
    void setLatitudeDestination() {
        double lat = 1234.5;

        r1.setLatitudeDestination(lat);

        assertEquals(lat,r1.getLatitudeDestination());
    }

    @Test
    void setLongitudeDestination() {
        double lat = 1234.5;

        r1.setLongitudeDestination(lat);

        assertEquals(lat,r1.getLongitudeDestination());
    }

    @Test
    void testtoString(){
        Rent r2 = new Rent(1, "EPT050", "Point1", initial,initial,"User1", 10, 10 );
        Assert.assertEquals(r1.toString(), r2.toString());
    }
}