package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.data.PathDB;
import lapr.project.data.UserDB;
import lapr.project.data.VehicleDB;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class to test SuggestEscootersToGoFromOneParkToAnother classe
 *
 * @author ines-
 */
public class SuggestEscootersToGoFromOneParkToAnotherTest {

    private static SuggestEscootersToGoFromOneParkToAnother controller;
    private static ParkDB parkDB;
    private static VehicleDB vehicleDB;
    private static UserDB userDB;
    private static PathDB pathDB;

    @BeforeAll
    static void SuggestEscootersToGoFromOneParkToAnother() {

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
        controller = new SuggestEscootersToGoFromOneParkToAnother();
        vehicleDB = mock(VehicleDB.class);
        parkDB = mock(ParkDB.class);
        userDB = mock(UserDB.class);
        pathDB = mock(PathDB.class);
        controller.setVehicleDB(vehicleDB);
        controller.setParkDB(parkDB);
    }


    @Test
    void getVehiclesFromPark() {
        Vehicle b1 = new Vehicle("PT050", 12, 1.10, 0.3);
        Vehicle b2 = new Vehicle("PT051", 12, 1.10, 0.3);

        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        List<String> expected = new LinkedList<>();
        expected.add(b1.getDescriptionVehicle());
        expected.add(b2.getDescriptionVehicle());

        when(parkDB.getVehiclesFromPark(p1.getDescriptionPark())).thenReturn(expected);

        List<String> result = controller.getVehiclesFromPark(p1.getDescriptionPark());

        Assert.assertEquals(result, expected);
    }

    @Test
    void getListScooterFromPark() {
        Scooter b1 = new Scooter("PT050", 12, 1.10, 0.3, 1, "city", 250, 70, 350);
        Scooter b2 = new Scooter("PT051", 16, 1.50, 0.5, 1, "city", 250, 70, 350);

        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);

        List<Scooter> lstScooters = new LinkedList<>();
        List<String> lstScootersID = new LinkedList<>();

        lstScooters.add(b1);
        lstScooters.add(b2);

        lstScootersID.add(b1.getDescriptionVehicle());
        lstScootersID.add(b2.getDescriptionVehicle());

        when(vehicleDB.getVehicle("PT050")).thenReturn(b1).thenReturn(b1);
        when(vehicleDB.getVehicle("PT051")).thenReturn(b1).thenReturn(b2);
        when(parkDB.getVehiclesFromPark(p1.getDescriptionPark())).thenReturn(lstScootersID);
        when(vehicleDB.getScooterById("PT050")).thenReturn(b1);
        when(vehicleDB.getScooterById("PT051")).thenReturn(b2);

        List<Scooter> lstActual = controller.getListScooterFromPark(p1.getDescriptionPark());
        Assert.assertEquals(lstScooters, lstActual);
    }

    @Test
    void getPark() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        when(parkDB.getPark(p1.getDescriptionPark())).thenReturn(p1);
        Park actual = controller.getPark(p1.getDescriptionPark());
        Assert.assertEquals(actual, p1);
    }

    @Test
    void getActualBatery() {
        Scooter s1 = new Scooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.75, 150);
        double actual = s1.getActualBatery() * s1.getMaxBatery();
        double expected = 0.75;
        Assert.assertEquals(actual, expected, 0.01);

    }

    /*@Test
    void suggestEscooters() {
        Scooter b1 = new Scooter("PT050", 12, 1.10, 0.3, 1, "city", 250, 70, 350);
        Scooter b2 = new Scooter("PT051", 16, 1.50, 0.5, 1, "city", 250, 70, 350);

        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 20, 20, 20, "park no2", 10, 10, 25, 25);

        List<Scooter> lstScooters = new LinkedList<>();
        List<String> lstScootersID = new LinkedList<>();

        lstScooters.add(b1);
        lstScooters.add(b2);

        lstScootersID.add(b1.getDescriptionVehicle());
        lstScootersID.add(b2.getDescriptionVehicle());

        when(vehicleDB.getVehicle("PT050")).thenReturn(b1).thenReturn(b1);
        when(vehicleDB.getVehicle("PT051")).thenReturn(b1).thenReturn(b2);
        when(parkDB.getVehiclesFromPark(p1.getDescriptionPark())).thenReturn(lstScootersID);

        User user = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(userDB.getUserWeight(user.getUsername())).thenReturn(user.getWeight());

        double windSpeed = 14;
        when(pathDB.getWindSpeed(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude())).thenReturn(windSpeed);

        double kinetic = 0.6;
        when(pathDB.getKineticCoefficient(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude())).thenReturn(kinetic);

        double windDirection = 45;
        when(pathDB.getwindDirection(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude())).thenReturn(windDirection);

        when(parkDB.getElevationFromCoords(p1.getLatitude(), p1.getLongitude())).thenReturn(10.0);
        when(parkDB.getElevationFromCoords(p2.getLatitude(), p2.getLongitude())).thenReturn(20.0);

        List<Scooter> list = controller.suggestScooter(p1, "User1", p2.getLatitude(), p2.getLongitude());

        Assert.assertEquals(list, lstScooters);

    }*/


}
