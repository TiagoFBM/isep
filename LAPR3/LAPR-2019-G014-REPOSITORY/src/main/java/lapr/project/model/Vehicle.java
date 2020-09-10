package lapr.project.model;

import lapr.project.utils.Utils;

import java.util.Objects;

public class Vehicle {

    private String descriptionVehicle;
    private double weight;
    private double aerodynamic;
    private double frontalArea;
    private int status;

    public Vehicle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea) {
        this.descriptionVehicle = descriptionVehicle;
        this.weight = weight;
        this.aerodynamic = aerodynamic;
        this.frontalArea = frontalArea;
        this.status = Utils.STATUS_ACTIVE;

    }

    /**
     * @param descriptionVehicle
     * @param weight
     * @param aerodynamic
     * @param frontalArea
     * @param status
     */
    public Vehicle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea, int status) {
        this.descriptionVehicle = descriptionVehicle;
        this.weight = weight;
        this.aerodynamic = aerodynamic;
        this.frontalArea = frontalArea;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescriptionVehicle() {
        return descriptionVehicle;
    }

    public void setDescriptionVehicle(String descriptionVehicle) {
        this.descriptionVehicle = descriptionVehicle;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAerodynamic() {
        return aerodynamic;
    }

    public void setAerodynamic(double aerodynamic) {
        this.aerodynamic = aerodynamic;
    }

    public double getFrontalArea() {
        return frontalArea;
    }

    public void setFrontalArea(double frontalArea) {
        this.frontalArea = frontalArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.weight, weight) == 0
                && Double.compare(vehicle.aerodynamic, aerodynamic) == 0
                && Double.compare(vehicle.frontalArea, frontalArea) == 0
                && status == vehicle.status
                && descriptionVehicle.equals(vehicle.descriptionVehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionVehicle, weight, aerodynamic, frontalArea, status);
    }

    @Override
    public String toString() {
        return "Vehicle\n"
                + "descriptionVehicle='" + descriptionVehicle + '\''
                + ", weight=" + weight
                + ", aerodynamic=" + aerodynamic
                + ", frontalArea=" + frontalArea
                + '\n';
    }
}
