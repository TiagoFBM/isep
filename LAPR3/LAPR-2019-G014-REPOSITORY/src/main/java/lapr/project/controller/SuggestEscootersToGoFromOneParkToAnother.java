package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.data.VehicleDB;
import lapr.project.model.Park;
import lapr.project.model.Scooter;

import java.util.LinkedList;
import java.util.List;

public class SuggestEscootersToGoFromOneParkToAnother {

    private ParkDB parkDB;
    private VehicleDB vehicleDB;
    private RentVehicleController rvc;

    public SuggestEscootersToGoFromOneParkToAnother(){
        parkDB=new ParkDB();
        vehicleDB=new VehicleDB();
        rvc=new RentVehicleController();
    }

    public void setParkDB(ParkDB parkDB) {
        this.parkDB = parkDB;
    }

    public void setVehicleDB(VehicleDB vehicleDB) {
        this.vehicleDB = vehicleDB;
    }

    public List<String> getVehiclesFromPark(String descriptionPark) {
        return new LinkedList<>(parkDB.getVehiclesFromPark(descriptionPark));
    }

    public List<Scooter> getListScooterFromPark(String descriptionPark) {
        List<Scooter> lstScooter = new LinkedList<>();
        List<String> lstVehiclesID = getVehiclesFromPark(descriptionPark);
        for (String v : lstVehiclesID) {
            if (vehicleDB.getVehicle(v.trim()) instanceof Scooter) {
                lstScooter.add(vehicleDB.getScooterById(v.trim()));
            }
        }
        return lstScooter;
    }

    public Park getPark(String descriptionPark){
        return parkDB.getPark(descriptionPark);
    }

    public List<Scooter> suggestScooter (Park park, String username, double destinationParkLatitudeInDegrees, double destinationParkLongitudeInDegrees){
        List<Scooter> suggestScooters = new LinkedList<>();
        List<Scooter> lstScooter = getListScooterFromPark(park.getDescriptionPark());
        double energyToTravel = rvc.calculateEnergyTravel(park.getLatitude(),park.getLongitude(), destinationParkLatitudeInDegrees, destinationParkLongitudeInDegrees,username);
        for(Scooter s: lstScooter){
            double actualBateryInJoules=getActualBatery(s);
            if(actualBateryInJoules>(energyToTravel+energyToTravel*0.10)){
                suggestScooters.add(s);
            }
        }
        return suggestScooters;
    }

    private double getActualBatery(Scooter s){
        return s.getActualBatery()*s.getMaxBatery();
    }

}
