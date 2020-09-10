package lapr.project.model;

import java.sql.Date;
import java.util.Objects;

public class Invoice {
    private String username;
    private String descriptionVehicle;
    private Date dateTimeBegin;
    private Date dateTimeFinal;
    private String source;
    private String destiny;
    private double totalCost;

    public Invoice (String username, String descriptionVehicle, Date dateTimeBegin, Date dateTimeFinal, String source, String destiny, double totalCost){
        this.username = username;
        this.descriptionVehicle = descriptionVehicle;
        this.dateTimeBegin = dateTimeBegin;
        this.dateTimeFinal = dateTimeFinal;
        this.source = source;
        this.destiny = destiny;
        this.totalCost = totalCost;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescriptionVehicle() {
        return descriptionVehicle;
    }

    public void setDescriptionVehicle(String descriptionVehicle) {
        this.descriptionVehicle = descriptionVehicle;
    }

    public Date getDateTimeBegin() {
        return dateTimeBegin;
    }

    public void setDateTimeBegin(Date dateTimeBegin) {
        this.dateTimeBegin = dateTimeBegin;
    }

    public Date getDateTimeFinal() {
        return dateTimeFinal;
    }

    public void setDateTimeFinal(Date dateTimeFinal) {
        this.dateTimeFinal = dateTimeFinal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Double.compare(invoice.totalCost, totalCost) == 0 &&
                Objects.equals(username, invoice.username) &&
                Objects.equals(descriptionVehicle, invoice.descriptionVehicle) &&
                Objects.equals(dateTimeBegin, invoice.dateTimeBegin) &&
                Objects.equals(dateTimeFinal, invoice.dateTimeFinal) &&
                Objects.equals(source, invoice.source) &&
                Objects.equals(destiny, invoice.destiny);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, descriptionVehicle, dateTimeBegin, dateTimeFinal, source, destiny, totalCost);
    }

    @Override
    public String toString() {
        return  "descriptionVehicle='" + descriptionVehicle + '\'' +
                ", dateTimeBegin='" + dateTimeBegin + '\'' +
                ", dateTimeFinal='" + dateTimeFinal  + '\''+
                ", source='" + source + '\'' +
                ", destiny='" + destiny + '\'' +
                ", totalCost=" + totalCost +
                '\n';
    }
}
