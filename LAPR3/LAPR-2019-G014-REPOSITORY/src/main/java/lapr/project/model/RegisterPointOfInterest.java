package lapr.project.model;

public class RegisterPointOfInterest {

    public PointOfInterest newPointOfInterest(String poiDescription, String poiDescriptionText, double latitude, double longitude, int elevation){
        return new PointOfInterest(poiDescription,poiDescriptionText,latitude,longitude,elevation);
    }
}
