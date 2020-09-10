package lapr.project.model;

import java.util.Objects;

public class PointOfInterest {

    private String poiDescription;
    private String poiTextDescription;
    private double latitude;
    private double longitude;
    private int elevation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointOfInterest that = (PointOfInterest) o;
        return poiDescription == that.poiDescription &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                elevation == that.elevation &&
                poiTextDescription.equals(that.poiTextDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poiDescription, latitude, longitude, elevation);
    }

    public PointOfInterest(String poiDescription, String poiTextDescription, double latitude, double longitude, int elevation) {
        this.poiDescription = poiDescription;
        this.poiTextDescription = poiTextDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public String getPoiDescription() {
        return poiDescription;
    }

    public void setPoiDescription(String poiDescription) {
        this.poiDescription = poiDescription;
    }

    public String getPoiTextDescription() {
        return poiTextDescription;
    }

    public void setPoiTextDescription(String poiTextDescription) {
        this.poiTextDescription = poiTextDescription;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    @Override
    public String toString() {
        return "Description = '" + poiTextDescription + "\n" +
                "Latitude = " + latitude + "\n" +
                "Longitude = " + longitude + "\n" +
                "Elevation=" + elevation + "\n";
    }
}
