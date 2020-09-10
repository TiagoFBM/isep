package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.Park;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to class ParkController
 *
 * @author ines-
 */
class ParkControllerTest {

    private static ParkController controller;
    static ParkDB db;

    @BeforeAll
    static void ParkController() {
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = new ParkController();
        db = mock(ParkDB.class);
        controller.setParkDB(db);
    }

    /**
     * Teste of method newPark
     */
    @Test
    void newPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Park p2 = controller.newPark("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Assert.assertEquals(p1.toString(), p2.toString());
    }

    @Test
    void addPark1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(db.addPark(p1)).thenReturn(true);

        boolean result = controller.addPark(p1);

        assertTrue(result);
    }

    @Test
    void addPark2() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(db.addPark(p1)).thenReturn(false);

        boolean result = controller.addPark(p1);

        assertFalse(result);
    }

    @Test
    void addPark3() {
        when(db.removePark("Park_1")).thenReturn(false);
        boolean result = controller.addPark(null);

        assertFalse(result);
    }

    @Test
    void removePark1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(db.removePark("Park_1")).thenReturn(true);

        boolean result = controller.removePark(p1.getDescriptionPark());

        assertTrue(result);
    }

    @Test
    void removePark2() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(db.removePark("Park_1")).thenReturn(false);

        boolean result = controller.removePark(p1.getDescriptionPark());

        assertFalse(result);
    }

    @Test
    void removePark3() {
        when(db.removePark("Park_1")).thenReturn(false);
        boolean result = controller.removePark(null);

        assertFalse(result);
    }

    @Test
    void updatePark1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(db.updatePark("Park_2", 15, 20, 10, "park no2", 10, 10, 25, 25)).thenReturn(true);
        assertTrue(controller.updatePark("Park_2", 15, 20, 10, "park no2", 10, 10, 25, 25));
    }

    /**
     * Teste of method getPark
     */
    @Test
    void getPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Park expected = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        when(db.getPark(10, 10)).thenReturn(expected);
        Assert.assertEquals(expected.toString(), controller.getPark(p1.getLatitude(), p1.getLongitude()).toString());
    }

    /**
     * Teste of method getPark
     */
    @Test
    void getPark1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Park expected = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        when(db.getPark(p1.getDescriptionPark())).thenReturn(expected);
        Assert.assertEquals(expected.toString(), controller.getPark(p1.getDescriptionPark()).toString());
    }

    @Test
    void getVehiclesFromThePark1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        List<String> expected = new LinkedList<>();
        expected.add("PT050");
        expected.add("ePT050");
        when(db.getVehiclesFromPark("Park_1")).thenReturn(expected);
        Assert.assertEquals(expected, controller.getVehiclesFromPark(p1));
    }

    @Test
    void getVehiclesFromThePark2() {
        Park p1 = new Park("test", 10, 10, 10, "park no1", 10, 10, 25, 25);
        List<String> expected = new LinkedList<>();
        when(db.getVehiclesFromPark("test")).thenReturn(expected);
        Assert.assertEquals(expected, controller.getVehiclesFromPark(p1));
    }

    @Test
    void getVehiclesFromThePark3() {
        List<String> expected = new LinkedList<>();
        when(db.getVehiclesFromPark("teste")).thenReturn(null);
        Assert.assertEquals(expected, controller.getVehiclesFromPark(null));
    }

    @Test
    void getDistanceToPark1() {
        double latUser = 30;
        double longUser = 40;
        Park p = new Park("Park_01", 10, 20, 12, "Parque da Trindade", 20, 15, 250, 12);
        when(db.getPark("Park_1")).thenReturn(p);
        double actual = controller.getDistanceToPark(latUser, longUser, "Park_1");
        double expected = 3040602.81;
        Assert.assertEquals(actual, expected, 0.1);
    }

    @Test
    void getDistanceToPark2() {
        Park p = null;
        when(db.getPark("Park_1")).thenReturn(p);
        double expected = -1;
        assertEquals(expected, controller.getDistanceToPark(0, 0, "Park_1"));
    }

    /**
     * Teste of method getVehiclesFromThePark
     */
    @Test
    void getListActiveParks() {
        List<Park> expected = new LinkedList<>();
        expected.add(new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25));

        when(db.getActiveParks()).thenReturn(expected);
        Assert.assertEquals(expected, controller.getListActiveParks());
    }

    /**
     * Teste of method getListNearestParks
     */
    @Test
    void getListNearestParks() {
        List<Park> expected = new LinkedList<>();
        expected.add(new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25));
        expected.add(new Park("Park_2", 10.5, 10, 10, "park no2", 10, 10, 25, 25));
        expected.add(new Park("Park_3", 100.5, 55, 10, "park no3", 10, 10, 25, 25));
        List<Park> inRange = new LinkedList<>();
        inRange.add(new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25));
        inRange.add(new Park("Park_2", 10.5, 10, 10, "park no2", 10, 10, 25, 25));
        when(db.getActiveParks()).thenReturn(expected);
        Assert.assertEquals(inRange, controller.getListNearestParks(10.3, 10.1, 50));
    }

    /**
     * Teste of method getListNearestParks
     */
    @Test
    void getListNearestParks1() {
        List<Park> expected = new LinkedList<>();
        expected.add(new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25));
        expected.add(new Park("Park_2", 10.5, 10, 10, "park no2", 10, 10, 25, 25));
        expected.add(new Park("Park_3", 41.84, -8.66, 10, "park no3", 10, 10, 25, 25));
        List<Park> inRange = new LinkedList<>();
        inRange.add(new Park("Park_3", 41.84, -8.66, 10, "park no3", 10, 10, 25, 25));
        when(db.getActiveParks()).thenReturn(expected);
        Assert.assertEquals(inRange, controller.getListNearestParks(41.84, -8.67));
    }

    /**
     * Teste of method assignVehiclePark (true)
     */
    @Test
    void assignVehiclePark1() {
        Park p1 = new Park("PP01", 60.152712, -20.609297, 18, "Trindade", 10, 20, 220, 16);

        when(db.assignVehiclePark(p1.getDescriptionPark(), "PT05")).thenReturn(true);

        boolean result = controller.assignVehiclePark(p1.getDescriptionPark(), "PT05");

        assertTrue(result);
    }

    /**
     * Teste of method assignVehiclePark (false)
     */
    @Test
    void assignVehiclePark2() {
        Park p1 = new Park("PP01", 60.152712, -20.609297, 18, "Trindade", 10, 20, 220, 16);

        when(db.assignVehiclePark(p1.getDescriptionPark(), "PB05")).thenReturn(false);

        boolean result = controller.assignVehiclePark("PT01", "PT05");

        assertFalse(result);
    }

    /**
     * Teste of method getBicyclesFromPark
     */
    @Test
    void getBicyclesFromPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Bicycle b1 = new Bicycle("b1", 10, 0, 8, 2);

        List<Bicycle> expected = new LinkedList<>();
        expected.add(b1);
        when(db.getBicyclesFromPark(p1.getDescriptionPark())).thenReturn(expected);
        Assert.assertEquals(expected.toString(), controller.getBicyclesFromPark(p1.getDescriptionPark()).toString());
    }

    /**
     * Teste of method getScootersFromPark
     */
    @Test
    void getScootersFromPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 11.1, 10);
        Scooter s2 = new Scooter("s1", 10, 0, 8, "city", 95, 0, 10);

        List<Scooter> expected = new LinkedList<>();
        expected.add(s1);
        expected.add(s2);
        when(db.getScootersFromPark(p1.getDescriptionPark())).thenReturn(expected);
        Assert.assertEquals(expected.toString(), controller.getScootersFromPark(p1.getDescriptionPark()).toString());
    }

   /* @Test
    void getFreeBicycleSlotsAtPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        int expected = 10;

        when(db.getFreeBicycleSlotsAtPark(p1.getDescriptionPark())).thenReturn(expected);

        Assert.assertEquals(expected, controller.getFreeBicycleSlotsAtPark(p1.getDescriptionPark()));
    }

    @Test
    void getFreeScooterSlotsAtPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        int expected = 10;

        when(db.getFreeScooterSlotsAtPark(p1.getDescriptionPark())).thenReturn(expected);

        Assert.assertEquals(expected, controller.getFreeBicycleSlotsAtPark(p1.getDescriptionPark()));
    }*/

}
