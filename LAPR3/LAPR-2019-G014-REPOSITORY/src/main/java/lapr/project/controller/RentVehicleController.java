package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Utils;
import lapr.project.data.graphs.adjacencymatrix.AdjacencyMatrixGraph;
import lapr.project.data.graphs.adjacencymatrix.EdgeAsDoubleGraphAlgorithms;
import lapr.project.data.graphs.graphbase.Edge;
import lapr.project.data.graphs.graphbase.Graph;
import lapr.project.data.graphs.graphbase.GraphAlgorithms;

import java.sql.Date;
import java.util.*;

public class RentVehicleController {

    private RentDB rentDB;
    private UserDB userDB;
    private VehicleDB vehicleDB;
    private ParkDB parkDB;
    private final RegisterRent registerRent;
    private PointOfInterestDB poiDB;
    private PathDB pathDB;

    public RentVehicleController() {
        rentDB = new RentDB();
        userDB = new UserDB();
        vehicleDB = new VehicleDB();
        parkDB = new ParkDB();
        poiDB = new PointOfInterestDB();
        registerRent = new RegisterRent();
        pathDB = new PathDB();
    }

    public Graph<PointOfInterest, Double> createGraphDistance() {
        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        Map<String, List<String>> mapPaths = pathDB.getActivePaths();

        for (String p : mapPaths.keySet()) {
            PointOfInterest pointA = poiDB.getPointOfInterest(p);

            for (String point : mapPaths.get(p)) {
                PointOfInterest pointB = poiDB.getPointOfInterest(point);

                double dist = Utils.calculateDistanceBetweenCoordinates(pointA.getLatitude(), pointB.getLatitude(),
                        pointA.getLongitude(), pointB.getLongitude());

                grafo.insertEdge(pointA, pointB, null, dist);
            }
        }

        return grafo;
    }

    public Graph<PointOfInterest, Double> createGraphCalories(User currentUser, String idScooter) {
        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        Map<String, List<String>> mapPaths = pathDB.getActivePaths();

        for (String p : mapPaths.keySet()) {
            PointOfInterest pointA = poiDB.getPointOfInterest(p);

            if (!grafo.getKeySet().contains(pointA)) {
                grafo.insertVertex(pointA);
            }

            for (String point : mapPaths.get(p)) {
                PointOfInterest pointB = poiDB.getPointOfInterest(point);

                double dist = caloriesBurnedByUserBetweenCoords(pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(), pointB.getLongitude(), currentUser.getUsername());

                if (!grafo.getKeySet().contains(pointB)) {
                    grafo.insertVertex(pointB);
                }

                grafo.insertEdge(pointA, pointB, null, dist);
            }
        }
        return grafo;
    }

    public Graph<PointOfInterest, Double> createGraphPower(User currentUser, String idScooter) {
        Graph<PointOfInterest, Double> grafo = new Graph<>(true);

        Map<String, List<String>> mapPaths = pathDB.getActivePaths();

        for (String p : mapPaths.keySet()) {
            PointOfInterest pointA = poiDB.getPointOfInterest(p);

            if (!grafo.getKeySet().contains(pointA)) {
                grafo.insertVertex(pointA);
            }

            for (String point : mapPaths.get(p)) {
                PointOfInterest pointB = poiDB.getPointOfInterest(point);

                double dist = calculateEnergyTravel(pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(), pointB.getLongitude(), currentUser.getUsername());

                if (!grafo.getKeySet().contains(pointB)) {
                    grafo.insertVertex(pointB);
                }

                grafo.insertEdge(pointA, pointB, null, dist);
            }
        }
        return grafo;
    }

    /**
     * Create a new rental
     *
     * @param descriptionRent description of rent
     * @param descriptionVehicle description of vehicle
     * @param descriptionPoint description of point
     * @param dateTimeBegin date and time begin of rent
     * @param dateHourLocal date and hour local
     * @param username usarname
     * @param latitude latitude of rent
     * @param longitude longitude of rent
     * @return new rent
     */
    public Rent newRent(int descriptionRent, String descriptionVehicle, String descriptionPoint, java.sql.Date dateTimeBegin, java.sql.Date dateHourLocal, String username, double latitude, double longitude) {
        return registerRent.newRent(descriptionRent, descriptionVehicle, descriptionPoint, dateTimeBegin, dateHourLocal, username, latitude, longitude);
    }

    public Rent newRent(int descriptionRent, String descriptionVehicle, String descriptionPoint, Date dateTimeBegin, String username) {
        return registerRent.newRent(descriptionRent, descriptionVehicle, descriptionPoint, dateTimeBegin, username);
    }

