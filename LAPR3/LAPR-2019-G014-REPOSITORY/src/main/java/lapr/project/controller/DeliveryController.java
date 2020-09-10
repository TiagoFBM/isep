package lapr.project.controller;

import lapr.project.data.DeliveryDB;
import lapr.project.data.PointOfInterestDB;
import lapr.project.data.RentDB;
import lapr.project.model.Delivery;
import lapr.project.model.PointOfInterest;
import lapr.project.model.RegisterDelivery;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class DeliveryController {

    private DeliveryDB ddb;
    private PointOfInterestDB pdb;
    private RentDB rdb;
    private final RegisterDelivery reg;

    public DeliveryController() {
        ddb = new DeliveryDB();
        reg = new RegisterDelivery();
        pdb = new PointOfInterestDB();
        rdb = new RentDB();
    }

    /**
     * Method to set DeliveryDB
     *
     * @param db DeliveryDB to set
     */
    public void setDeliveryDB(DeliveryDB db) {
        this.ddb = db;
    }
    
    /**
     * Method to set PointOfInterestDB
     *
     * @param db PointOfInterestDB to set
     */
    public void setPointOfInterestDB(PointOfInterestDB db) {
        this.pdb = db;
    }

    public void setRentDB(RentDB rdb) { this.rdb = rdb;}
   
    /**
     * Create a new Delivery
     *
     * @param descriptionDelivery description of delivery
     * @param descriptionRent description of rent
     * @param dateTimeEnd date and time end of rent
     * @return new delivery
     */
    public Delivery newDelivery(int descriptionDelivery, int descriptionRent, int descriptionCost, Date dateTimeEnd, String descriptionPointDestination, double finalCost) {
        return reg.newDelivery(descriptionDelivery, descriptionRent, descriptionCost, dateTimeEnd, descriptionPointDestination, finalCost);
    }

    /**
     * Register the Delivery
     *
     * @param delivery delivery to register
     * @return true if it was well registered and false if it wasn't
     */

    public long registerDelivery(Delivery delivery) {
        if (delivery!=null){
            ddb.addDeliveryToDataBase(delivery);
            return ddb.calculateTravelCost(delivery.getDescriptionDelivery());
        }
        return -1;
    }

    public Delivery lockVehicle(String bicycleDescription, double parkLatitude, double parkLongitude, String username){

        Date initial = new Date(Calendar.getInstance().getTimeInMillis());
        PointOfInterest p = pdb.getPointOfInterest(parkLatitude, parkLongitude);
        if (p != null) {
            String descriptionPark = p.getPoiDescription();
            int descriptionRent = rdb.getRentByVehicle(bicycleDescription, username);
            int descriptionCost = ddb.getCostsID(descriptionRent);
            Delivery delivery = newDelivery(1, descriptionRent, descriptionCost, initial, descriptionPark, 0);
            registerDelivery(delivery);
            return delivery;
        }
        return null;
    }

    public Delivery lockVehicle(String bicycleDescription, String parkDescription, String username){
        Date initial = new Date(Calendar.getInstance().getTimeInMillis());
        PointOfInterest p = pdb.getPointOfInterest(parkDescription);
        if (p != null) {
            int descriptionRent = rdb.getRentByVehicle(bicycleDescription, username);
            int descriptionCost = ddb.getCostsID(descriptionRent);
            Delivery delivery = newDelivery(1, descriptionRent, descriptionCost, initial, parkDescription, 0);
            registerDelivery(delivery);
            return delivery;
        }
        return null;
    }

    public List<String> getAllDeliveries(String username) {
        return ddb.getAllDeliveries(username);
    }
}
