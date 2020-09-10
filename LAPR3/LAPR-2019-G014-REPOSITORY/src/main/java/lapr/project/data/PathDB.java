package lapr.project.data;

import lapr.project.model.RegisterPark;
import lapr.project.utils.Utils;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathDB extends DataHandler {

    private final RegisterPark rp;

    public PathDB() {
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

    public boolean registerPath(double latitudeA, double longitudeA, double latitudeB, double longitudeB, double kineticCoefficient, double windDirection, double windSpeed){
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ call registerPath(?,?,?,?,?,?,?) }");
                callStmt.setDouble(1, latitudeA);
                callStmt.setDouble(2, longitudeA);
                callStmt.setDouble(3, latitudeB);
                callStmt.setDouble(4, longitudeB);
                callStmt.setDouble(5, kineticCoefficient);
                callStmt.setDouble(6, windDirection);
                callStmt.setDouble(7, windSpeed);

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

    public Map<String, List<String>> getActivePaths() {
        CallableStatement callStmt = null;
        Map<String, List<String>> pathMap = new HashMap<>();
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getActivePathsOriginAndDestination() }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.execute();
                ResultSet rSet = callStmt.getResultSet();

                while (rSet.next()) {
                    String origin = rSet.getString(1);
                    String destination = rSet.getString(2);
                    if (pathMap.get(origin) == null) {
                        List<String> list = new LinkedList<>();
                        pathMap.put(origin, list);
                    }
                    pathMap.get(origin).add(destination);
                }

                closeAll();
                return pathMap;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public boolean addWindInformationToPath(int descriptionPath, double windDirectionDegrees, double averageWindSpeed) {
        CallableStatement callStmt = null;
        try {
            openConnection();

            try {
                callStmt = getConnection().prepareCall("{ call addWindInformationToPath(?,?,?) }");
                callStmt.setInt(1, descriptionPath);
                callStmt.setDouble(2, windDirectionDegrees);
                callStmt.setDouble(3, averageWindSpeed);

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
    
     /**
     * Get the speed wind on path.
     *
     * @param lat1 latitude point 1
     * @param long1 longitude point 1
     * @param lat2 latitude point 2
     * @param long2 longitude point 2
     * @return speed wind
     */
    public double getWindSpeed(double lat1, double long1, double lat2, double long2) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getWindSpeed(?,?,?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setDouble(2, lat1);
                callStmt.setDouble(3, long1);
                callStmt.setDouble(4, lat2);
                callStmt.setDouble(5, long2);
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

    public double getwindDirection(double lat1, double long1, double lat2, double long2) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getwindDirection(?,?,?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setDouble(2, lat1);
                callStmt.setDouble(3, long1);
                callStmt.setDouble(4, lat2);
                callStmt.setDouble(5, long2);
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

    public double getKineticCoefficient(double lat1, double long1, double lat2, double long2) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getKineticCoefficient(?,?,?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setDouble(2, lat1);
                callStmt.setDouble(3, long1);
                callStmt.setDouble(4, lat2);
                callStmt.setDouble(5, long2);
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
}