    /**
     * Register the Rent
     *
     * @param rent rental to register
     * @return true if it was well registered and false if it wasn't
     */
    public int registerRent(Rent rent) {
        return rentDB.addRentToDataBase(rent.getDescriptionVehicle(), rent.getDescriptionPoint(), rent.getDateTimeBegin(), rent.getUsername());
    }

    public double calculatePotencyGeneratedByUser(double userWeight, double bicycleWeight,
                                                  double averageUserSpeed, double kineticCoefficient,
                                                  double inclination, double aerodynamicFriction,
                                                  double windSpeed, double windDirection) {
        return Utils.GRAVITY_FORCE * (userWeight + bicycleWeight) * averageUserSpeed * (kineticCoefficient + inclination) + (aerodynamicFriction * Math.pow(windSpeed, 2) * averageUserSpeed * Math.cos(windDirection));
    }

    public double caloriesBurnedByUserBetweenCoords(double lat1, double long1, double lat2,
                                                    double long2, String username) {
        double userWeight = userDB.getUserWeight(username);
        double elevation = calculateElevationBetweenCoordinates(lat1, long1, lat2, long2);
        double distance = Utils.calculateDistanceBetweenCoordinates(lat1, lat2, long1, long2);
        double inclination = elevation / distance;
        double aerodynamicDrag = 0.5 * Utils.STANDARD_AREA * Utils.AERODYNAMIC_DRAG_COEFFICIENT;

        double windSpeed = pathDB.getWindSpeed(lat1, long1, lat2, long2);
        double averageSpeed = userDB.getUserAverageSpeed(username);
        double kineticCoefficient = pathDB.getKineticCoefficient(lat1, long1, lat2, long2);
        double windDirection = pathDB.getwindDirection(lat1, long1, lat2, long2);
        if (averageSpeed == 0) {
            averageSpeed = Utils.AVERAGE_CYCLING_SPEED; // m/s
        }
        double timeSpentCycling = distance / averageSpeed;

        double power = calculatePotencyGeneratedByUser(userWeight, Utils.AVERAGE_WEIGHT_BYCICLE,
                averageSpeed, kineticCoefficient, inclination, aerodynamicDrag, windSpeed, windDirection);

        return (power / timeSpentCycling) / 4.1855;


    }

    /**
     * Calculates de energy travel between coordinates
     *
     * @param lat1 latitude point 1
     * @param long1 longitude point 1
     * @param lat2 latitude point 2
     * @param long2 longitude point 2
     * @param username actual client
     * @return energy travel
     */
    public double calculateEnergyTravel(double lat1, double long1, double lat2, double long2, String username) {

        double userWeight = userDB.getUserWeight(username);
        double distance = Utils.calculateDistanceBetweenCoordinates(lat1, lat2, long1, long2);
        double elevation = calculateElevationBetweenCoordinates(lat1, long1, lat2, long2);
        double inclination = elevation / distance;

        double windSpeed = pathDB.getWindSpeed(lat1, long1, lat2, long2);
        double aerodynamicDrag = 0.5 * Utils.STANDARD_AREA * Utils.AERODYNAMIC_DRAG_COEFFICIENT;
        double kineticCoefficient = pathDB.getKineticCoefficient(lat1, long1, lat2, long2);
        double averageSpeed = Utils.AVERAGE_SPEED_SCOOTER;
        double windDirection = pathDB.getwindDirection(lat1, long1, lat2, long2);

        double tripTime = distance / averageSpeed;

        double power = calculatePotencyGeneratedByUser(userWeight, Utils.AVERAGE_WEIGHT_BYCICLE,
                averageSpeed, kineticCoefficient, inclination, aerodynamicDrag, windSpeed, windDirection);

        return power / tripTime;
    }

    /**
     * Method to calculate elevation between coordinates
     *
     * @param lat1 latitude point 1
     * @param long1 longitude point 1
     * @param lat2 latitude point 2
     * @param long2 longitude point 2
     * @return elevation between coordinates
     */
    private double calculateElevationBetweenCoordinates(double lat1, double long1, double lat2, double long2) {
        double elevation1 = parkDB.getElevationFromCoords(lat1, long1);
        double elevation2 = parkDB.getElevationFromCoords(lat2, long2);
        return elevation2 - elevation1;
    }

    public double getDistanceFromRent(Rent oRent) {
        double lat1 = rentDB.getInitialLat(oRent.getDescriptionRent());
        double long1 = rentDB.getInitialLong(oRent.getDescriptionRent());
        return Utils.calculateDistanceBetweenCoordinates(lat1, oRent.getLatitudeDestination(), long1, oRent.getLongitudeDestination());
    }

    public double shortestPathBetweenParks(Park p1, Park p2, LinkedList<PointOfInterest> lstParks) {

        Graph<PointOfInterest, Double> grafo = createGraphDistance();
        if (p1 != null && p2 != null) {
            double res = GraphAlgorithms.shortestPath(grafo, p1, p2, lstParks);
            Collections.reverse(lstParks);
            return res;
        }
        return -1;
    }

