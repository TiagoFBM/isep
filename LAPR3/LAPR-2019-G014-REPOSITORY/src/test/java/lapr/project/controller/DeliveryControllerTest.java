package lapr.project.controller;

import lapr.project.data.DeliveryDB;
import lapr.project.data.PointOfInterestDB;
import lapr.project.data.RentDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Delivery;
import lapr.project.model.PointOfInterest;
import lapr.project.model.Rent;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to class DeliveryController
 *
 * @author ines-
 */
class DeliveryControllerTest {

    private static DeliveryDB ddb;
    private static RentDB rdb;
    private static PointOfInterestDB pdb;
    private static Delivery d1;
    private static DeliveryController controller;
    private static java.sql.Date date1;

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
        int year = 2019;
        int month = 12;
        int day = 12;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        date1 = new java.sql.Date(cal.getTimeInMillis());

        ddb = new DeliveryDB();
        pdb = new PointOfInterestDB();
        rdb = new RentDB();
        controller = new DeliveryController();
        d1 = new Delivery(1, 1, 1, date1, "Park 1", 10);
        ddb = mock(DeliveryDB.class);
        pdb = mock(PointOfInterestDB.class);
        rdb = mock(RentDB.class);
        controller.setDeliveryDB(ddb);
        controller.setPointOfInterestDB(pdb);
        controller.setRentDB(rdb);
    }

    /**
     * Teste of method newDelivery
     */
    @Test
    void newDelivery() {
        Delivery actual = controller.newDelivery(1, 1, 1, date1, "Park 1", 10);
        Assert.assertEquals(d1, actual);
    }

    /**
     * Teste of method registerDelivery1 (true)
     */
    @Test
    void registerDelivery1() {
        when(ddb.calculateTravelCost(d1.getDescriptionDelivery())).thenReturn(20L);
        double result = controller.registerDelivery(d1);
        assertEquals(20, result);

    }

    /**
     * Teste of method registerDelivery2 (false)
     */
    @Test
    void registerDelivery2() {
        when(ddb.calculateTravelCost(d1.getDescriptionDelivery())).thenReturn(-1L);
        double result = controller.registerDelivery(d1);

        assertEquals(result, -1L);

    }

    /**
     * Teste of method registerDelivery3 (null)
     */
    @Test
    void registerDelivery3() {
        Delivery d2 = null;
        double result = controller.registerDelivery(d2);
        assertEquals(result, -1L);

    }


    /**
     * Test of method lockVehicle.
     */
    @Test
    void lockVehicle1() {
        PointOfInterest poi = new PointOfInterest("1", "01", 12.2, 14.42, 11);
        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 13);
        Date initial = new Date(Calendar.getInstance().getTimeInMillis());
        Rent r = new Rent(1, "PT050", "PP01", date1, "user1@email.com");
        when(pdb.getPointOfInterest(poi.getLatitude(), poi.getLongitude())).thenReturn(poi);
        when(rdb.getRentByVehicle(bike.getDescriptionVehicle(), r.getUsername())).thenReturn(r.getDescriptionRent());
        when(ddb.getCostsID(r.getDescriptionRent())).thenReturn(0);

        Delivery expected = new Delivery(1,1,0, initial, "1", 0);
        Delivery result = controller.lockVehicle(bike.getDescriptionVehicle(), poi.getLatitude(), poi.getLongitude(), r.getUsername());

        assertEquals(expected.toString(), result.toString());
    }

    /**
     * Test of method lockVehicle (null).
     */
    @Test
    void lockVehicle2() {
        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 13);
        Rent r = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        when(pdb.getPointOfInterest(40,-50)).thenReturn(null);
        when(rdb.getRentByVehicle(bike.getDescriptionVehicle(), r.getUsername())).thenReturn(r.getDescriptionRent());
        when(ddb.getCostsID(r.getDescriptionRent())).thenReturn(0);

        Delivery expected = null;
        Delivery result = controller.lockVehicle(bike.getDescriptionVehicle(), 40, -50, r.getUsername());

        assertEquals(expected, result);
    }

    /**
     * Test of method lockVehicle.
     */
    @Test
    void lockVehicle3() {
        PointOfInterest poi = new PointOfInterest("1", "01", 12.2, 14.42, 11);
        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 13);
        Date initial = new Date(Calendar.getInstance().getTimeInMillis());
        Rent r = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        when(pdb.getPointOfInterest(poi.getPoiDescription())).thenReturn(poi);
        when(rdb.getRentByVehicle(bike.getDescriptionVehicle(), r.getUsername())).thenReturn(r.getDescriptionRent());
        when(ddb.getCostsID(r.getDescriptionRent())).thenReturn(0);

        Delivery expected = new Delivery(1,1,0, initial, "1", 0);
        Delivery result = controller.lockVehicle(bike.getDescriptionVehicle(), poi.getPoiDescription(), r.getUsername());

        assertEquals(expected.toString(), result.toString());
    }
       
     /**
     * Test of method lockVehicle (null).
     */
    @Test
    void lockVehicle4() {
        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 13);
        Rent r = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        when(pdb.getPointOfInterest("2")).thenReturn(null);
        when(rdb.getRentByVehicle(bike.getDescriptionVehicle(), r.getUsername())).thenReturn(r.getDescriptionRent());
        when(ddb.getCostsID(r.getDescriptionRent())).thenReturn(0);

        Delivery expected = null;
        Delivery result = controller.lockVehicle(bike.getDescriptionVehicle(), "2", r.getUsername());
        
        assertEquals(expected, result);
    }

    @Test
    void getAllDeliveries() {
        Delivery delivery1 = new Delivery(1, 1, 1, date1, "Park1", 10);
        Delivery delivery2 = new Delivery(2, 2, 1, date1, "Park1", 20);
        LinkedList<String> list = new LinkedList<>();
        list.add(delivery1.toString());
        list.add(delivery2.toString());
        when(ddb.getAllDeliveries("User1")).thenReturn(list);
        List<String> actual = controller.getAllDeliveries("User1");
        Assert.assertEquals(actual, list);
    }

}
