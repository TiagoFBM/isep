package lapr.project.ui;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lapr.project.assessment.Facade;
import lapr.project.data.DataHandler;
import lapr.project.model.Park;
import lapr.project.utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Main {

    private static Facade oFacade;

    public static void main(String[] args) {
        oFacade = new Facade();

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

        DataHandler dh = new DataHandler();
        try {
            dh.scriptRunner("target/classes/CreateTables.sql");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        testAddUsers("ficheiros/users.csv");
        testAddPark("ficheiros/parks.csv");
        testAddBicycles("ficheiros/bicycles.csv");
        testAddScooters("ficheiros/escooters.csv");

        /*
        testGetNumberOfBicyclesAtPark1(41.16875,-8.68995,"ficheiros/outputBicycles1.txt");
        testGetNumberOfScootersAtPark1(41.16875,-8.68995,"ficheiros/outputScooters1.txt");
        testGetNumberOfBicyclesAtPark2("CasteloQueijo", "ficheiros/outputBicycles2.txt");
        testGetNumberOfScootersAtPark2("CasteloQueijo","ficheiros/outputScooters2.txt");
        oFacade.getNearestParks(41.148302, -8.611029,"ficheiros/outputNearestParks1.txt", 10);
        testLinearDistance(41.14582, -8.61398, 41.14723, -8.60657);
        testGetUserCurrentPoints("tiagomoreira", "ficheiros/points1.txt");
        */
        testUnlockBicycle("b01","tiagomoreira");
    }

    public static void testAddUsers(String file) { // TESTADO
        int num = oFacade.addUsers(file);
        System.out.println(num + " new users were added sucessfully.");
    }

    public static void testAddPark(String file) {   // TESTADO
        int num = oFacade.addParks(file);
        System.out.println(num + " new parks were added sucessfully.");
    }

    public static void testAddPOI(String file) {    // TESTADO
        int num = oFacade.addPOIs(file);
        System.out.println(num + " new points of interest were added sucessfully.");
    }

    public static void testAddBicycles(String file) {   // TESTADO
        int num = oFacade.addBicycles(file);
        System.out.println(num + " new bicycles were added sucessfully.");
    }

    public static void testAddScooters(String file) {   // TESTADO
        int num = oFacade.addEscooters(file);
        System.out.println(num + " new escooters were added sucessfully.");
    }

    public static void testRegisterUser() {     // TESTADO
        if (oFacade.registerUser("joaosilva", "jonny@icloud.com", "12345", "1212121212", 195, 85, BigDecimal.valueOf(9), "M") == 1) {
            System.out.println("User added sucessfuly");
        } else {
            System.out.println("It was not possible to add the new user.");
        }
    }

    public static void testGetNumberOfBicyclesAtPark1(double lat, double lon, String s) {   // TESTADO
        int num = oFacade.getNumberOfBicyclesAtPark(lat, lon, s);
        System.out.println("The number of Bicycles at the park selected is " + num + ". The output was sent to the selected file.");
    }

    public static void testGetNumberOfBicyclesAtPark2(String park, String s) {  // TESTADO
        int num = oFacade.getNumberOfBicyclesAtPark(park, s);
        System.out.println("The number of bicycles at the park selected is " + num + ". The output was sent to the selected file.");
    }

    public static void testGetNumberOfScootersAtPark1(double lat, double lon, String s) {   // TESTADO
        int num = oFacade.getNumberOfEscootersAtPark(lat, lon, s);
        System.out.println("The number of Escooters at the park selected is " + num + ". The output was sent to the selected file.");
    }

    public static void testGetNumberOfScootersAtPark2(String park, String s) {  // TESTADO
        int num = oFacade.getNumberOfEScootersAtPark(park, s);
        System.out.println("The number of Escooters at the park selected is " + num + ". The output was sent to the selected file.");
    }

    public static void testGetNearestParks1(double lat, double lon, String out) {   // TESTADO
        oFacade.getNearestParks(lat, lon, out);
        System.out.println("The nearest parks were sent to the selected file.");
    }

    public static void testGetNearestParks2(double lat, double lon, String out, int raio) {     // TESTADO
        oFacade.getNearestParks(lat, lon, out, raio);
        System.out.println("The nearest parks were sent to the selected file.");
    }

    public static void testLinearDistance(double lat, double lon, double lat2, double lon2) {      // TESTADO
        int dist = oFacade.linearDistanceTo(lat, lat2, lon, lon2);
        System.out.println("The distance between the park " + lat + " & " + lon + " and the park " + lat2 + " & " + lon2 + " is: " + dist);
    }

    public static void testUnlockBicycle(String bicycleDescription, String username) {
        long time = oFacade.unlockBicycle(bicycleDescription, username);
        System.out.println("The time in milliseconds at which the bicycle was unlocked: " + time + "The output was sent to the selected file.");
    }

    public static void testUnlockScooter(String escooterDescription, String username) {
        long time = oFacade.unlockEscooter(escooterDescription, username);
        System.out.println("The time in milliseconds at which the scooter was unlocked: " + time + "The output was sent to the selected file.");
    }

    public static void testUnlockAnyEscooterAtPark(String parkID, String username, String output) {
        long time = oFacade.unlockAnyEscooterAtPark(parkID, username, output);
        System.out.println("The time in milliseconds at which the bicycle was unlocked: " + time + "The output was sent to the selected file.");
    }

    public static void testUnlockAnyEscooterAtParkForDestination(String parkIdentification, String username, double destinyLatitudeInDegrees, double destinyLongitudeInDegrees, String outputFileName) {
        long time = oFacade.unlockAnyEscooterAtParkForDestination(parkIdentification, username, destinyLatitudeInDegrees, destinyLongitudeInDegrees, outputFileName);
        System.out.println("The time in milliseconds at which the scooter was unlocked: " + time + "The output was sent to the selected file.");
    }

    public static void testLockBycicle(String bicycleDescription, double parkLatitude, double parkLongitude, String username) {
        long date = oFacade.lockBicycle(bicycleDescription, parkLatitude, parkLongitude, username);
        System.out.println("The date of the lock in miliseconds is " + date + ".");
    }

    public static void testMostEnergyEfficientRouteBetweenTwoParks(String originParkIdentification, String destinationParkIdentification, String typeOfVehicle, String vehicleSpecs,
                                                                   String username, String outputFileName) {
        long dist = oFacade.mostEnergyEfficientRouteBetweenTwoParks(originParkIdentification, destinationParkIdentification, typeOfVehicle, vehicleSpecs, username, outputFileName);
        System.out.println("The most energy efficient rounte between two parks has " + dist + " meters.");
    }

    public static void testGetUserCurrentPoints(String username, String outputFileName) {
        double points = oFacade.getUserCurrentPoints(username, outputFileName);
        System.out.println("The current points of the user " + username + " is " + points + ". The output was sent to the selected file.");
    }

   /* public static void testShortestRouteBetweenTwoParks1(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees,
                                                         double destinationLongitudeInDegrees, String outputFileName) {
        double dist = oFacade.shortestRouteBetweenTwoParks(originLatitudeInDegrees, originLongitudeInDegrees, destinationLatitudeInDegrees, destinationLongitudeInDegrees, outputFileName);
        System.out.println("The shortest route between the selected parks is " + dist + " meters.");
    }

    public static void testShortestRouteBetweenTwoParks2(String originParkIdentification, String destinationParkIdentification, String outputFileName) {
        double dist = oFacade.shortestRouteBetweenTwoParks(originParkIdentification, destinationParkIdentification, outputFileName);
        System.out.println("The shortest route between the selected parks is " + dist + " meters.");
    }*/

    public static void testshortestRouteBetweenTwoParksForGivenPOIs1(String originParkIdentification, String destinationParkIdentification, String inputPOIs, String outputFileName) {
        double dist = oFacade.shortestRouteBetweenTwoParksForGivenPOIs(originParkIdentification, destinationParkIdentification, inputPOIs, outputFileName);
        System.out.println("The shortest route between the selected parks that passes throw the given points of interest is " + dist + " meters.");
    }

    public static void testshortestRouteBetweenTwoParksForGivenPOIs2(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, String inputPOIs, String outputFileName) {
        double dist = oFacade.shortestRouteBetweenTwoParksForGivenPOIs(originLatitudeInDegrees, originLongitudeInDegrees, destinationLatitudeInDegrees, destinationLongitudeInDegrees, inputPOIs, outputFileName);
        System.out.println("The shortest route between the selected parks that passes throw the given points of interest is " + dist + " meters.");
    }

    public static void testGetInvoiceForMonth(int month, String username, String outputPath) {
        double cost = oFacade.getInvoiceForMonth(month, username, outputPath);
        System.out.println("The invoice cost for the selected month is " + cost + "€.");
    }
}


