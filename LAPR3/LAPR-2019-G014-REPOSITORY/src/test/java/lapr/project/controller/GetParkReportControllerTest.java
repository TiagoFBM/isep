
package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.data.VehicleDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ines-
 */
public class GetParkReportControllerTest {

    static private ParkDB parkdb;
    static private VehicleDB vehicledb;
    static private GetParkReportController controller;

    @BeforeAll
    static void GetParkReportControllerTest() {
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
        controller = new GetParkReportController();
        parkdb = mock(ParkDB.class);
        vehicledb = mock(VehicleDB.class);
        controller.setParkDB(parkdb);
        controller.setVehicleDB(vehicledb);
    }

    @Test
    void getVehiclesWithout100perBatery() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 40, 10);
        Scooter s2 = new Scooter("s2", 12, 0, 10, "city", 100, 100, 14);
        Scooter s3 = new Scooter("s3", 12, 0, 10, "city", 100, 90, 14);

        Park p1 = new Park("p1", 45.5, -50.6, 0, "a", 2, 3, 5, 10);
        List<String> vehiclesPark = new LinkedList<>();

        vehiclesPark.add(s1.getDescriptionVehicle());
        vehiclesPark.add(s2.getDescriptionVehicle());
        vehiclesPark.add(s3.getDescriptionVehicle());

        when(parkdb.getVehiclesFromPark(p1.getDescriptionPark())).thenReturn(vehiclesPark);
        when(vehicledb.getScooterById(s1.getDescriptionVehicle())).thenReturn(s1);
        when(vehicledb.getScooterById(s2.getDescriptionVehicle())).thenReturn(s2);
        when(vehicledb.getScooterById(s3.getDescriptionVehicle())).thenReturn(s3);

        long actual = controller.getVehiclesWithout100perBatery(p1.getDescriptionPark());
        long expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void getListScooterFromPark() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 40, 10);
        Scooter s2 = new Scooter("s2", 12, 0, 10, "city", 100, 100, 14);
        Scooter s3 = new Scooter("s3", 12, 0, 10, "city", 100, 90, 14);
        Bicycle b1 = new Bicycle("b1",12,0,0,2);

        Park p1 = new Park("p1", 45.5, -50.6, 0, "a", 2, 3, 5, 10);
        List<String> vehiclesPark = new LinkedList<>();

        vehiclesPark.add(s1.getDescriptionVehicle());
        vehiclesPark.add(s2.getDescriptionVehicle());
        vehiclesPark.add(s3.getDescriptionVehicle());
        vehiclesPark.add(b1.getDescriptionVehicle());
        

        when(parkdb.getVehiclesFromPark(p1.getDescriptionPark())).thenReturn(vehiclesPark);
        when(vehicledb.getVehicle(s1.getDescriptionVehicle())).thenReturn(s1);
        when(vehicledb.getVehicle(s2.getDescriptionVehicle())).thenReturn(s2);
        when(vehicledb.getVehicle(s3.getDescriptionVehicle())).thenReturn(s3);
        when(vehicledb.getScooterById(s1.getDescriptionVehicle())).thenReturn(s1);
        when(vehicledb.getScooterById(s2.getDescriptionVehicle())).thenReturn(s2);
        when(vehicledb.getScooterById(s3.getDescriptionVehicle())).thenReturn(s3);

        List<Scooter> actual = controller.getListScooterFromPark(p1.getDescriptionPark());

        List<Scooter> expected = new LinkedList<>();
        expected.add(s1);
        expected.add(s3);
        expected.add(s2);
        assertEquals(expected, actual);
    }

    @Test
    void getChargingTimeLeft1() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 40, 10);

        Park p1 = new Park("p1", 45.5, -50.6, 0, "a", 2, 3, 5, 10);

        when(parkdb.getPark(p1.getDescriptionPark())).thenReturn(p1);
        when(vehicledb.getScooterChargingTime("s1")).thenReturn(11.1);

        double charge = (p1.getInputVoltage()*p1.getInputCurrent()*Math.pow(10d, -3d)) / 3;
        double calc = ((s1.getMaxBatery() / charge) - ((s1.getActualBatery() / charge) + 11.1)) * 60;

        Assert.assertEquals(calc, controller.getChargingTimeLeft(s1, 1, p1.getDescriptionPark()),0.1f);
    }

    @Test
    void getChargingTimeLeft2() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 40, 10);

        Park p1 = new Park("p1", 45.5, -50.6, 0, "a", 2, 3, 5, 10);

        when(parkdb.getPark(p1.getDescriptionPark())).thenReturn(p1);
        when(vehicledb.getScooterChargingTime("s1")).thenReturn(11.1);

        double charge = (p1.getInputVoltage()*p1.getInputCurrent()*Math.pow(10d, -3d)) /5;
        double calc = ((s1.getMaxBatery() / charge) - ((s1.getActualBatery() / charge) + 11.1)) * 60;

        Assert.assertEquals(calc, controller.getChargingTimeLeft(s1, 5, p1.getDescriptionPark()),0.1f);
    }

    /**
     * Test of method getChargingTimeLeft3.
     */
     @Test
    void getChargingTimeLeft3() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 95, 11.1, 10);
        Scooter s2 = new Scooter("s1", 10, 0, 8, "city", 95, 0, 10);

        Park p1 = new Park("p1", 45.5, -50.6, 0, "a", 2, 3, 5, 10);

        when(parkdb.getPark(p1.getDescriptionPark())).thenReturn(p1);
        when(vehicledb.getScooterChargingTime("s1")).thenReturn(11.1);

        double charge = (p1.getInputVoltage()*p1.getInputCurrent()*Math.pow(10d, -3d)) /5;
        double calc = ((s2.getMaxBatery() / charge) - ((s1.getActualBatery() / charge) + 11.1)) * 60;

        Assert.assertEquals(calc, controller.getChargingTimeLeft(s1, 5, p1.getDescriptionPark()),0.1f);
    }

}
