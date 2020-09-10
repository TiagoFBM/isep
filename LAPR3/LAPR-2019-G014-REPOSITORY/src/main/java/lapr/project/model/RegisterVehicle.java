package lapr.project.model;

public class RegisterVehicle {

    public Bicycle newBicycle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea,int wheelSize){
        return new Bicycle(descriptionVehicle,weight,aerodynamic,frontalArea,wheelSize);
    }

    public Scooter newScooter(String descriptionVehicle, double weight, double aerodynamic, double frontalArea, String type, int maxBatery, double actualBatery, int potency){
        return new Scooter(descriptionVehicle,weight,aerodynamic,frontalArea,type,maxBatery,actualBatery, potency);
    }

}
