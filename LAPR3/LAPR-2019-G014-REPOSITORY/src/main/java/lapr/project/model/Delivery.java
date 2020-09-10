package lapr.project.model;

import java.sql.Date;
import java.util.Objects;

public class Delivery {

    private int descriptionDelivery;
    private int descriptionRent;
    private int descriptionCost;
    private Date dateTimeEnd;
    private String descriptionPointDestination;
    private double finalCost;


    public Delivery(int descriptionDelivery, int descriptionRent, int descriptionCost, Date dateTimeEnd, String descpritionPointDestination, double finalCost) {

        this.descriptionDelivery = descriptionDelivery;
        this.descriptionRent = descriptionRent;
        this.descriptionCost = descriptionCost;
        this.dateTimeEnd = dateTimeEnd;
        this.descriptionPointDestination = descpritionPointDestination;
        this.finalCost = finalCost;

    }

    public int getDescriptionDelivery() {
        return descriptionDelivery;
    }

    public void setDescriptionDelivery(int descriptionDelivery) {
        this.descriptionDelivery = descriptionDelivery;
    }

    public int getDescriptionRent() {
        return descriptionRent;
    }

    public void setDescriptionRent(int descriptionRent) {
        this.descriptionRent = descriptionRent;
    }

    public Date getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }


    public int getDescriptionCost() {
        return descriptionCost;
    }

    public void setDescriptionCost(int descriptionCost) {
        this.descriptionCost = descriptionCost;
    }

    public String getDescriptionPointDestination() {
        return descriptionPointDestination;
    }

    public void setDescriptionPointDestination(String descriptionPointDestination) {
        this.descriptionPointDestination = descriptionPointDestination;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return descriptionCost == delivery.descriptionCost &&
                Double.compare(delivery.finalCost, finalCost) == 0 &&
                Objects.equals(descriptionDelivery, delivery.descriptionDelivery) &&
                Objects.equals(descriptionRent, delivery.descriptionRent) &&
                Objects.equals(dateTimeEnd, delivery.dateTimeEnd) &&
                Objects.equals(descriptionPointDestination, delivery.descriptionPointDestination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionDelivery, descriptionRent, descriptionCost, dateTimeEnd, descriptionPointDestination, finalCost);
    }

    @Override
    public String toString() {
        return "Delivery\n" +
                "descriptionDelivery='" + descriptionDelivery + '\'' +
                ", descriptionRent='" + descriptionRent + '\'' +
                ", descriptionCost=" + descriptionCost +
                ", dateTimeEnd='" + dateTimeEnd + '\'' +
                ", descriptionPointDestination='" + descriptionPointDestination + '\'' +
                ", finalCost=" + finalCost +
                '\n';
    }
}