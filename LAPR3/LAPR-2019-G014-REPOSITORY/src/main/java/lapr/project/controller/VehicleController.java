package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Bicycle;
import lapr.project.model.RegisterVehicle;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;

public class VehicleController {
    private VehicleDB vdb;
    private final RegisterVehicle reg;

    public VehicleController() {
        vdb = new VehicleDB();
        reg = new RegisterVehicle();
    }

    public void setVehicleDB(VehicleDB db) {
        this.vdb = db;
    }

    public Bicycle newBicycle(String descriptionVehicle, double weight, double aerodynamic, double frontalArea, int wheelSize) {
        return reg.newBicycle(descriptionVehicle, weight, aerodynamic, frontalArea, wheelSize);
    }

    public Scooter newScooter(String descriptionVehicle, double weight, String type, int maxBatery, double actualBatery, double aerodynamic, double frontalArea, int potency) {
        return reg.newScooter(descriptionVehicle, weight, aerodynamic, frontalArea, type, maxBatery, actualBatery, potency);
    }

    public boolean registerBicycle(Bicycle bicycle) {
        return vdb.addBicycleToDataBase(bicycle);
    }

    public boolean registerScooter(Scooter scooter) {
        return vdb.addScooterToDataBase(scooter);
    }

    public boolean updateScooter(Scooter scooter) {
        return vdb.updateScooter(scooter.getDescriptionVehicle(), scooter.getWeight(), scooter.getAerodynamic(), scooter.getFrontalArea(), scooter.getType(), scooter.getMaxBatery(), scooter.getActualBatery(), scooter.getPotency());
    }

    public boolean updateBicycle(Bicycle bicycle) {
        return vdb.updateBicycle(bicycle.getDescriptionVehicle(), bicycle.getWeight(), bicycle.getAerodynamic(), bicycle.getFrontalArea(), bicycle.getWheelSize());
    }

    public boolean removeBicycle(String id) {
        return vdb.removeBicycle(id);
    }

    public boolean removeScooter(String id) {
        return vdb.removeScooter(id);
    }

    public Vehicle getVehicle(String id) {
        return vdb.getVehicle(id);
    }

}
































