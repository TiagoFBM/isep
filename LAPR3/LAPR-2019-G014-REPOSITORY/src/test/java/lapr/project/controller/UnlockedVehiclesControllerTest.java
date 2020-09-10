package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnlockedVehiclesControllerTest {

    private static UnlockedVehiclesController controller;
    private static VehicleDB vdb;

    @BeforeAll
    static void UnlockedVehiclesControllerTest(){
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

        controller = new UnlockedVehiclesController();
        vdb = mock(VehicleDB.class);
        controller.setVehicleDB(vdb);
    }


    @Test
    void getReportUnclockedVehicles() {
        List<Vehicle> list = new LinkedList<>();
        Bicycle b1 = new Bicycle("ePT050", 12, 1.10, 0.3, 15);
        Bicycle b2 = new Bicycle("ePT051", 15, 1.50, 0.3, 20);
        Scooter s1 = new Scooter("ePT053",30,1.20,0.7,"city",1,0.75,150);
        list.add(b1);
        list.add(b2);
        list.add(s1);


        int year = 2019;
        int month = 12;
        int day = 30;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        java.sql.Date initial = new java.sql.Date(cal.getTimeInMillis());


        int year2 = 2020;
        int month2 = 01;
        int day2 = 02;
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, year2);
        cal2.set(Calendar.MONTH, month2 - 1); // <-- months start
        // at 0.
        cal2.set(Calendar.DAY_OF_MONTH, day2);

        java.sql.Date dFinal = new java.sql.Date(cal.getTimeInMillis());

        when(vdb.getReportUnlockedVehicles(initial,dFinal)).thenReturn(list);
        List<Vehicle> actual = controller.getReportUnclockedVehicles(initial, dFinal);
        Assert.assertEquals(actual,list);
    }
}