package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Scooter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetReportAbilityVehiclesFlatTripGreaterXControllerTest {

    static private VehicleDB db;
    static private GetReportAbilityVehiclesFlatTripGreaterXController controller;


    @BeforeAll
    static void DeliveryControllerTest() {
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
        controller = new GetReportAbilityVehiclesFlatTripGreaterXController();
        db = mock(VehicleDB.class);
        controller.setDB(db);
    }

    @Test
    void getListScootersCantMakeXkm() {
        Scooter s1 = new Scooter("s1", 10, 0, 8, "city", 50, 1, 10);
        Scooter s2 = new Scooter("s2", 25, 0, 15, "offroad", 75, 5, 20);
        Scooter s3 = new Scooter("s3", 12, 0, 10, "city", 45, 40, 14);
        List<Scooter> activeScooters = new LinkedList<>();
        activeScooters.add(s1);
        activeScooters.add(s2);
        activeScooters.add(s3);
        when(db.getActiveScooters()).thenReturn(activeScooters);
        List<Scooter> actual = controller.getListScootersCantMakeXkm(10);
        List<Scooter> expected = new LinkedList<>();
        expected.add(s1);
        expected.add(s2);
        assertEquals(expected, actual);
    }
}