    public double getMostEfficientByCalories(Park p1, Park p2, LinkedList<PointOfInterest> lstParks, User user, String id) {

        Graph<PointOfInterest, Double> grafo = createGraphCalories(user, id);

        return calculateShortestPath(p1, p2, lstParks, grafo);
    }

    public double getMostEfficientByEnergy(Park p1, Park p2, LinkedList<PointOfInterest> lstParks, User user, String idScooter) {

        Graph<PointOfInterest, Double> grafo = createGraphPower(user, idScooter);

        return calculateShortestPath(p1, p2, lstParks, grafo);
    }

    private double calculateShortestPath(Park p1, Park p2, LinkedList<PointOfInterest> lstParks, Graph<PointOfInterest, Double> grafo) {
        if (p1 != null && p2 != null) {
            GraphAlgorithms.shortestPath(grafo, p1, p2, lstParks);
            Collections.reverse(lstParks);
            return calculateDistanceBetweenPoiList(lstParks);
        }
        return -1;
    }

    private double calculateDistanceBetweenPoiList(List<PointOfInterest> lstParks) {
        double dist = 0;

        for (int i = 0; i < lstParks.size() - 1; i++) {
            dist = dist + Utils.calculateDistanceBetweenCoordinates(lstParks.get(i).getLatitude(), lstParks.get(i + 1).getLatitude(), lstParks.get(i).getLongitude(), lstParks.get(i + 1).getLongitude());
        }
        return dist;
    }

    private AdjacencyMatrixGraph<PointOfInterest, Double> criarMatrizAdj(Graph<PointOfInterest, Double> grafo) {
        AdjacencyMatrixGraph<PointOfInterest, Double> matrizAdj = new AdjacencyMatrixGraph<>();
        for (PointOfInterest p : grafo.vertices()) {
            matrizAdj.insertVertex(p);
        }

        for (Edge<PointOfInterest, Double> ed : grafo.edges()) {
            matrizAdj.insertEdge(ed.getVOrig(), ed.getVDest(), ed.getWeight());
        }
        return matrizAdj;
    }

