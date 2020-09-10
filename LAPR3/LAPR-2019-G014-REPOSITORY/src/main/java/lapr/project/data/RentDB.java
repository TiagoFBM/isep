package lapr.project.data;

import lapr.project.model.RegisterRent;
import lapr.project.model.Rent;
import lapr.project.model.Scooter;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RentDB extends DataHandler {

    private final RegisterRent rr;

    public RentDB() {
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
        rr = new RegisterRent();
    }

    public Rent getRent(int descriptionRent) {

        CallableStatement callStmt = null;
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getRent(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, descriptionRent);

                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int description = rSet.getInt(1);
                    String descriptionVehicle = rSet.getString(2);
                    String descriptionPoint = rSet.getString(3);
                    Date dateTimeBegin = rSet.getDate(4);
                    String dateHourLocal = rSet.getString(5);
                    String username = rSet.getString(6);
                    double latitude = rSet.getDouble(7);
                    double longitude = rSet.getDouble(8);
                    return rr.newRent(description, descriptionVehicle, descriptionPoint, dateTimeBegin, username);
                }
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
        }
        throw new IllegalArgumentException("No Rent with description:" + descriptionRent);
    }

    public int addRentToDataBase(String descriptionVehicle, String descriptionPoint, Date dateTimeBegin, String username) {
        CallableStatement callStmt = null;

        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{? = call registerRent(?,?,?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                callStmt.setString(2, descriptionVehicle);
                callStmt.setString(3, descriptionPoint);
                callStmt.setDate(4, dateTimeBegin);
                callStmt.setString(5, username);

                callStmt.execute();
                int id = callStmt.getInt(1);
                closeAll();
                return id;
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public double getInitialLat(int descriptionRent) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getInitialLat(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setInt(2, descriptionRent);
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

    public double getInitialLong(int descriptionRent) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getInitialLong(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setInt(2, descriptionRent);
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

    public int getRentByVehicle(String bicycleDescription, String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getRentByVehicle(?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setString(2, bicycleDescription);
                callStmt.setString(3, username);
                callStmt.execute();
                return callStmt.getInt(1);
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public Scooter getScooterByIdRent(int idRent){
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getScooterByIdRent(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, idRent);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                if (rSet.next()) {
                    return new Scooter(rSet.getString(1), rSet.getDouble(2), rSet.getDouble(3), rSet.getDouble(4), rSet.getString(5),
                            rSet.getInt(6), rSet.getDouble(7), rSet.getInt(8));
                }
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }

    public long forHowLongAVehicleIsUnlocked(String vehicle) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call forHowLongAVehicleIsUnlocked(?) }");
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                callStmt.setString(2, vehicle);
                callStmt.execute();
                return callStmt.getLong(1);
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public int getFreeSlotsAtParkForMyLoanedVehicle(String username,String park) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getFreeSlotsAtParkForMyLoanedVehicle(?) }");
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                callStmt.setString(2, username);
                callStmt.setString(3, park);
                callStmt.execute();
                return callStmt.getInt(1);
            } finally {
                if (callStmt != null) {
                    callStmt.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }
}
