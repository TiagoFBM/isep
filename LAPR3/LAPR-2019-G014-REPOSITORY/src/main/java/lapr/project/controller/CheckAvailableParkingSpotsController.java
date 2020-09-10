package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.model.Park;
import lapr.project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CheckAvailableParkingSpotsController {
    private static final double SEARCH_RANGE = 1.0D;
    private ParkDB oParkDB;

    public CheckAvailableParkingSpotsController() {
        oParkDB = new ParkDB();
    }

     public void setParkDB(ParkDB db){
        this.oParkDB = db;
    }
    
    public List<Park> getListActiveParksNearCoords(double latitude, double longitude) {
        List<Park> lstActiveParksTemp = oParkDB.getActiveParks();
        List<Park> lstActiveParks = new ArrayList<>();
        for (Park p : lstActiveParksTemp) {
            if (Utils.calculateDistanceBetweenCoordinates(p.getLatitude(), latitude, p.getLongitude(), longitude) <= SEARCH_RANGE) {
                lstActiveParks.add(p);
            }
        }
        return lstActiveParks;
    }

    public boolean checkAvailability(Park selectedPark, String vehicleType) {
        return oParkDB.checkParkAvailability(selectedPark.getDescriptionPark(), vehicleType);
    }
}
