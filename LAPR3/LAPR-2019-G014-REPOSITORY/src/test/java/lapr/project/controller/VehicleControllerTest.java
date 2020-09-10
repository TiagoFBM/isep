package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lapr.project.model.Vehicle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleControllerTest {

    static VehicleController controller;
    static VehicleDB db;

    @BeforeAll
    static void VehicleController() {

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
        //Initial Database Setup
        controller = new VehicleController();
        db = mock(VehicleDB.class);
        controller.setVehicleDB(db);
    }

    @Test
    void newBicycle() {
        Bicycle b1 = new Bicycle("PT050", 12, 1.10, 0.3, 15);

        Bicycle b2 = controller.newBicycle("PT050", 12, 1.10, 0.3, 15);

        Assert.assertEquals(b1.toString(), b2.toString());
    }

    @Test
    void newScooter() {
        Scooter s1 = new Scooter("ePT050", 12, 1.0, 0.3, "city", 1, 0.75, 150);

        Scooter s2 = controller.newScooter("ePT050", 12, "city", 1, 0.75, 1, 0.3, 150);

        Assert.assertEquals(s1.toString(), s2.toString());
    }

    @Test
    void registerBicycle1() {
        Bicycle b1 = new Bicycle("PT051", 12, 1.10, 0.3, 15);

        when(db.addBicycleToDataBase(b1)).thenReturn(true);

        boolean result = controller.registerBicycle(b1);

        assertTrue(result);
    }

    @Test
    void registerBicycle2() {
        Bicycle b1 = new Bicycle("PT052", 12, 1.10, 0.3, 15);

        when(db.addBicycleToDataBase(b1)).thenReturn(false);

        boolean result = controller.registerBicycle(b1);

        assertFalse(result);
    }

    @Test
    void registerScooter1() {
        Scooter s1 = new Scooter("ePT014", 12, 1.10, 0.3, "city", 1, 0.75, 150);

        when(db.addScooterToDataBase(s1)).thenReturn(true);

        boolean result = controller.registerScooter(s1);

        assertTrue(result);
    }

    @Test
    void registerScooter2() {
        Scooter s1 = new Scooter("ePT015", 12, 1.10, 0.3, "city", 1, 0.75, 150);

        when(db.addScooterToDataBase(s1)).thenReturn(false);

        boolean result = controller.registerScooter(s1);

        assertFalse(result);
    }

    @Test
    void updateScooter1() {
        Scooter s1 = new Scooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.75, 150);

        when(db.updateScooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.75, 150)).thenReturn(true);

        boolean result = controller.updateScooter(s1);

        assertTrue(result);
    }

    @Test
    void updateScooter2() {
        Scooter s1 = new Scooter("ePT051", 12, 1.10, 0.3, "city", 1, 0.75, 150);

        when(db.updateScooter("ePT051", 12, 1.10, 0.3, "city", 1, 0.75, 150)).thenReturn(false);

        boolean result = controller.updateScooter(s1);

        assertFalse(result);
    }

    @Test
    void updateBicycle1() {
        Bicycle b1 = new Bicycle("PT050", 12, 1.10, 0.3, 15);

        when(db.updateBicycle("PT050", 12, 1.10, 0.3, 15)).thenReturn(true);

        boolean result = controller.updateBicycle(b1);

        assertTrue(result);
    }

    @Test
    void updateBicycle2() {
        Bicycle b1 = new Bicycle("PT051", 12, 1.10, 0.3, 15);

        when(db.updateBicycle("PT051", 12, 1.10, 0.3, 15)).thenReturn(false);

        boolean result = controller.updateBicycle(b1);

        assertFalse(result);
    }

    @Test
    void removeBicycle1() {
        when(db.removeBicycle("PT050")).thenReturn(true);

        boolean result = controller.removeBicycle("PT050");

        assertTrue(result);
    }

    @Test
    void removeBicycle2() {
        when(db.removeBicycle("PT051")).thenReturn(false);

        boolean result = controller.removeBicycle("PT051");

        assertFalse(result);
    }

    @Test
    void removeScooter1() {
        String tmp = "ePT050";
        when(db.removeScooter(tmp)).thenReturn(true);

        boolean result = controller.removeScooter(tmp);

        assertTrue(result);
    }

    @Test
    void removeScooter2() {
        String tmp = "ePT051";
        when(db.removeScooter(tmp)).thenReturn(false);

        boolean result = controller.removeScooter(tmp);

        assertFalse(result);
    }

    @Test
    void getVehicle() {
        Vehicle expected = new Vehicle("PT050", 12, 1.10, 0.3);
        when(db.getVehicle(expected.getDescriptionVehicle())).thenReturn(expected);

        Vehicle result = controller.getVehicle(expected.getDescriptionVehicle());

        assertEquals(expected, result);
    }
}
