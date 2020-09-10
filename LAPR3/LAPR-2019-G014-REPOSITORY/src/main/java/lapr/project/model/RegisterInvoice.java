package lapr.project.model;

import java.sql.Date;

public class RegisterInvoice {

    public Invoice newInvoice(String username, String descriptionVehicle, Date dateTimeBegin, Date dateTimeFinal, String source, String destiny, double totalCost) {
        return new Invoice(username, descriptionVehicle, dateTimeBegin, dateTimeFinal, source, destiny, totalCost);
    }

}
