package lapr.project.model;

import java.sql.Date;

public class Rent {

    private int descriptionRent;
    private String descriptionVehicle;
    private String descriptionPoint;
    private Date dateTimeBegin;
    private Date localDateTime;
    private String username;
    private double latitudeDestination;
    private double longitudeDestination;

    public Rent(int descriptionRent, String descriptionVehicle, String descriptionPoint, Date dateTimeBegin, Date dateHourLocal, String username, double latitudeDestination, double longitudeDestination) {
        this.descriptionRent = descriptionRent;
        this.descriptionVehicle = descriptionVehicle;
        this.descriptionPoint = descriptionPoint;
        this.dateTimeBegin = dateTimeBegin;
        this.localDateTime = dateHourLocal;
        this.username = username;
        this.latitudeDestination = latitudeDestination;
        this.longitudeDestination = longitudeDestination;
    }

    public Rent(int descriptionRent, String descriptionVehicle, String descriptionPoint, Date dateTimeBegin, String username) {
        this.descriptionRent = descriptionRent;
        this.descriptionVehicle = descriptionVehicle;
        this.descriptionPoint = descriptionPoint;
        this.dateTimeBegin = dateTimeBegin;
        this.username = username;
    }

    public int getDescriptionRent() {
        return descriptionRent;
    }

    public void setDescriptionRent(int descriptionRent) {
        this.descriptionRent = descriptionRent;
    }

    public String getDescriptionVehicle() {
        return descriptionVehicle;
    }

    public void setDescriptionVehicle(String descriptionVehicle) {
        this.descriptionVehicle = descriptionVehicle;
    }

    public String getDescriptionPoint() {
        return descriptionPoint;
    }

    public void setDescriptionPoint(String descriptionPoint) {
        this.descriptionPoint = descriptionPoint;
    }

    public Date getDateTimeBegin() {
        return dateTimeBegin;
    }

    public void setDateTimeBegin(Date dateTimeBegin) {
        this.dateTimeBegin = dateTimeBegin;
    }

    public Date getDateHourLocal() {
        return localDateTime;
    }

    public void setDateHourLocal(Date dateHourLocal) {
        this.localDateTime = dateHourLocal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getLatitudeDestination() {
        return latitudeDestination;
    }

    public void setLatitudeDestination(double latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public double getLongitudeDestination() {
        return longitudeDestination;
    }

    public void setLongitudeDestination(double longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    @Override
    public String toString() {
        return "Rent: " + this.descriptionRent;
    }
}
