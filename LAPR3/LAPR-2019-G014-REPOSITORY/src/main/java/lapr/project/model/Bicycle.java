package lapr.project.model;

import java.util.Objects;

public class Bicycle extends Vehicle{

    private int wheelSize;

    public Bicycle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea,int wheelSize){
        super(descriptionVehicle, weight, aerodynamic, frontalArea);
        this.wheelSize = wheelSize;
    }

    public Bicycle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea,int status, int wheelSize){
        super(descriptionVehicle, weight, aerodynamic, frontalArea, status);
        this.wheelSize = wheelSize;
    }

    public int getWheelSize(){
        return wheelSize;
    }

    public void setWheelSize(int wheelSize){
        this.wheelSize = wheelSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bicycle bicycle = (Bicycle) o;
        return wheelSize == bicycle.wheelSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wheelSize);
    }

    @Override
    public String toString() {
        return "Bicycle\n" +
                super.toString() +
                "wheelSize=" + wheelSize +
                '\n';
    }
}
