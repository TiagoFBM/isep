package lapr.project.model;

import java.util.Objects;

public class Scooter extends Vehicle {

    private String type;
    private int maxBatery;
    private double actualBatery;
    private int potency;

    public Scooter(String descriptionVehicle, double weight, double aerodynamic, double frontalArea, String type, int maxBatery, double actualBatery, int potency) {
        super(descriptionVehicle, weight, aerodynamic, frontalArea);
        this.type = type;
        this.maxBatery = maxBatery;
        this.actualBatery = actualBatery;
        this.potency = potency;
    }

    /**
     *
     * @param descriptionVehicle
     * @param weight
     * @param aerodynamic
     * @param frontalArea
     * @param status
     * @param type
     * @param maxBatery
     * @param actualBatery
     * @param potency
     */
    public Scooter(String descriptionVehicle, double weight, double aerodynamic, double frontalArea, int status, String type, int maxBatery, double actualBatery, int potency) {
        super(descriptionVehicle, weight, aerodynamic, frontalArea, status);
        this.type = type;
        this.maxBatery = maxBatery;
        this.actualBatery = actualBatery;
        this.potency = potency;
    }

    public int getPotency() {
        return potency;
    }

    public void setPotency(int potency) {
        this.potency = potency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxBatery() {
        return maxBatery;
    }

    public void setMaxBatery(int maxBatery) {
        this.maxBatery = maxBatery;
    }

    public double getActualBatery() {
        return actualBatery;
    }

    public void setActualBatery(int actualBatery) {
        this.actualBatery = actualBatery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Scooter scooter = (Scooter) o;
        return maxBatery == scooter.maxBatery
                && Double.compare(scooter.actualBatery, actualBatery) == 0
                && type.equals(scooter.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, maxBatery, actualBatery);
    }

    @Override
    public String toString() {
        return "Scooter\n"
                + super.toString()
                + "type='" + type + '\''
                + ", maxBatery=" + maxBatery
                + ", actualBatery=" + actualBatery;

    }
}
