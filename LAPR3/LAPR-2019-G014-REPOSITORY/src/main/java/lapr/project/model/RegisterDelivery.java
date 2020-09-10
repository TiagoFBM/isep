package lapr.project.model;

import java.sql.Date;

public class RegisterDelivery {

    public Delivery newDelivery (int descriptionDelivery, int descriptionRent, int descriptionCost, Date dateTimeEnd, String descriptionPointDestination, double finalCost){
        return new Delivery(descriptionDelivery, descriptionRent, descriptionCost, dateTimeEnd, descriptionPointDestination, finalCost);
    }
}
