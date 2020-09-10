package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestUpToDateHistoryOfVehiclesControllerTest {


    private static RequestUpToDateHistoryOfVehiclesController controller;
    private static VehicleDB db;


    @BeforeAll
    static void RequestUpToDateHistoryOfVehicleController() {

        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = new RequestUpToDateHistoryOfVehiclesController();
        db = mock(VehicleDB.class);
        controller.setVehicleDB(db);
    }


    @Test
    void getActiveVehicles() {
        Bicycle v1 = new Bicycle("01", 15.12, 21.1, 3.1, 12);
        Bicycle v2 = new Bicycle("02", 11.31, 22.4, 1.1, 20);

        db.addBicycleToDataBase(v1);
        db.addBicycleToDataBase(v2);
        List<Vehicle> activeVehicles = new ArrayList<>();
        activeVehicles.add(v1);
        activeVehicles.add(v2);

        List<Vehicle> expected = new ArrayList<>();
        expected.add(v1);
        expected.add(v2);

        when(db.getActiveVehicles()).thenReturn(activeVehicles);
        assertEquals(expected, controller.getActiveVehicles());
    }

    @Test
    void getHistoryOfVehicle() {
        Bicycle v1 = new Bicycle("01", 15.12, 21.1, 3.1, 12);
        Park p1 = new Park("02", 10, 10, 10, "parque01", 10, 10, 10, 10);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Rent r1 = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        Delivery d1 = new Delivery(04, 03, 1, date1, "parque01", 11.2);


        String expected = String.format(" ----- Rent %d ----- \n" +
                "Vehicle ID: %s\n" +
                "Park ID: %s\n" +
                "Unlock Time: %s\n" +
                "Lock Time: %s\n\n", 1, v1.getDescriptionVehicle(), p1.getDescriptionPark(), r1.getDateHourLocal(), d1.getDateTimeEnd());

        when(db.getHistoryOfVehicle("01")).thenReturn(expected);
        assertEquals(expected, controller.getHistoryOfVehicle("01"));

    }
}