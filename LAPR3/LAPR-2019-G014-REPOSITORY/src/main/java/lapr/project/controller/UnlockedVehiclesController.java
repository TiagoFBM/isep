package lapr.project.controller;

import java.sql.Date;
import java.util.List;
import lapr.project.data.VehicleDB;
import lapr.project.model.Vehicle;

/**
 * Class to get unlocked vehicles.
 *
 * @author ines-
 */
public class UnlockedVehiclesController  {

    private VehicleDB vehicleDB;

    public UnlockedVehiclesController () {
        vehicleDB = new VehicleDB();
    }

    public void setVehicleDB(VehicleDB db) {
        this.vehicleDB = db;
    }

    /**
     * Method to get a list of unlocked vehicles.
     *
     * @param inicialDate inicial date to get the report
     * @param finalDate final date to get the report
     * @return unlocked vehicle list
     */
    public List<Vehicle> getReportUnclockedVehicles(Date inicialDate, Date finalDate) {
        return vehicleDB.getReportUnlockedVehicles(inicialDate, finalDate);
    }

}