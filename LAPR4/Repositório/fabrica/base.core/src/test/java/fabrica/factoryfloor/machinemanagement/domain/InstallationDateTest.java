package fabrica.factoryfloor.machinemanagement.domain;


import eapli.framework.general.domain.model.Designation;
import eapli.framework.time.util.Calendars;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class InstallationDateTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidInstallationDate() {
        Calendar c = Calendars.now();
        c.add(Calendar.YEAR, 5);
        InstallationDate id1 = InstallationDate.valueOf(c);
    }

    @Test
    public void testInstallationDateHashCode1() {
        Calendar c = Calendars.now();
        c.add(Calendar.DAY_OF_MONTH, -10);
        InstallationDate id1 = InstallationDate.valueOf(c);
        InstallationDate id2 = InstallationDate.valueOf(c);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void testInstallationDateHashCode2() {
        Calendar c = Calendars.now();
        Calendar c2 = Calendars.now();
        c.add(Calendar.DAY_OF_MONTH, -5);
        InstallationDate id1 = InstallationDate.valueOf(c);
        c2.add(Calendar.YEAR, -10);
        InstallationDate id2 = InstallationDate.valueOf(c2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void testEquals1() {
        Calendar c = Calendars.of(2000, 7, 10);
        InstallationDate id1 = InstallationDate.valueOf(c);
        assertEquals(id1, id1);
    }

    @Test
    public void testEquals2() {
        Calendar c = Calendars.of(2000, 7, 10);
        Designation d = Designation.valueOf("lol");
        InstallationDate id1 = InstallationDate.valueOf(c);
        assertNotEquals(id1, d);
    }
}