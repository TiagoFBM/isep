package lapr.project.controller;

import lapr.project.data.VehicleDB;
import lapr.project.model.Scooter;
import lapr.project.utils.Utils;
import java.util.LinkedList;
import java.util.List;

public class GetReportAbilityVehiclesFlatTripGreaterXController {

    private VehicleDB vehicleDB;

    public GetReportAbilityVehiclesFlatTripGreaterXController() {
        vehicleDB = new VehicleDB();
    }

    public void setDB(VehicleDB vDB) {
        vehicleDB = vDB;
    }

    private List<Scooter> getActiveScooters() {
        return new LinkedList<>(vehicleDB.getActiveScooters());
    }

    private double calculateDistancePossible(Scooter s) {
        double timeOfCharge = s.getActualBatery() / s.getPotency();
        int averagevelocity;

        if (s.getType().equalsIgnoreCase("city")) {
            averagevelocity = Utils.AVERAGE_SPEED_SCOOTER_CITY; //25
        } else {
            averagevelocity = Utils.AVERAGE_SPEED_SCOOTER_OFFROAD; //40
        }

        return timeOfCharge * averagevelocity * Utils.EFFICIENCY_PERCENTAGE;
    }

    public List<Scooter> getListScootersCantMakeXkm(int distance) {
        LinkedList<Scooter> lstScooters = new LinkedList<>();
        for (Scooter s : getActiveScooters()) {
            if (calculateDistancePossible(s) <= distance) {
                lstScooters.add(s);
            }
        }
        return lstScooters;
    }
}
