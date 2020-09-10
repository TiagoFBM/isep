package lapr.project.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import lapr.project.data.*;
import lapr.project.data.graphs.graphbase.Graph;
import lapr.project.model.*;
import lapr.project.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to class RentVehicleController
 *
 * @author ines-
 */
class RentVehicleControllerTest {

    private static RentVehicleController controller;
    private static VehicleDB vdb;
    private static RentDB rdb;
    private static UserDB udb;
    private static ParkDB pdb;

    private static PathDB pathDB;
    private static PointOfInterestDB poiDB;

    @BeforeAll
    static void RentVehicleController() {
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

        controller = new RentVehicleController();
        vdb = mock(VehicleDB.class);
        rdb = mock(RentDB.class);
        udb = mock(UserDB.class);
        pdb = mock(ParkDB.class);
        pathDB = mock(PathDB.class);
        poiDB = mock(PointOfInterestDB.class);

        controller.setVehicleDB(vdb);
        controller.setRentDB(rdb);
        controller.setUserDB(udb);
        controller.setParkDB(pdb);
        controller.setPathDB(pathDB);
        controller.setPoiDB(poiDB);
    }

    @Test
    void newRent() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Rent r1 = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        Rent r2 = controller.newRent(1, "PT050", "PP01", date1, "user1@email.com");

