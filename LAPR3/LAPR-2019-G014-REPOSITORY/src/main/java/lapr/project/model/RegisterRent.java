package lapr.project.model;

import java.sql.Date;

public class RegisterRent {

    public Rent newRent (int descriptionRent, String descriptionVehicle, String descriptionPoint, Date dateTimeBegin, Date dateHourLocal, String username, double latitude, double longitude){
        return new Rent(descriptionRent,descriptionVehicle,descriptionPoint,dateTimeBegin,dateHourLocal,username,latitude,longitude);
    }

    public Rent newRent (int descriptionRent,String descriptionVehicle,String descriptionPoint,Date dateTimeBegin, String username){
        return new Rent(descriptionRent,descriptionVehicle,descriptionPoint,dateTimeBegin,username);
    }

}
