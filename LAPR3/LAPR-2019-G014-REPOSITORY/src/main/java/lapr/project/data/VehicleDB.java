package lapr.project.data;

import lapr.project.model.*;
import lapr.project.utils.Utils;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDB extends DataHandler {

    public VehicleDB() {
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setDataToConnection(CallableStatement callStmt, Vehicle vehicle) {
        try {
            callStmt.setString(1, vehicle.getDescriptionVehicle());
            callStmt.setDouble(3, vehicle.getWeight());
            callStmt.setDouble(4, vehicle.getAerodynamic());
            callStmt.setDouble(5, vehicle.getFrontalArea());
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean addBicycleToDataBase(Bicycle vehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();

            try {
                callStmt = getConnection().prepareCall("{ call addBicycle(?,?,?,?,?,?) }");
                setDataToConnection(callStmt, vehicle);
                callStmt.setInt(6, vehicle.getWheelSize());
                callStmt.setInt(2, Utils.STATUS_ACTIVE);

                callStmt.execute();

                closeAll();
                return true;
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean addScooterToDataBase(Scooter vehicle) {
        boolean result;
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ call addScooter(?,?,?,?,?,?,?,?,?) }");
                setDataToConnection(callStmt, vehicle);
                callStmt.setString(6, vehicle.getType());
                callStmt.setInt(7, vehicle.getMaxBatery());
                callStmt.setDouble(8, vehicle.getActualBatery());
                callStmt.setInt(9, vehicle.getPotency());
                callStmt.setInt(2, Utils.STATUS_ACTIVE);

                callStmt.execute();

                result = true;
            } catch (SQLException e) {
                Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                result = false;
            }
        } finally {
            closeAll();
        }
        return result;
    }

    public boolean removeBicycle(String idVehicle) {
        CallableStatement callStmt = null;
        if (getVehicle(idVehicle) != null) {
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{ call removeBicycle(?) }");

                    callStmt.setString(1, idVehicle);

                    callStmt.execute();
                    closeAll();
                    return true;

                } catch (SQLException e) {
                    Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                    return false;
                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } else {
            return false;
        }

    }

    public Vehicle getVehicle(String idVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getVehicle(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, idVehicle);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet != null) {
                    if (rSet.next()) {
                        return new Vehicle(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4));
                    }
                }
                closeAll();

            } finally {
                if (callStmt != null) {
                    callStmt.close();
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean removeScooter(String idVehicle) {
        CallableStatement callStmt = null;
        if (getVehicle(idVehicle) != null) {
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{ call removeScooter(?) }");

                    callStmt.setString(1, idVehicle);

                    callStmt.execute();
                    closeAll();
                    return true;

                } catch (SQLException e) {
                    Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                    return false;
                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } else {
            return false;
        }

    }

    public boolean updateBicycle(String idVehicle, double weight, double aerodynamic, double frontalArea, int wheelSize) {
        RegisterVehicle reg = new RegisterVehicle();

        CallableStatement callStmt = null;

        if (getVehicle(idVehicle) != null) {
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{call updateBicycle(?,?,?,?,?,?)}");

                    Bicycle b = reg.newBicycle(idVehicle, weight, aerodynamic, frontalArea, wheelSize);

                    setDataToConnection(callStmt, b);

                    callStmt.setInt(6, wheelSize);
                    callStmt.setInt(2, Utils.STATUS_ACTIVE);

                    callStmt.execute();
                    closeAll();
                    return true;
                } finally {
                    if (callStmt != null) {
                        callStmt.close();
                    }
                }
            } catch (SQLException e) {
                Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
        }
        return false;
    }

    public boolean updateScooter(String idVehicle, double weight, double aerodynamic, double frontal_area, String type, int maxBatery, double actualBatery, int potency) {
        RegisterVehicle reg = new RegisterVehicle();

        CallableStatement callStmt = null;

        if (getVehicle(idVehicle) != null) {
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{call updateScooter(?,?,?,?,?,?,?,?,?)}");

                    Scooter s = reg.newScooter(idVehicle, weight, aerodynamic, frontal_area, type, maxBatery, actualBatery, potency);

                    setDataToConnection(callStmt, s);

                    callStmt.setString(6, type);
                    callStmt.setInt(7, maxBatery);
                    callStmt.setDouble(8, actualBatery);
                    callStmt.setInt(9, potency);
                    callStmt.setInt(2, Utils.STATUS_ACTIVE);

                    callStmt.execute();
                } catch (SQLException e) {
                    Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                    return false;
                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        }
        return true;
    }

    public boolean updateActualBatery(Scooter scooter) {
        CallableStatement callStmt = null;
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{call updateActualBatery(?)}");
                    callStmt.setString(1, scooter.getDescriptionVehicle());
                    callStmt.execute();
                } catch (SQLException e) {
                    Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                    return false;
                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        return true ;
    }

    /**
     * Get the value of Weight
     *
     * @param descriptionVehicle description of vehicle to get Weight
     * @return value of Weight
     */
    public double getVehicleWeight(String descriptionVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getBicycleWeight(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, descriptionVehicle);
                callStmt.execute();
                return callStmt.getDouble(1);
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     * Get the value of maxBatery.
     *
     * @param descriptionVehicle description of vehicle to get batery
     * @return value of maxBatery
     */
    public double getScooterMaxBatery(String descriptionVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterMaxBatery(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, descriptionVehicle);
                callStmt.execute();
                return callStmt.getDouble(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

   /**
     * Get the scooter by description.
     *
     * @param descriptionVehicle description of vehicle to get batery
     * @return scooter
     */
    public Scooter getScooterById(String descriptionVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterById(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, descriptionVehicle);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet != null) {
                    closeAll();
                    return new Scooter(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3),
                            rSet.getDouble(4), rSet.getInt(5), rSet.getString(6), rSet.getInt(7), rSet.getDouble(8), rSet.getInt(9));
                }

            } finally {
                if (callStmt != null) {
                    callStmt.close();
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Get the value of power of scooter.
     *
     * @param descriptionVehicle description of vehicle to get power
     * @return value of power
     */
    public double getScooterPower(String descriptionVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterPower(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, descriptionVehicle);
                callStmt.execute();
                return callStmt.getDouble(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    /**
     * Get the value of power of scooter.
     *
     * @param descriptionVehicle description of vehicle to get power
     * @return value of power
     */
    public double getScooterActualBatery(String descriptionVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterActualBatery(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, descriptionVehicle);
                callStmt.execute();
                return callStmt.getDouble(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public List<Vehicle> getActiveVehicles() {
        CallableStatement callStmt = null;
        List<Vehicle> listVehicle = new LinkedList<>();
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getActiveVehicles() }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.execute();
                ResultSet rSet = callStmt.getResultSet();

                while (rSet.next()) {
                    Vehicle v = new Vehicle(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4));
                    listVehicle.add(v);
                }

                closeAll();
                return listVehicle;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return new LinkedList<>();
        }
    }

    public String getHistoryOfVehicle(String idVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getHistoryOfVehicle(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, idVehicle);
                callStmt.execute();

                closeAll();
                return getHistoryStringFromResultSet(callStmt.getResultSet());
            } catch (SQLException e) {
                Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
                return "";
            }
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
    }

    public String getHistoryStringFromResultSet(ResultSet rSet) {
        String result = "";
        int i = 0;

        try {
            while (rSet.next()) {
                i++;

                result = String.format(" ----- Rent %d ----- \n"
                        + "Vehicle ID: %s\n"
                        + "Park ID: %s\n"
                        + "Unlock Time: %s\n"
                        + "Lock Time: %s\n\n",
                        i, rSet.getString(1), rSet.getString(2), rSet.getString(3), rSet.getString(4));
            }
            return result;
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return result;
        }
    }

    public List<Scooter> getActiveScooters() {
        CallableStatement callStmt = null;
        List<Scooter> listScooter = new LinkedList<>();
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getActiveScooters() }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.execute();
                ResultSet rSet = callStmt.getResultSet();

                while (rSet.next()) {
                    Scooter scooter = new Scooter(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3),
                            rSet.getDouble(4), rSet.getString(5), rSet.getInt(6), rSet.getDouble(7), rSet.getInt(8));

                    listScooter.add(scooter);
                }
                closeAll();
                return listScooter;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }

    /**
     * Method to get a list of unlocked vehicles
     *
     * @param inicialDate inicial date to get the report
     * @param finalDate final date to get the report
     * @return unlocked vehicle list
     */
    public List<Vehicle> getReportUnlockedVehicles(Date inicialDate, Date finalDate) {
        CallableStatement callStmt = null;
        List<Vehicle> listVehicle = new LinkedList<>();
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getUnlockedVehicles(?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setDate(2, inicialDate);
                callStmt.setDate(3, finalDate);
                callStmt.execute();
                ResultSet rSet = callStmt.getResultSet();

                while (rSet.next()) {
                    Vehicle vehicle = new Vehicle(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4));
                    listVehicle.add(vehicle);
                }
                closeAll();
                return listVehicle;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }

    public Bicycle getBicycleByID(String bicycleID) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getBicycleById(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, bicycleID);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet != null) {
                    closeAll();
                    return new Bicycle(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3),
                            rSet.getDouble(4), rSet.getInt(5), rSet.getInt(6));
                }

            } finally {
                if (callStmt != null) {
                    callStmt.close();
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public double getScooterChargingTime(String idScooter) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterChargingTime(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, idScooter);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet != null) {
                    closeAll();
                    return rSet.getDouble(1);
                }

            } finally {
                if (callStmt != null) {
                    callStmt.close();
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public double getCurrentBateryScooter(String idScooter){
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getCurrentBateryScooter(?) }");
                callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
                callStmt.setString(2, idScooter);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet != null) {
                    closeAll();
                    return rSet.getDouble(1);
                }

            } finally {
                if (callStmt != null) {
                    callStmt.close();
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}
