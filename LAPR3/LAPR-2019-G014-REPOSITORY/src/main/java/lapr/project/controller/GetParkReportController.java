package lapr.project.controller;

import lapr.project.data.ParkDB;
import lapr.project.data.VehicleDB;
import lapr.project.model.Scooter;

import java.util.*;

public class GetParkReportController {

    private ParkDB parkDB;
    private VehicleDB vehicleDB;

    public GetParkReportController() {
        parkDB = new ParkDB();
        vehicleDB = new VehicleDB();
    }

    public void setParkDB(ParkDB parkDB) {
        this.parkDB = parkDB;
    }

    public void setVehicleDB(VehicleDB vehicleDB) {
        this.vehicleDB = vehicleDB;
    }

    private List<String> getVehiclesFromPark(String descriptionPark) {
        return new LinkedList<>(parkDB.getVehiclesFromPark(descriptionPark));
    }

    public long getVehiclesWithout100perBatery(String descriptionPark) {
        long result = 0;
        List<String> lstVehiclesID = getVehiclesFromPark(descriptionPark);
        for (String v : lstVehiclesID) {
            if (vehicleDB.getScooterById(v).getActualBatery() < 100) {
                result++;
            }
        }
        return result;
    }

    public List<Scooter> getListScooterFromPark(String descriptionPark) {
        List<Scooter> lstScooter = new LinkedList<>();
        List<String> lstVehiclesID = getVehiclesFromPark(descriptionPark);
        for (String v : lstVehiclesID) {
            if (vehicleDB.getVehicle(v.trim()) instanceof Scooter) {
                lstScooter.add(vehicleDB.getScooterById(v.trim()));
            }
        }
        lstScooter.sort(comparator);
        return lstScooter;
    }

    public void updateActualBatery(Scooter s){
        vehicleDB.updateActualBatery(s);
    }

    public double getChargingTimeLeft(Scooter s, int n, String descriptionPark) {
        double charge;
        if (n <= 3) {
            charge = (parkDB.getPark(descriptionPark).getInputVoltage()*parkDB.getPark(descriptionPark).getInputCurrent()*Math.pow(10d, -3d)) / 3;
        } else {
            charge = (parkDB.getPark(descriptionPark).getInputVoltage()*parkDB.getPark(descriptionPark).getInputCurrent()*Math.pow(10d, -3d)) / n;
        }
        return ((s.getMaxBatery() / charge) - ((s.getActualBatery() / charge) + vehicleDB.getScooterChargingTime(s.getDescriptionVehicle()))) * 60;
    }

    Comparator<Scooter> comparator = new Comparator<Scooter>() {
        @Override
        public int compare(Scooter aDouble, Scooter t1) {
            int result= Double.compare(aDouble.getActualBatery(),t1.getActualBatery());
            if(result==0){
                result=t1.getDescriptionVehicle().compareToIgnoreCase(aDouble.getDescriptionVehicle());
            }
            return result;
        }
    };


}