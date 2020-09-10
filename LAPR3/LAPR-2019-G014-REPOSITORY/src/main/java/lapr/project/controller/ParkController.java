package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Park;
import lapr.project.model.RegisterPark;
import lapr.project.model.Scooter;
import lapr.project.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParkController {

    private ParkDB pdb;
    private final RegisterPark rp;

    public ParkController() {

        rp = new RegisterPark();
        pdb = new ParkDB();
    }

    public void setParkDB(ParkDB db) {
        this.pdb = db;
    }

    public Park newPark(String descriptionPark, double latitude, double longitude, int elevation, String textDescription, int maxBicycles, int maxScooters, int inputVoltage, int inputCurrent) {
        return rp.newPark(descriptionPark, latitude, longitude, elevation, textDescription, maxBicycles, maxScooters, inputVoltage, inputCurrent);
    }

    public boolean addPark(Park park) {
        if (park != null) {
            return pdb.addPark(park);
        }
        return false;
    }

    public boolean removePark(String parkID) {
        return pdb.removePark(parkID);
    }

    public boolean updatePark(String descriptionPark, double latitude, double longitude, int elevation, String textDescription, int maxBicycles, int maxScooters, int inputVoltage, int inputCurrent) {
        return pdb.updatePark(descriptionPark, latitude, longitude, elevation, textDescription, maxBicycles, maxScooters, inputVoltage, inputCurrent);
    }

    public Park getPark(double latitude, double longitude) {
        return pdb.getPark(longitude, latitude);
    }

    public Park getPark(String parkDescription) {
        return pdb.getPark(parkDescription);
    }

    public List<String> getVehiclesFromPark(Park park) {
        if (park != null) {
            return pdb.getVehiclesFromPark(park.getDescriptionPark());
        }
        return new ArrayList<>();
    }

    public double getDistanceToPark(double latUser, double longUser, String description) {
        Park park = pdb.getPark(description);

        if (park != null) {
            return Utils.calculateDistanceBetweenCoordinates(latUser, park.getLatitude(), longUser, park.getLongitude());
        }
        return -1;
    }

    public List<Park> getListActiveParks() {
        return pdb.getActiveParks();
    }

    /**
     * Returns a list of the nearest parks
     *
     * @param latitude user latitude
     * @param longitude user longitude
     * @return list of nearest parks
     */
    public List<Park> getListNearestParks(double latitude, double longitude) {
        List<Park> lstNearestParks = new LinkedList<>();
        List<Park> lstActiveParks = new LinkedList<>();
        lstActiveParks = pdb.getActiveParks();
        for (Park p : lstActiveParks) {
            double dist = Utils.calculateDistanceBetweenCoordinates(latitude, p.getLatitude(), longitude, p.getLongitude());
            if (dist <= Utils.RADIUS_NEARBY_PARKS) {
                lstNearestParks.add(p);
            }
        }
        return lstNearestParks;
    }

    /**
     * Returns a list of the nearest parks within a given radius
     *
     * @param latitude user latitude
     * @param longitude user longitude
     * @param radius radius where to look for parks
     * @return list of nearest parks
     */
    public List<Park> getListNearestParks(double latitude, double longitude, int radius) {
        List<Park> lstNearestParks = new LinkedList<>();
        List<Park> lstActiveParks = new LinkedList<>();
        lstActiveParks = pdb.getActiveParks();
        for (Park p : lstActiveParks) {
            double dist = Utils.calculateDistanceBetweenCoordinates(latitude, p.getLatitude(), longitude, p.getLongitude());
            if (dist <= radius * 1000) {
                lstNearestParks.add(p);
            }
        }
        return lstNearestParks;
    }

    public boolean assignVehiclePark(String idPark, String idVehicle) {
        return pdb.assignVehiclePark(idPark, idVehicle);
    }

    public int getFreeBicycleSlotsAtPark(String parkID) {
        return pdb.getFreeBicycleSlotsAtPark(parkID);
    }

    public int getFreeScooterSlotsAtPark(String parkID) {
        return pdb.getFreeBicycleSlotsAtPark(parkID);
    }

    /**
     * Returns a list of Bicycles
     *
     * @param parkID park to search
     * @return list of bicylces
     */
    public List<Bicycle> getBicyclesFromPark(String parkID) {
        return pdb.getBicyclesFromPark(parkID);
    }

    /**
     * Returns a list of Scooters
     *
     * @param parkID park to search
     * @return list of Scooter
     */
    public List<Scooter> getScootersFromPark(String parkID) {
        return pdb.getScootersFromPark(parkID);
    }
}
