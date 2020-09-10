package lapr.project.controller;

import lapr.project.data.PointOfInterestDB;
import lapr.project.model.PointOfInterest;
import lapr.project.model.RegisterPointOfInterest;

public class AddTouristicPointOfInterestController {

    private final RegisterPointOfInterest register;
    private PointOfInterestDB db;

    public void setPoiDb(PointOfInterestDB db) {
        this.db = db;
    }

    public AddTouristicPointOfInterestController() {
        this.register = new RegisterPointOfInterest();
        this.db = new PointOfInterestDB();
    }

    public PointOfInterest createNewPOI(String poiDescription, double latitude, double longitude, int elevation) {
        int cod = db.getMostRecentPointOfInterestCode();
        if (cod != -1) {
            return register.newPointOfInterest("POI_" + poiDescription, poiDescription, latitude, longitude, elevation);
        }
        return null;
    }

    public boolean registerPointOfInterest(PointOfInterest poi) {
        return db.registerPointOfInterest(poi);
    }

    public PointOfInterest getPointOfInterest(double latitude, double longitude){
        return db.getPointOfInterest(latitude,longitude);
    }
}
