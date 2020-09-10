package lapr.project.model;

public class RegisterPark {

    public Park newPark(String descriptionPark, double latitude,double longitude,int elevation,String textDescription,int maxBicycles,int maxScooters,int inputVoltage,int inputCurrent){
        return new Park(descriptionPark,latitude,longitude,elevation,textDescription,maxBicycles,maxScooters,inputVoltage,inputCurrent);
    }



}
