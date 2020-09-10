/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lapr.project.data.ParkDB;
import lapr.project.model.Park;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


/**
 * Test class to class CheckAvailableParkingSpotsController
 * @author ines-
 */
public class CheckAvailableParkingSpotsControllerTest {
    
    private static CheckAvailableParkingSpotsController controller;
    private static ParkDB db;

    @BeforeAll
    static void CheckAvailableParkingSpotsController() {
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

        controller = new CheckAvailableParkingSpotsController();
        db = mock(ParkDB.class);
        controller.setParkDB(db);
    }
    
    /**
     * Teste of method getListActiveParksNearCoords
     */
     @Test
    void getListActiveParksNearCoords1() {
         Park p1 = new Park("01", 10, 10, 10, "parque01", 10, 10, 10, 10);
         List<Park> expected = new ArrayList<>();
         expected.add(p1);
         when(db.getNearestParks(10, 10, 1)).thenReturn(expected);
         when(db.getActiveParks()).thenReturn(expected);
         assertEquals(expected, controller.getListActiveParksNearCoords(10, 10));
    }
    
     
    /**
     * Teste of method getListActiveParksNearCoords
     */
     @Test
    void getListActiveParksNearCoords2() {
        List<Park> expected = new ArrayList<>();
        when(db.getNearestParks(50, 50, 1)).thenReturn(expected);
        assertEquals(expected, controller.getListActiveParksNearCoords(50, 50));
    }

    @Test
    void getListActiveParksNearCoords3() {
        Park p1 = new Park("01", 10, 10, 10, "parque01", 10, 10, 10, 10);
        Park p2 = new Park("02", 50, 50, 10, "parque02", 10, 10, 10, 10);
        List<Park> activeParks = new ArrayList<>();
        activeParks.add(p1);
        activeParks.add(p2);
        List<Park> expected = new ArrayList<>();
        expected.add(p1);
        when(db.getNearestParks(10, 10, 1)).thenReturn(expected);
        when(db.getActiveParks()).thenReturn(activeParks);
        assertEquals(expected, controller.getListActiveParksNearCoords(10, 10));
    }
    
    /**
     * Teste of method checkAvailability (true)
     */
     @Test
    void checkAvailability1() {
        Park p1 = new Park("PP01", 60.152712, -20.609297, 18, "Trindade", 10, 20, 220, 16);

        when(db.checkParkAvailability(p1.getDescriptionPark(), "bicycle")).thenReturn(true);

        boolean result = controller.checkAvailability(p1, "bicycle");

        assertTrue(result);
    }
    
    /**
     * Teste of method checkAvailability (false)
     */
      @Test
    void checkAvailability2() {
        Park p1 = new Park("PP01", 60.152712, -20.609297, 18, "Trindade", 10, 20, 220, 16);

        when(db.checkParkAvailability(p1.getDescriptionPark(), "scooter")).thenReturn(false);

        boolean result = controller.checkAvailability(p1, "scooter");

        assertFalse(result);
    }
   
    
}
