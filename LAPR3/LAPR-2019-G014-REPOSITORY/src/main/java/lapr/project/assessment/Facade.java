package lapr.project.assessment;

import lapr.project.controller.*;
import lapr.project.data.DataHandler;
import lapr.project.data.RentDB;
import lapr.project.model.*;
import lapr.project.utils.Utils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Facade implements Serviceable {

    public Facade() {
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

        DataHandler dh = new DataHandler();
        try {
            dh.scriptRunner("target/classes/CreateTables.sql");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Método que recebe uma lista de linhas e apaga todas as que estão vazias
     * ou que começam por "#". Apaga também a próxima linha após isso
     * (cabeçalho).
     *
     * @param linhas lista de linhas do ficheiro
     */
    private void clearFile(List<String> linhas) {
        Iterator<String> it = linhas.iterator();

        String str = it.next();
        while (str.startsWith(Utils.CHAR_TO_IGNORE) || str.trim().isEmpty()) {
            it.remove();
            str = it.next();
        }
        it.remove();
    }

    @Override
    public int addBicycles(String s) {
        try {
            List<String> lista = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(lista);
            return addBicycles(lista);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int addBicycles(List<String> list) {
        VehicleController controller = new VehicleController();
        ParkController parkController = new ParkController();
        int numBicycles = 0;
        for (String s : list) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);
            //String description_vehicle, double weight, double latitude, double longitude, double aerodynamic, double frontal_area,int wheel_size
            Bicycle bike = controller.newBicycle(vecLine[0].trim(), Double.parseDouble(vecLine[1].trim()), Double.parseDouble(vecLine[4].trim()), Double.parseDouble(vecLine[5].trim()), Integer.parseInt(vecLine[6].trim()));
            if (bike != null && controller.registerBicycle(bike)) {
                numBicycles++;
                Park p = parkController.getPark(Double.parseDouble(vecLine[2].trim()), Double.parseDouble(vecLine[3].trim()));
                if (p != null) {
                    parkController.assignVehiclePark(p.getDescriptionPark(), vecLine[0].trim());
                }
            }
        }
        return numBicycles;
    }

    @Override
    public int addEscooters(String s) {
        try {
            List<String> lista = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(lista);
            return addEscooters(lista);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int addEscooters(List<String> list) {
        VehicleController controller = new VehicleController();
        ParkController parkController = new ParkController();

        int numEscooters = 0;
        for (String s : list) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);
            // String description_vehicle, double weight, double latitude, double longitude, double aerodynamic, double frontal_area, String type, int max_batery, int actual_batery
            Scooter scooter = controller.newScooter(vecLine[0].trim(), Double.parseDouble(vecLine[1].trim()), (vecLine[2].trim()), Integer.parseInt(vecLine[5].trim()), Double.parseDouble(vecLine[6].trim()), Double.parseDouble(vecLine[7].trim()), Double.parseDouble(vecLine[8].trim()), Integer.parseInt(vecLine[9].trim()));
            if (scooter != null && controller.registerScooter(scooter)) {
                numEscooters++;
                Park p = parkController.getPark(Double.parseDouble(vecLine[3].trim()), Double.parseDouble(vecLine[4].trim()));
                if (p != null) {
                    parkController.assignVehiclePark(p.getDescriptionPark(), vecLine[0].trim());
                }

            }
        }
        return numEscooters;
    }

    @Override
    public int addParks(String s) {
        try {
            List<String> lista = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(lista);
            return addPark(lista);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int addPark(List<String> list) {
        ParkController controller = new ParkController();
        int numPark = 0;
        for (String s : list) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);
            //String description_vehicle, double weight, double latitude, double longitude, double aerodynamic, double frontal_area,int wheel_size
            Park park = controller.newPark(vecLine[0].trim(), Double.parseDouble(vecLine[1].trim()), Double.parseDouble(vecLine[2].trim()), Integer.parseInt(vecLine[3].trim()), vecLine[4].trim(), Integer.parseInt(vecLine[5].trim()), Integer.parseInt(vecLine[6].trim()), Integer.parseInt(vecLine[7].trim()), Integer.parseInt(vecLine[8].trim()));
            if (park != null && controller.addPark(park)) {
                numPark++;
            }
        }
        return numPark;
    }

    @Override
    public int removePark(String s) {
        ParkController controller = new ParkController();
        if (controller.removePark(s)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int addPOIs(String s) {
        try {
            List<String> l = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(l);
            return addPOIs(l);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int addPOIs(List<String> linhas) {
        AddTouristicPointOfInterestController controller = new AddTouristicPointOfInterestController();

        int numberOfAdded = 0;
        int elevation = 0;
        String description = "";

        for (String s : linhas) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);

            if (!vecLine[2].trim().isEmpty()) {
                elevation = Integer.parseInt(vecLine[2].trim());
            }

            if (!vecLine[3].trim().isEmpty()) {
                description = vecLine[3].trim();
            }

            PointOfInterest poi = controller.createNewPOI(description, Double.parseDouble(vecLine[0].trim()), Double.parseDouble(vecLine[1].trim()), elevation);
            if (poi != null && controller.registerPointOfInterest(poi)) {
                numberOfAdded++;
            }
        }
        return numberOfAdded;
    }

    @Override
    public int addUsers(String s) {
        try {
            List<String> l = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(l);
            return addUsers(l);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    private int addUsers(List<String> linhas) {
        RegisterUserController controller = new RegisterUserController();
        int numberOfAdded = 0;

        for (String s : linhas) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);
            //park identification;latitude;longitude;elevation;park description;max number of bicycles;max number of escooters;park input voltage;park input current
            User user = controller.newUser(vecLine[0].trim(), vecLine[5].trim(), vecLine[6].trim(), vecLine[1].trim(), vecLine[7].trim(), Integer.parseInt(vecLine[3].trim()), Integer.parseInt(vecLine[2].trim()), Double.parseDouble(vecLine[4].trim()), Utils.START_CYCLING_AVERAGE_SPEED);
            if (user != null && controller.registerUser(user)) {
                numberOfAdded++;
            }
        }
        return numberOfAdded;
    }

    @Override
    public int addPaths(String s) {
        try {
            List<String> l = Files.lines(Paths.get(s)).collect(Collectors.toList());
            clearFile(l);
            return addPaths(l);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public int addPaths(List<String> linhas) {
        PathController controller = new PathController();
        int numberOfAdded = 0;
        double kinectic = 0;
        double windDirection = 0;
        double windSpeed = 0;

        for (String s : linhas) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);

            if (!(vecLine[6].trim()).isEmpty()) {
                windSpeed = Double.parseDouble((vecLine[6].trim()));
            }

            if (!(vecLine[5].trim()).isEmpty()) {
                windDirection = Double.parseDouble((vecLine[5].trim()));
            }

            if (!(vecLine[4].trim()).isEmpty()) {
                kinectic = Double.parseDouble((vecLine[4].trim()));
            }

            if (controller.registerPath(Double.parseDouble(vecLine[0].trim()), Double.parseDouble(vecLine[1].trim()),
                    Double.parseDouble(vecLine[2].trim()), Double.parseDouble(vecLine[3].trim()), kinectic, windDirection, windSpeed)) {
                numberOfAdded++;
            }
        }
        return numberOfAdded;
    }

    @Override
    public int getNumberOfBicyclesAtPark(double v, double v1, String s) {
        ParkController parkController = new ParkController();
        Park p = parkController.getPark(v, v1);
        List<Bicycle> bicyclesInPark = parkController.getBicyclesFromPark(p.getDescriptionPark());
        bicyclesInPark.sort(Comparator.comparing(Vehicle::getDescriptionVehicle));
        int numberOfBicycles = bicyclesInPark.size();
        try {
            Formatter f = new Formatter(new File(s));

            f.format("%s", "bicycle description;weight;park latitude;park longitude;aerodynamic coefficient;frontal area;wheel size");
            for (Bicycle b : bicyclesInPark) {
                f.format("%n");
                f.format("%s;%.2f;%.6f;%.6f;%.2f;%.1f;%d", b.getDescriptionVehicle(), b.getWeight(), p.getLatitude(), p.getLongitude(), b.getAerodynamic(), b.getFrontalArea(), b.getWheelSize());
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return numberOfBicycles;
    }

    @Override
    public int getNumberOfBicyclesAtPark(String s, String s1) {
        ParkController parkController = new ParkController();
        Park p = parkController.getPark(s);
        List<Bicycle> bicyclesInPark = parkController.getBicyclesFromPark(p.getDescriptionPark());
        bicyclesInPark.sort(Comparator.comparing(Vehicle::getDescriptionVehicle));
        int numberOfBicycles = bicyclesInPark.size();
        try {
            Formatter f = new Formatter(new File(s1));

            f.format("%s", "bicycle description;weight;park latitude;park longitude;aerodynamic coefficient;frontal area;wheel size");
            for (Bicycle b : bicyclesInPark) {
                f.format("%n");
                f.format("%s;%.2f;%.6f;%.6f;%.2f;%.1f;%d", b.getDescriptionVehicle(), b.getWeight(), p.getLatitude(), p.getLongitude(), b.getAerodynamic(), b.getFrontalArea(), b.getWheelSize());
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return numberOfBicycles;
    }

    @Override
    public int getNumberOfEscootersAtPark(double v, double v1, String s) {
        ParkController parkController = new ParkController();

        Park p = parkController.getPark(v, v1);

        List<Scooter> scootersInPark = parkController.getScootersFromPark(p.getDescriptionPark());
        scootersInPark.sort(Comparator.comparing(Vehicle::getDescriptionVehicle));
        int numberOfBicycles = scootersInPark.size();
        try {
            Formatter f = new Formatter(new File(s));

            f.format("%s", "escooter description;weight;type;park latitude;park longitude;max battery capacity;actual battery capacity;aerodynamic coefficient;frontal area;motor");
            for (Scooter scooter : scootersInPark) {
                f.format("%n");
                f.format("%s;%.2f;%s;%.6f;%.6f;%d;%d%.2f%.1f%d", scooter.getDescriptionVehicle(), scooter.getWeight(), scooter.getType(), p.getLatitude(),
                        p.getLongitude(), scooter.getMaxBatery(), (int) scooter.getActualBatery(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getPotency());
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return numberOfBicycles;

    }

    @Override
    public int getNumberOfEScootersAtPark(String s, String s1) {
        ParkController parkController = new ParkController();

        Park p = parkController.getPark(s);

        List<Scooter> scootersInPark = parkController.getScootersFromPark(p.getDescriptionPark());
        scootersInPark.sort(Comparator.comparing(Vehicle::getDescriptionVehicle));
        int numberOfBicycles = scootersInPark.size();
        try {
            Formatter f = new Formatter(new File(s1));

            f.format("%s", "escooter description;weight;type;park latitude;park longitude;max battery capacity;actual battery capacity;aerodynamic coefficient;frontal area;motor");
            for (Scooter scooter : scootersInPark) {
                f.format("%n");
                f.format("%s;%.2f;%s;%.6f;%.6f;%d;%d%.2f%.1f%d", scooter.getDescriptionVehicle(), scooter.getWeight(), scooter.getType(), p.getLatitude(),
                        p.getLongitude(), scooter.getMaxBatery(), (int) scooter.getActualBatery(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getPotency());
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return numberOfBicycles;
    }

    @Override
    public void getNearestParks(double v, double v1, String s) {
        ParkController parkController = new ParkController();

        List<Park> p = parkController.getListNearestParks(v, v1);
        try {
            Formatter f = new Formatter(new File(s));
            f.format("%s", "latitude;longitude;distance in meters");
            for (Park park : p) {
                f.format("%n%.6f;%.6f;%d", park.getLatitude(), park.getLongitude(), (int) (Utils.calculateDistanceBetweenCoordinates(v, park.getLatitude(), v1, park.getLongitude())));
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void getNearestParks(double v, double v1, String s, int i) {
        ParkController parkController = new ParkController();

        List<Park> p = parkController.getListNearestParks(v, v1, i);
        try {
            Formatter f = new Formatter(new File(s));
            f.format("%s", "latitude;longitude;distance in meters");
            for (Park park : p) {
                f.format("%n%.6f;%.6f;%d", park.getLatitude(), park.getLongitude(), (int) (Utils.calculateDistanceBetweenCoordinates(v, park.getLatitude(), v1, park.getLongitude())));
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public int getFreeBicycleSlotsAtPark(String s, String s1) {
        ParkController controller = new ParkController();
        return controller.getFreeBicycleSlotsAtPark(s);
    }

    @Override
    public int getFreeEscooterSlotsAtPark(String s, String s1) {
        ParkController controller = new ParkController();
        return controller.getFreeScooterSlotsAtPark(s);
    }

    @Override
    public int getFreeSlotsAtParkForMyLoanedVehicle(String s, String s1) {
        RentVehicleController controller = new RentVehicleController();
        return controller.getFreeSlotsAtParkForMyLoanedVehicle(s,s1);
    }

    @Override
    public int linearDistanceTo(double v, double v1, double v2, double v3) {
        return (int) Utils.calculateDistanceBetweenCoordinates(v, v1, v2, v3);
    }

    @Override
    public int pathDistanceTo(double v, double v1, double v2, double v3) {
        RentVehicleController controller = new RentVehicleController();
        return (int) controller.pathDistanceTo(v, v1, v2, v3);
    }

    @Override
    public long unlockBicycle(String s, String s1) {
        Calendar cal = Calendar.getInstance();
        RentVehicleController rentController = new RentVehicleController();

        Rent rent = rentController.unlockVehicle(s, s1);
        rentController.registerRent(rent);

        return cal.getTimeInMillis();
    }


    @Override
    public long unlockEscooter(String s, String s1) {

        Calendar cal = Calendar.getInstance();
        RentVehicleController rentController = new RentVehicleController();

        Rent rent = rentController.unlockVehicle(s, s1);
        rentController.registerRent(rent);

        return cal.getTimeInMillis();
    }

    @Override
    public long lockBicycle(String bicycleDescription, double parkLatitude, double parkLongitude, String username) {
        Calendar cal = Calendar.getInstance();
        DeliveryController controller = new DeliveryController();
        controller.lockVehicle(bicycleDescription, parkLatitude, parkLongitude, username);
        return cal.getTimeInMillis();
    }

    @Override
    public long lockBicycle(String bicycleDescription, String parkDescription, String username) {
        Calendar cal = Calendar.getInstance();
        DeliveryController controller = new DeliveryController();
        controller.lockVehicle(bicycleDescription, parkDescription, username);
        return cal.getTimeInMillis();

    }

    @Override
    public long lockEscooter(String scooterDescription, double parkLatitude, double parkLongitude, String username) {
        Calendar cal = Calendar.getInstance();
        DeliveryController controller = new DeliveryController();
        Delivery delivery = controller.lockVehicle(scooterDescription, parkLatitude, parkLongitude, username);
        controller.registerDelivery(delivery);
        return cal.getTimeInMillis();
    }

    @Override
    public long lockEscooter(String scooterDescription, String parkDescription, String username) {
        Calendar cal = Calendar.getInstance();
        DeliveryController controller = new DeliveryController();
        controller.lockVehicle(scooterDescription, parkDescription, username);
        return cal.getTimeInMillis();
    }

    @Override
    public int registerUser(String username, String email, String password, String visaCardNumber, int height, int weight, BigDecimal bigDecimal, String gender) {
        RegisterUserController controller = new RegisterUserController();

        User user = controller.newUser(username, visaCardNumber, gender, email, password, weight, height, bigDecimal.doubleValue(), 0);
        if (user != null && controller.registerUser(user)) {
            return Utils.SUCCESS;
        }
        return Utils.NOT_SUCCESS;
    }


    @Override
    public long unlockAnyEscooterAtPark(String s, String s1, String s2) {
        Calendar cal = Calendar.getInstance();
        RentVehicleController rentController = new RentVehicleController();
        ParkController parkController = new ParkController();

        Rent rent = rentController.unlockAnyEscooterAtPark(s, s1);
        rentController.registerRent(rent);
        Scooter scooter = rentController.getScooterByIdRent(rent.getDescriptionRent());
        Park park = parkController.getPark(s);


        try {
            Formatter f = new Formatter(new File(s2 + "/paths.csv"));

            f.format("escooter description;weight;type;park latitude;park longitude;max battery capacity;actual battery capacity;aerodynamic coefficient;frontal area;motor");
            f.format("%s; %.2f; %s, %.2f, %.2f, %d, %.2f, %.2f, %.2f, %d ",
                    rent.getDescriptionRent(), scooter.getWeight(), scooter.getType(), park.getLatitude(), park.getLongitude(), scooter.getMaxBatery(), scooter.getActualBatery(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getPotency());
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return cal.getTimeInMillis();
    }

    @Override
    public long unlockAnyEscooterAtParkForDestination(String s, String s1, double v, double v1, String s2) {
        Calendar cal = Calendar.getInstance();
        SuggestEscootersToGoFromOneParkToAnother sug = new SuggestEscootersToGoFromOneParkToAnother();
        RentDB rentdb=new RentDB();
        Park park=sug.getPark(s);
        List<Scooter> lstSuggestScooters = sug.suggestScooter(park, s1, v, v1);
        try {
            Formatter f = new Formatter(new File(s2 + "/escooters.csv"));
            Scooter scooter = lstSuggestScooters.get(0);
            java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
            int x=rentdb.addRentToDataBase(scooter.getDescriptionVehicle(), park.getDescriptionPark(), date,s2);
            f.format("escooter description;weight;type;park latitude;park longitude;max battery capacity;actual battery capacity;aerodynamic coefficient;frontal area;motor");
            f.format("%s; %.2f; %s, %.2f, %.2f, %d, %.2f, %.2f, %.2f, %d",
                    scooter.getDescriptionVehicle(), scooter.getWeight(), scooter.getType(), park.getLatitude(), park.getLongitude(), scooter.getMaxBatery(), scooter.getActualBatery(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getPotency());
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return cal.getTimeInMillis();
    }

    @Override
    public int suggestEscootersToGoFromOneParkToAnother(String s, String s1, double v, double v1, String s2) {
        SuggestEscootersToGoFromOneParkToAnother sug = new SuggestEscootersToGoFromOneParkToAnother();
        Park park=sug.getPark(s);
        List<Scooter> lstSuggestScooters = sug.suggestScooter(park, s1, v, v1);
        try {
            Formatter f = new Formatter(new File(s2));
            for (Scooter scooter : lstSuggestScooters) {
                f.format("escooter description;weight;type;park latitude;park longitude;max battery capacity;actual battery capacity;aerodynamic coefficient;frontal area;motor");
                f.format("%s; %.2f; %s, %.2f, %.2f, %d, %.2f, %.2f, %.2f, %d ",
                        scooter.getDescriptionVehicle(), scooter.getWeight(), scooter.getType(), park.getLatitude(), park.getLongitude(), scooter.getMaxBatery(), scooter.getActualBatery(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getPotency());
            }
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return lstSuggestScooters.size();
    }

    @Override
    public long mostEnergyEfficientRouteBetweenTwoParks(String s, String s1, String s2, String s3, String s4, String s5) {
        RentVehicleController rentController = new RentVehicleController();
        ParkController parkController = new ParkController();
        RegisterUserController userController = new RegisterUserController();
        LinkedList<PointOfInterest> list = new LinkedList<>();

        Park parkOrigin = parkController.getPark(s);
        Park parkDestin = parkController.getPark(s1);
        User user = userController.getUser(s4);

        long distance = -1;

        if (s2.equalsIgnoreCase("bicycle")) {
            distance = (long) rentController.getMostEfficientByCalories(parkOrigin, parkDestin, list, user, s3);
        } else {
            if (s2.equalsIgnoreCase("escooter")) {
                distance = (long) rentController.getMostEfficientByEnergy(parkOrigin, parkDestin, list, user, s3);
            }
        }

        try {
            Formatter f = new Formatter(new File(s5));
            f.format("%s","latitudeA;longitudeA;latitudeB;longitudeB;kinetic coefficient;wind direction;wind speed");
            for (int j = 0; j < list.size() - 1; j++) {
                f.format("%n%.6f;%.6f;%.6f;%.6f;;;", list.get(0).getLatitude(), list.get(0).getLongitude(), list.get(1).getLatitude(), list.get(1).getLongitude());
            }
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return distance;
    }

    @Override
    public double getUserCurrentDebt(String username, String outputFile) {
        GenerateInvoiceController controller = new GenerateInvoiceController();
        List<String> list = controller.generateInvoices(username);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write("longitude;destination park latitude;destination park longitude;total time spent in seconds;charged valuevehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park");
            writer.newLine();
            for (String s : list) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    @Override
    public double getUserCurrentPoints(String username, String outputFile) {
        DeliveryController deliveryController = new DeliveryController();
        List<String> list = deliveryController.getAllDeliveries(username);
        double points = 0;
        try {
            Formatter f = new Formatter(new File(outputFile));
            f.format("%s", "vehicle description;vehicle unlock time;vehicle lock time;origin park latitude;origin park longitude;origin park elevation;destination park latitude;destination park longitude;destination park elevation;elevation difference;points");
            for (String s : list) {
                f.format("%s", s);
            }

            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return points;
    }

    @Override

    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(double v, double v1, double v2,
                                                                            double v3, String s) {
        RentVehicleController controller = new RentVehicleController();
        return controller.caloriesBurnedByUserBetweenCoords(v, v1, v2, v3, s);
    }

    @Override
    public long forHowLongAVehicleIsUnlocked(String s) {
        RentVehicleController controller = new RentVehicleController();
        return controller.forHowLongAVehicleIsUnlocked(s);
    }

    @Override
    public long shortestRouteBetweenTwoParks(double v, double v1, double v2, double v3, int i, String s) {
        RentVehicleController rentController = new RentVehicleController();
        ParkController parkController = new ParkController();
        LinkedList<PointOfInterest> list = new LinkedList<>();

        Park parkOrigin = parkController.getPark(v, v1);
        Park parkDestin = parkController.getPark(v2, v3);

        long distance = (long) rentController.shortestPathBetweenParks(parkOrigin, parkDestin, list);

        try {
            Formatter f = new Formatter(new File(s));
            f.format("%s","latitudeA;longitudeA;latitudeB;longitudeB;kinetic coefficient;wind direction;wind speed");
            for (int j = 0; j < list.size() - 1; j++) {
                f.format("%n%.6f;%.6f;%.6f;%.6f;;;", list.get(0).getLatitude(), list.get(0).getLongitude(), list.get(1).getLatitude(), list.get(1).getLongitude());
            }
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return distance;
    }

    @Override
    public long shortestRouteBetweenTwoParks(String s, String s1, int i, String s2) {
        RentVehicleController rentController = new RentVehicleController();
        ParkController parkController = new ParkController();
        LinkedList<PointOfInterest> list = new LinkedList<>();

        Park parkOrigin = parkController.getPark(s);
        Park parkDestin = parkController.getPark(s1);

        long distance = (long) rentController.shortestPathBetweenParks(parkOrigin, parkDestin, list);

        try {
            Formatter f = new Formatter(new File(s2));
            f.format("%s","latitudeA;longitudeA;latitudeB;longitudeB;kinetic coefficient;wind direction;wind speed");
            for (int j = 0; j < list.size() - 1; j++) {
                f.format("%n%.6f;%.6f;%.6f;%.6f;;;", list.get(0).getLatitude(), list.get(0).getLongitude(), list.get(1).getLatitude(), list.get(1).getLongitude());
            }
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return distance;
    }

    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(String s, String s1, String s2, String s3) {
        RentVehicleController rentController = new RentVehicleController();
        LinkedList<PointOfInterest> list = new LinkedList<>();
        ParkController parkController = new ParkController();

        Park parkOrigin = parkController.getPark(s);
        Park parkDestin = parkController.getPark(s1);

        List<PointOfInterest> l = new LinkedList<>();
        try {
            l = readFilePois(Files.lines(Paths.get(s2)).collect(Collectors.toList()));
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return (long) rentController.shortestRouteGoingByCertainPoints(parkOrigin, parkDestin, l, list);
    }

    private List<PointOfInterest> readFilePois(List<String> linhas) {
        List<PointOfInterest> listPois = new LinkedList<>();
        AddTouristicPointOfInterestController poiController = new AddTouristicPointOfInterestController();
        clearFile(linhas);

        for (String s : linhas) {
            String[] vecLine = s.trim().split(Utils.FILES_SPLITTER);

            if (!vecLine[0].isEmpty() && !vecLine[1].isEmpty() && !vecLine[2].isEmpty()) {

                double latitude = Double.parseDouble(vecLine[0]);
                double longitude = Double.parseDouble(vecLine[1]);
                PointOfInterest poi = poiController.getPointOfInterest(latitude, longitude);
                listPois.add(poi);

            }
        }
        return listPois;
    }

    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(double v, double v1, double v2, double v3, String s, String s1) {
        ParkController parkController = new ParkController();
        RentVehicleController rentController = new RentVehicleController();
        LinkedList<PointOfInterest> list = new LinkedList<>();

        Park parkOrigin = parkController.getPark(v, v1);
        Park parkDestin = parkController.getPark(v2, v3);

        List<PointOfInterest> l = new LinkedList<>();
        try {
            l = readFilePois(Files.lines(Paths.get(s)).collect(Collectors.toList()));
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        return (long) rentController.shortestRouteGoingByCertainPoints(parkOrigin, parkDestin, l, list);
    }

    @Override
    public long getParkChargingReport(String s, String s1) {
        GetParkReportController gprc = new GetParkReportController();
        List<Scooter> lstScooters = gprc.getListScooterFromPark(s1);
        try {
            Formatter f = new Formatter(new File(s));
            f.format("%s","escooter description;actual battery capacity;time to finish charge in seconds");
            for (Scooter scooter : lstScooters) {
                gprc.updateActualBatery(scooter);
                int time = (int) gprc.getChargingTimeLeft(scooter, lstScooters.size(), s1);
                f.format("%s;%.2f;%d", scooter.getDescriptionVehicle(), scooter.getActualBatery(), time);
            }
            f.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return gprc.getVehiclesWithout100perBatery(s1);
    }

    @Override
    public int suggestRoutesBetweenTwoLocations(String s, String s1, String s2, String s3, String s4, int i, boolean b, String s5, String s6, String s7) {
        return 0;
    }

    @Override
    public double getInvoiceForMonth(int month, String username, String outputPath) {
        GenerateInvoiceController controller = new GenerateInvoiceController();
        BufferedWriter writer = null;
        //LIST OF THE INVOICES
        //Total cost
        double totalCost = 0;
        List<Invoice> list = controller.generateInvoices(month, username);
        //USERNAME
        //Previous Points
        int previousPoints = controller.getPreviousPoints(username);
        //Earned Points
        double earnedPoints = controller.getEarnedPoints(username);
        //Discounted Points
        int discountedPoints = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Invoice invoice : list) {
            totalCost += invoice.getTotalCost();
            stringBuilder.append(invoice.toString());
        }

        while (previousPoints >= Utils.VALUES_TO_SWAP_POINTS && totalCost >= 1) {
            totalCost = totalCost - 1;
            previousPoints = previousPoints - Utils.VALUES_TO_SWAP_POINTS;
            discountedPoints += Utils.VALUES_TO_SWAP_POINTS;
        }

        int actualPoints = previousPoints;

        try {

            writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write("USERNAME + " + username + "\n");

            writer.write("Previous points:" + previousPoints);
            writer.newLine();
            writer.write("Earned points:" + earnedPoints);
            writer.newLine();
            writer.write("Discounted points:" + discountedPoints);
            writer.newLine();
            writer.write("Actual points:" + actualPoints);
            writer.newLine();
            writer.write("Charged value:" + totalCost);
            writer.newLine();
            writer.write("vehicle description;vehicle unlock time;vehicle lock time;origin park;destination park;total time spent in seconds;charged value");
            for (Invoice i : list) {
                writer.newLine();
                writer.write(i.getDescriptionVehicle() + ";" + i.getDateTimeBegin() + ";" + i.getDateTimeFinal() + ";" + i.getSource() + ";" + i.getDestiny() + ";" + ((i.getDateTimeFinal().getTime() - i.getDateTimeBegin().getTime()) / 1000) + ";" + i.getTotalCost());
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalCost;
    }

    public double calculateDistanceFromUserToPark(double latitudeUser, double longitudeUser, String park) {
        ParkController controller = new ParkController();
        return controller.getDistanceToPark(latitudeUser, longitudeUser, park);
    }
}