        assertEquals(r1.toString(), r2.toString());
    }

    /*@Test
    void registerRent1() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Rent r1 = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        when(rdb.addRentToDataBase("PT50", "PP01", new Date(date1.getTime()), "user1@email.com")).thenReturn(true);

        boolean result = controller.registerRent(r1);

        assertTrue(result);
    }*/
    @Test
    void registerRent2() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Rent r1 = new Rent(2, "PT050", "PP01", date1, "user1@email.com");

        when(rdb.addRentToDataBase("PT050", "PP01", date1, "user1@email.com")).thenReturn(2);

        int result = controller.registerRent(r1);

        assertEquals(2, result);
    }

    /*
    @Test
    void calculateAmountOfCaloriesBurned() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Rent r1 = controller.newRent(1, "ePT050", "PP01", date1, "user1@email.com");
        Rent r2 = controller.newRent(2, "ePT050", "PP02", date1, "user1@email.com");
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        Bicycle bike = new Bicycle("ePT050", 12, 1, 2, 1, 13);

        double resultExpected = 682.24;
        when(udb.getUserWeight("User1")).thenReturn(60);
        when(vdb.getVehicleWeight("ePT050")).thenReturn(12.0);
        when(vdb.getBicycleByID("ePT050")).thenReturn(bike);
        when(pdb.getElevationFromCoords(r1.getLatitudeDestination(), r1.getLongitudeDestination())).thenReturn(104.0);
        when(pdb.getElevationFromCoords(r2.getLatitudeDestination(), r2.getLongitudeDestination())).thenReturn(234.0);
        double result = controller.calculateAmountOfCaloriesBurned(us1, r1.getLatitudeDestination(), r1.getLongitudeDestination(), r2.getLatitudeDestination(), r2.getLongitudeDestination(), "ePT050");
        Assert.assertEquals(resultExpected, result, 0.01);
    }*/
    @Test
    void createGraphDistance() {
        Map<String, List<String>> mapPaths = new HashMap<>();

        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        List<String> lstStr = new LinkedList<>();
        lstStr.add("02");
        lstStr.add("03");
        mapPaths.put("01", lstStr);

        when(pathDB.getActivePaths()).thenReturn(mapPaths);

        PointOfInterest p1 = new PointOfInterest("01", "Um", 1, 1, 1);
        PointOfInterest p2 = new PointOfInterest("02", "Dois", 2, 2, 2);
        PointOfInterest p3 = new PointOfInterest("03", "Tres", 3, 3, 3);

        when(poiDB.getPointOfInterest("01")).thenReturn(p1);
        when(poiDB.getPointOfInterest("02")).thenReturn(p2);
        when(poiDB.getPointOfInterest("03")).thenReturn(p3);

        double dist12 = Utils.calculateDistanceBetweenCoordinates(p1.getLatitude(), p2.getLatitude(),
                p1.getLongitude(), p2.getLongitude());
        double dist13 = Utils.calculateDistanceBetweenCoordinates(p1.getLatitude(), p3.getLatitude(),
                p1.getLongitude(), p3.getLongitude());

        grafo.insertVertex(p1);
        grafo.insertVertex(p2);
        grafo.insertVertex(p3);

        grafo.insertEdge(p1, p2, null, dist12);
        grafo.insertEdge(p1, p3, null, dist13);

        Assert.assertEquals(grafo, controller.createGraphDistance());
    }

    @Test
    void createGraphPower() {
        Map<String, List<String>> mapPaths = new HashMap<>();

        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        List<String> lstStr = new LinkedList<>();
        lstStr.add("02");
        lstStr.add("03");
        mapPaths.put("01", lstStr);

        when(pathDB.getActivePaths()).thenReturn(mapPaths);

        PointOfInterest p1 = new PointOfInterest("01", "Um", 1, 1, 1);
        PointOfInterest p2 = new PointOfInterest("02", "Dois", 2, 2, 2);
        PointOfInterest p3 = new PointOfInterest("03", "Tres", 3, 3, 3);

        when(poiDB.getPointOfInterest("01")).thenReturn(p1);
        when(poiDB.getPointOfInterest("02")).thenReturn(p2);
        when(poiDB.getPointOfInterest("03")).thenReturn(p3);

        User u1 = new User("Tiago", "123456789", "F", "tiaguinho@gmail.com", "ticomaster", 320, 50, 0, 0);
        Scooter sc1 = new Scooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.75, 150);

        when(vdb.getScooterById("ePT050")).thenReturn(sc1);
        when(udb.getUserWeight("Tiago")).thenReturn(u1.getWeight());

        double dist12 = controller.calculateEnergyTravel(p1.getLatitude(), p1.getLongitude(),
                p2.getLatitude(), p2.getLongitude(), u1.getUsername());
        double dist13 = controller.calculateEnergyTravel(p1.getLatitude(), p1.getLongitude(),
                p2.getLatitude(), p2.getLongitude(), u1.getUsername());

        grafo.insertVertex(p1);
        grafo.insertVertex(p2);
        grafo.insertVertex(p3);

        grafo.insertEdge(p1, p2, null, dist12);
        grafo.insertEdge(p1, p3, null, dist13);

        Assert.assertEquals(grafo, controller.createGraphPower(u1, sc1.getDescriptionVehicle()));
    }

    @Test
    void createGraphCalories() {
        Map<String, List<String>> mapPaths = new HashMap<>();

        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        List<String> lstStr = new LinkedList<>();
        lstStr.add("02");
        lstStr.add("03");
        mapPaths.put("01", lstStr);

        when(pathDB.getActivePaths()).thenReturn(mapPaths);

        PointOfInterest p1 = new PointOfInterest("01", "Um", 1, 1, 1);
        PointOfInterest p2 = new PointOfInterest("02", "Dois", 2, 2, 2);
        PointOfInterest p3 = new PointOfInterest("03", "Tres", 3, 3, 3);

        when(poiDB.getPointOfInterest("01")).thenReturn(p1);
        when(poiDB.getPointOfInterest("02")).thenReturn(p2);
        when(poiDB.getPointOfInterest("03")).thenReturn(p3);

        User u1 = new User("Tiago", "123456789", "F", "tiaguinho@gmail.com", "ticomaster", 320, 50, 0, 0);
        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 13);

        when(vdb.getBicycleByID("ePT050")).thenReturn(bike);
        when(udb.getUserWeight("Tiago")).thenReturn(u1.getWeight());

        double dist12 = controller.caloriesBurnedByUserBetweenCoords(p1.getLatitude(), p1.getLongitude(),
                p2.getLatitude(), p2.getLongitude(), u1.getUsername());
        double dist13 = controller.caloriesBurnedByUserBetweenCoords(p1.getLatitude(), p1.getLongitude(),
                p2.getLatitude(), p2.getLongitude(), u1.getUsername());

        grafo.insertVertex(p1);
        grafo.insertVertex(p2);
        grafo.insertVertex(p3);

        grafo.insertEdge(p1, p2, null, dist12);
        grafo.insertEdge(p1, p3, null, dist13);

        Assert.assertEquals(grafo, controller.createGraphCalories(u1, bike.getDescriptionVehicle()));
    }

    @Test
    void getDistanceFromRent() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            java.util.Date dataTemp = sdf1.parse("12/12/2019 15:00:00");
            date1 = new Date(dataTemp.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Rent r1 = new Rent(1, "PT050", "PP01", date1, "user1@email.com");

        double lat1 = 123.45;
        double long1 = 543.21;
        when(rdb.getInitialLat(1)).thenReturn(lat1);
        when(rdb.getInitialLong(1)).thenReturn(long1);

        Assert.assertEquals(Utils.calculateDistanceBetweenCoordinates(lat1, r1.getLatitudeDestination(), long1, r1.getLongitudeDestination()), controller.getDistanceFromRent(r1), 0.1f);
    }

    @Test
    void shortestPathBetweenParks1() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 11, 11, 11, "park no2", 10, 10, 25, 25);
        Map<String, List<String>> map = new HashMap<>();
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);
        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        Graph<PointOfInterest, Double> grafo = controller.createGraphDistance();

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();
        double expected = 155941.21;
        double actual = controller.shortestPathBetweenParks(p1, p2, lstPoi);

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    void shortestPathBetweenParks2() {
        Park p1 = null;
        Park p2 = null;
        Map<String, List<String>> map = new HashMap<>();
        List<String> lst = new LinkedList<>();

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        Graph<PointOfInterest, Double> grafo = controller.createGraphDistance();

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();
        double expected = -1;
        double actual = controller.shortestPathBetweenParks(p1, p2, lstPoi);

        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    void shortestPathBetweenParks3() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();
        double expected = 917298.06;
        double actual = controller.shortestPathBetweenParks(p1, p4, lstPoi);
        List<PointOfInterest> expectedList = new LinkedList<>();
        expectedList.add(p1);
        expectedList.add(p3);
        expectedList.add(p4);

        Assert.assertEquals(expected, actual, 0.01);
        assertEquals(expectedList, lstPoi);
    }

    @Test
    void getMostEfficientByCalories() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        Bicycle bike = new Bicycle("ePT050", 12, 1.10, 0.3, 15);
        String id = "ePT050";

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);
        when(vdb.getVehicleWeight(id)).thenReturn(12.0);
        when(vdb.getBicycleByID(id)).thenReturn(bike);

        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();

        // VALOR NÃO TESTADO, MEIO À TOA.
        // TENHO DE FAZER AS CONTAS MANUALMENTE E TESTAR COM VALORES
        Assert.assertEquals(856179.79, controller.getMostEfficientByCalories(p1, p2, lstPoi, us1, id), 0.01);
    }

    @Test
    void getMostEfficientByCalories2() {
        Park p1 = null;
        Park p2 = null;
        Park p3 = null;
        Park p4 = null;

        Map<String, List<String>> map = new TreeMap<>();

        Scooter scooter = new Scooter("ePT050", 12, 1.10, 0.3, "", 120, 54, 250);
        String id = "ePT050";
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        when(vdb.getScooterById(id)).thenReturn(scooter);
        when(udb.getUserWeight(us1.getUsername())).thenReturn(60);

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();

        Assert.assertEquals(-1, controller.getMostEfficientByCalories(p1, p2, lstPoi, us1, id), 0.01);
    }

    @Test
    void getMostEfficientByEnergy() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        Scooter scooter = new Scooter("ePT050", 12, 1.10, 0.3, "", 120, 54, 250);
        String id = "ePT050";
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        when(vdb.getScooterById(id)).thenReturn(scooter);
        when(udb.getUserWeight(us1.getUsername())).thenReturn(60);

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();

        // VALOR NÃO TESTADO, MEIO À TOA.
        // TENHO DE FAZER AS CONTAS MANUALMENTE E TESTAR COM VALORES
        Assert.assertEquals(856179.79, controller.getMostEfficientByEnergy(p1, p2, lstPoi, us1, id), 0.01);
    }

    @Test
    void getMostEfficientByEnergy2() {
        Park p1 = null;
        Park p2 = null;
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        String id = "ePT050";

        LinkedList<PointOfInterest> lstPoi = new LinkedList<>();

        Assert.assertEquals(-1, controller.getMostEfficientByEnergy(p1, p2, lstPoi, us1, id), 0.01);
    }

    @Test
    void shortestRouteGoingByCertainPoints() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        double expected = 917298.06;

        List<PointOfInterest> requiredPois = new LinkedList<>();
        List<PointOfInterest> shortestPath = new LinkedList<>();
        requiredPois.add(p3);

        Assert.assertEquals(expected, controller.shortestRouteGoingByCertainPoints(p1, p4, requiredPois, shortestPath), 0.1f);
    }

    @Test
    void shortestRouteGoingByCertainPoints2() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        double expected = 931364;

        List<PointOfInterest> requiredPois = new LinkedList<>();
        List<PointOfInterest> shortestPath = new LinkedList<>();
        requiredPois.add(p2);

        Assert.assertEquals(expected, controller.shortestRouteGoingByCertainPoints(p1, p4, requiredPois, shortestPath), 0.1f);
    }

    @Test
    void shortestRouteGoingByCertainPoints3() {
        Park p1 = new Park("Park_1", 10, 10, 10, "park no1", 10, 10, 25, 25);
        Park p2 = new Park("Park_2", 15, 16, 50, "park no2", 10, 10, 25, 25);
        Park p3 = new Park("Park_3", 13, 13.5, 12, "park no3", 10, 10, 25, 25);
        Park p4 = new Park("Park_4", 15, 16.7, 13, "park no4", 10, 10, 25, 25);

        Map<String, List<String>> map = new TreeMap<>();

        //1
        List<String> lst = new LinkedList<>();
        lst.add(p2.getDescriptionPark());
        lst.add(p3.getDescriptionPark());
        map.put(p1.getDescriptionPark(), lst);

        //2
        List<String> lst2 = new LinkedList<>();
        lst2.add(p4.getDescriptionPark());
        map.put(p2.getDescriptionPark(), lst2);

        //3
        List<String> lst3 = new LinkedList<>();
        lst3.add(p4.getDescriptionPark());
        lst3.add(p2.getDescriptionPark());
        map.put(p3.getDescriptionPark(), lst3);

        //4
        List<String> lst4 = new LinkedList<>();
        map.put(p4.getDescriptionPark(), lst4);

        when(pathDB.getActivePaths()).thenReturn(map);
        when(poiDB.getPointOfInterest("Park_1")).thenReturn(p1);
        when(poiDB.getPointOfInterest("Park_2")).thenReturn(p2);
        when(poiDB.getPointOfInterest("Park_3")).thenReturn(p3);
        when(poiDB.getPointOfInterest("Park_4")).thenReturn(p4);

        double expected = 931396.39;

        List<PointOfInterest> requiredPois = new LinkedList<>();
        List<PointOfInterest> shortestPath = new LinkedList<>();
        requiredPois.add(p2);
        requiredPois.add(p3);

        Assert.assertEquals(expected, controller.shortestRouteGoingByCertainPoints(p1, p4, requiredPois, shortestPath), 0.1f);
    }
}
