package lapr.project.data;

import lapr.project.model.Bicycle;
import lapr.project.model.Park;
import lapr.project.model.RegisterPark;
import lapr.project.model.Scooter;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParkDB extends DataHandler {

    private final RegisterPark rp;

    public ParkDB() {
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        rp = new RegisterPark();
    }

    private void setDataToConnection(CallableStatement callStmt, Park park) {
        try {
            callStmt.setString(1, park.getDescriptionPark());
            callStmt.setString(2, park.getTextDescription());
            callStmt.setDouble(3, park.getLatitude());
            callStmt.setDouble(4, park.getLongitude());
            callStmt.setInt(5, park.getElevation());
            callStmt.setInt(6, park.getMaxBicycles());
            callStmt.setInt(7, park.getMaxScooters());
            callStmt.setInt(8, park.getInputVoltage());
            callStmt.setInt(9, park.getInputCurrent());
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Park getPark(String descriptionPark) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, descriptionPark);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                Park park = null;
                if (rSet.next()) {
                    String parkID = rSet.getString(1);
                    double parkLatitude = rSet.getDouble(2);
                    double parkLongitude = rSet.getDouble(3);
                    int parkElevation = rSet.getInt(4);
                    String parkDesc = rSet.getString(5);
                    int parkMaxBycicles = rSet.getInt(6);
                    int parkMaxScooters = rSet.getInt(7);
                    int parkInputVoltage = rSet.getInt(8);
                    int parkInputCurrent = rSet.getInt(9);
                    park = rp.newPark(parkID, parkLatitude, parkLongitude, parkElevation, parkDesc, parkMaxBycicles, parkMaxScooters, parkInputVoltage, parkInputCurrent);
                }

                closeAll();
                return park;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Park getPark(double longitude, double latitude) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getParkCoord(?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setDouble(2, longitude);
                callStmt.setDouble(3, latitude);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                Park park = null;
                if (rSet.next()) {
                    String parkID = rSet.getString(1);
                    double parkLatitude = rSet.getDouble(2);
                    double parkLongitude = rSet.getDouble(3);
                    int parkElevation = rSet.getInt(4);
                    String parkDesc = rSet.getString(5);
                    int parkMaxBycicles = rSet.getInt(6);
                    int parkMaxScooters = rSet.getInt(7);
                    int parkInputVoltage = rSet.getInt(8);
                    int parkInputCurrent = rSet.getInt(9);
                    park = rp.newPark(parkID, parkLatitude, parkLongitude, parkElevation, parkDesc, parkMaxBycicles, parkMaxScooters, parkInputVoltage, parkInputCurrent);
                }
                closeAll();
                return park;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public boolean addPark(Park park) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ call addPark(?,?,?,?,?,?,?,?,?) }");
                setDataToConnection(callStmt, park);
                callStmt.execute();
                closeAll();
                return true;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean removePark(String descriptionPark) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                Park park = getPark(descriptionPark);
                if (park != null) {
                    callStmt = getConnection().prepareCall("{ call removePark(?) }");
                    callStmt.setString(1, descriptionPark);
                    callStmt.execute();
                    closeAll();
                    return true;
                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updatePark(String descriptionPark, double latitude, double longitude, int elevation, String textDescription, int maxBicycles, int maxScooters, int inputVoltage, int inputCurrent) {
        RegisterPark reg = new RegisterPark();
        CallableStatement callStmt = null;
        if (getPark(descriptionPark) != null) {
            try {
                openConnection();
                try {
                    callStmt = getConnection().prepareCall("{call updatePark(?,?,?,?,?,?,?,?,?)}");
                    Park s = reg.newPark(descriptionPark, latitude, longitude, elevation, textDescription, maxBicycles, maxScooters, inputVoltage, inputCurrent);
                    setDataToConnection(callStmt, s);
                    callStmt.setString(1, descriptionPark);
                    callStmt.setDouble(2, latitude);
                    callStmt.setDouble(3, longitude);
                    callStmt.setInt(4, elevation);
                    callStmt.setInt(5, maxBicycles);
                    callStmt.setInt(6, maxScooters);
                    callStmt.setInt(7, inputVoltage);
                    callStmt.setInt(8, inputCurrent);
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
        return false;
    }

    public List<String> getVehiclesFromPark(String descriptionPark) {
        CallableStatement callStmt = null;
        List<String> listVehicleID = new ArrayList<>();
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getVehiclesFromPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, descriptionPark);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                listVehicleID = createListOfVehicleIDFromResultSet(rSet);

                closeAll();
                return listVehicleID;
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

    public List<Park> getActiveParks() {
        CallableStatement callStmt = null;
        List<Park> listPark;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getActiveParks() }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                listPark = createListOfParkFromResultSet(rSet);
                closeAll();
                return listPark;
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

    public boolean checkParkAvailability(String parkID, String vehicleType) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call checkAvailability(?, ?) }");
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setString(2, parkID);
                callStmt.setString(3, vehicleType);
                callStmt.execute();
                int result = callStmt.getInt(1);
                return result != 0;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    public List<Park> createListOfParkFromResultSet(ResultSet rSet) {
        ArrayList<Park> lstPark = new ArrayList<>();
        try {
            while (rSet.next()) {
                String parkID = rSet.getString(1);
                double parkLatitude = rSet.getDouble(2);
                double parkLongitude = rSet.getDouble(3);
                int parkElevation = rSet.getInt(4);
                String parkDesc = rSet.getString(5);
                int parkMaxBycicles = rSet.getInt(6);
                int parkMaxScooters = rSet.getInt(7);
                int parkInputVoltage = rSet.getInt(8);
                int parkInputCurrent = rSet.getInt(9);
                Park park = rp.newPark(parkID, parkLatitude, parkLongitude, parkElevation, parkDesc, parkMaxBycicles, parkMaxScooters, parkInputVoltage, parkInputCurrent);
                lstPark.add(park);
            }
            return lstPark;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return lstPark;
        }
    }

    private List<String> createListOfVehicleIDFromResultSet(ResultSet rSet) {
        ArrayList<String> lstVehicles = new ArrayList<>();
        try {
            while (rSet.next()) {
                String vehicleID = rSet.getString(1);
                lstVehicles.add(vehicleID);
            }
            return lstVehicles;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            return lstVehicles;
        }
    }
    
    /**
     * Returns a list of the nearest parks within a given radius
     *
     * @param latitude  user latitude
     * @param longitude user longitude
     * @param radius    radius where to look for parks
     * @return list of nearest parks
     */
    public List<Park> getNearestParks(double latitude, double longitude, int radius) {
        CallableStatement callStmt = null;
        List<Park> listNearestPark;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getNearestParks(?, ?, ?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.setDouble(4, radius);
                callStmt.execute();
                ResultSet rSet = callStmt.getResultSet();
                listNearestPark = createListOfParkFromResultSet(rSet);
                return listNearestPark;
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

    public double getElevationFromCoords(double latitudeDestination, double longitudeDestination) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getElevationFromCoords(?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setDouble(2, latitudeDestination);
                callStmt.setDouble(3, longitudeDestination);
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

    public boolean assignVehiclePark(String idPark, String idVehicle) {
        boolean result;
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ call addVehiclePark(?,?) }");
                callStmt.setString(1, idPark);
                callStmt.setString(2, idVehicle);
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

    public String getParkFromVehicle(String idVehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getParkFromVehicle(?) }");
                callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
                callStmt.setString(2, idVehicle);
                callStmt.execute();
                return callStmt.getString(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return " ";
        }
    }

    public int getFreeBicycleSlotsAtPark(String parkID) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getFreeBicycleSlotsAtPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                callStmt.setString(2, parkID);
                callStmt.execute();
                return callStmt.getInt(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public int getFreeScooterSlotsAtPark(String parkID) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getFreeScooterSlotsAtPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                callStmt.setString(2, parkID);
                callStmt.execute();
                return callStmt.getInt(1);
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public List<Bicycle> getBicyclesFromPark(String descriptionPark) {
        CallableStatement callStmt = null;
        List<Bicycle> listBicycles = new ArrayList<>();
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getBicyclesFromPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, descriptionPark);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                while(rSet.next()) {
                    Bicycle b = new Bicycle(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4), rSet.getInt(5));
                    listBicycles.add(b);
                }

                closeAll();
                return listBicycles;
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

    public List<Scooter> getScootersFromPark(String parkID) {
        CallableStatement callStmt = null;
        List<Scooter> listScooters = new ArrayList<>();
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getScootersFromPark(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, parkID);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                while(rSet.next()) {
                    Scooter s = new Scooter(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4), rSet.getString(5), rSet.getInt(6), rSet.getDouble(7), rSet.getInt(8));
                    listScooters.add(s);
                }

                closeAll();
                return listScooters;
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
}