    private static void addToList(List<PointOfInterest> perm, int n, List<LinkedList<PointOfInterest>> listaPerm) {
        LinkedList<PointOfInterest> aux = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            aux.add(perm.get(i));
        }
        listaPerm.add(aux);
    }

    private static void heapPermutation(List<PointOfInterest> perm, int size, int n, List<
            LinkedList<PointOfInterest>> listaPerm) {
        if (size == 1) {
            addToList(perm, n, listaPerm);
        }
        for (int i = 0; i < size; i++) {
            heapPermutation(perm, size - 1, n, listaPerm);
            if (size % 2 == 1) {
                PointOfInterest temp = perm.get(0);
                perm.set(0, perm.get(size - 1));
                perm.set(size - 1, temp);
            } else {
                PointOfInterest temp = perm.get(i);
                perm.set(i, perm.get(size - 1));
                perm.set(size - 1, temp);
            }
        }
    }

    public double shortestRouteGoingByCertainPoints(Park parkOrigin, Park parkDest, List<PointOfInterest> poiRequired, List<PointOfInterest> shortestPath) {

        Graph<PointOfInterest, Double> grafo = createGraphDistance();

        if (parkOrigin != null && parkDest != null) {
            return shortestPathRestricted(parkOrigin, parkDest, poiRequired, shortestPath, grafo);
        }
        return -1;
    }

    public double eficientRouteGoingByCertainPointsByCalories(Park parkOrigin, Park parkDest, List<PointOfInterest> poiRequired, List<PointOfInterest> shortestPath, User username, String idScooter) {

        Graph<PointOfInterest, Double> grafo = createGraphCalories(username, idScooter);

        return calculateShortesPathGoingByCertainPois(parkOrigin, parkDest, poiRequired, shortestPath, grafo);

    }

    public double eficientRouteGoingByCertainPointsByPower(Park parkOrigin, Park parkDest, List<PointOfInterest> poiRequired, List<PointOfInterest> shortestPath, User username, String idScooter) {

        Graph<PointOfInterest, Double> grafo = createGraphPower(username, idScooter);

        return calculateShortesPathGoingByCertainPois(parkOrigin, parkDest, poiRequired, shortestPath, grafo);

    }

    private double calculateShortesPathGoingByCertainPois(Park parkOrigin, Park parkDest, List<PointOfInterest> poiRequired, List<PointOfInterest> shortestPath, Graph<PointOfInterest, Double> grafo) {
        if (parkOrigin != null && parkDest != null) {

            shortestPathRestricted(parkOrigin, parkDest, poiRequired, shortestPath, grafo);
            return calculateDistanceBetweenPoiList(shortestPath);

        }
        return -1;
    }

    private double shortestPathRestricted(Park parkOrigin, Park parkDest, List<PointOfInterest> poiRequired, List<PointOfInterest> shortestPath, Graph<PointOfInterest, Double> grafo) {
        AdjacencyMatrixGraph<PointOfInterest, Double> matrizAdjOriginal = criarMatrizAdj(grafo);
        AdjacencyMatrixGraph<PointOfInterest, Double> matrizAdj = EdgeAsDoubleGraphAlgorithms.matrizAdjFloydWarshall(matrizAdjOriginal);
        List<LinkedList<PointOfInterest>> permutacoes = new LinkedList<>();

        heapPermutation(poiRequired, poiRequired.size(), poiRequired.size(), permutacoes);
        LinkedList<PointOfInterest> permutacaoMinima = new LinkedList<>();
        double distanciaMinima = Double.MAX_VALUE;
        double distancia;
        for (LinkedList<PointOfInterest> permPossivel : permutacoes) {
            distancia = 0;
            permPossivel.addFirst(parkOrigin);
            permPossivel.addLast(parkDest);
            for (int i = 0; i < permPossivel.size() - 1; i++) {
                distancia += matrizAdj.getEdge(permPossivel.get(i), permPossivel.get(i + 1));
            }
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                permutacaoMinima = permPossivel;
            }
        }
        shortestPath.add(parkOrigin);
        for (int i = 0; i < permutacaoMinima.size() - 1; i++) {
            LinkedList<PointOfInterest> caminho = new LinkedList<>();
            EdgeAsDoubleGraphAlgorithms.shortestPath(matrizAdjOriginal, permutacaoMinima.get(i), permutacaoMinima.get(i + 1), caminho);
            caminho.removeFirst();

            shortestPath.addAll(caminho);
        }

        return distanciaMinima;
    }

    public Rent unlockVehicle(String idVehicle, String username) {

        String idPoint = parkDB.getParkFromVehicle(idVehicle);

        Date dateTimeBegin = new Date(Calendar.getInstance().getTimeInMillis());

        int idRent = rentDB.addRentToDataBase(idVehicle, idPoint, dateTimeBegin, username);

        return newRent(idRent, idVehicle, idPoint, dateTimeBegin, username);

    }

    public Rent unlockAnyEscooterAtPark(String parkIdentification, String username) {
        double maximo = 0;

        List<String> listVehicles = parkDB.getVehiclesFromPark(parkIdentification);
        Date dateTimeBegin = new Date(Calendar.getInstance().getTimeInMillis());
        if (listVehicles.size() > 0) {
            String idVehicle = listVehicles.get(0);

            for (String id : listVehicles) {
                if (maximo < vehicleDB.getCurrentBateryScooter(id)) {
                    maximo = vehicleDB.getCurrentBateryScooter(id);
                    idVehicle = id;

                }
            }

            int idRent = rentDB.addRentToDataBase(idVehicle, parkIdentification, dateTimeBegin, username);

            return newRent(idRent, idVehicle, parkIdentification, dateTimeBegin, username);
        }

        return null;

    }

    public Scooter getScooterByIdRent(int idRent) {
        return rentDB.getScooterByIdRent(idRent);

    }

    public int getRentID(String bicycleDescription, String username) {
        return rentDB.getRentByVehicle(bicycleDescription, username);
    }

    public long forHowLongAVehicleIsUnlocked(String vehicle) {
        return rentDB.forHowLongAVehicleIsUnlocked(vehicle);
    }

    public int getFreeSlotsAtParkForMyLoanedVehicle(String username, String park) {
        return rentDB.getFreeSlotsAtParkForMyLoanedVehicle(username,park);
    }

    public void setUserDB(UserDB udb) {
        this.userDB = udb;
    }

    public void setVehicleDB(VehicleDB vdb) {
        this.vehicleDB = vdb;
    }

    public void setParkDB(ParkDB pdb) {
        this.parkDB = pdb;
    }

    public void setPathDB(PathDB pathDB) {
        this.pathDB = pathDB;
    }

    public void setPoiDB(PointOfInterestDB poiDB) {
        this.poiDB = poiDB;
    }

    public void setRentDB(RentDB db) {
        this.rentDB = db;
    }

    public double pathDistanceTo(double lat1, double long1, double lat2, double long2) {
        Park p1 = parkDB.getPark(lat1, long1);
        Park p2 = parkDB.getPark(lat2, long2);
        LinkedList<PointOfInterest> list = new LinkedList<>();
        return shortestPathBetweenParks(p1, p2, list);
    }
}
