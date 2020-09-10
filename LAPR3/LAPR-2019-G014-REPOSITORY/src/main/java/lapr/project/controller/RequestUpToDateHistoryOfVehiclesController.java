package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class RequestUpToDateHistoryOfVehiclesController {

    private VehicleDB vehicleDB;

    public RequestUpToDateHistoryOfVehiclesController(){
        vehicleDB = new VehicleDB();
    }

    public List<Vehicle> getActiveVehicles(){
        return new LinkedList<>(vehicleDB.getActiveVehicles());
    }

    public String getHistoryOfVehicle(String idVehicle){
        return vehicleDB.getHistoryOfVehicle(idVehicle);
    }

    public void setVehicleDB(VehicleDB db){
        this.vehicleDB = db;
    }

}