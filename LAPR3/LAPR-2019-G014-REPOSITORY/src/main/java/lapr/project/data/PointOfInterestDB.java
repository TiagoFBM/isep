package lapr.project.data;

import lapr.project.model.PointOfInterest;
import lapr.project.model.RegisterPointOfInterest;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PointOfInterestDB extends DataHandler {

    private final RegisterPointOfInterest registerPointOfInterest;
    public PointOfInterestDB() {
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

        registerPointOfInterest = new RegisterPointOfInterest();
    }

    public boolean registerPointOfInterest(PointOfInterest poi) {
        boolean result;
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ call registerPointOfInterest(?,?,?,?,?) }");

                callStmt.setString(1, poi.getPoiDescription());
                callStmt.setString(2, poi.getPoiTextDescription());
                callStmt.setDouble(3, poi.getLatitude());
                callStmt.setDouble(4, poi.getLongitude());
                callStmt.setInt(5, poi.getElevation());

                callStmt.execute();

                closeAll();
                result = true;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            result = false;
        }
        return result;
    }

    public int getMostRecentPointOfInterestCode() {
        int ret;
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ?=call getMostRecentPointOfInterestCode() }");

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.execute();

                ret = callStmt.getInt(1);

                closeAll();
                return ret;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            ret = -1;
        }
        return ret;
    }

    public PointOfInterest getPointOfInterest(String poiDescription) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ?=call getPointOfInterest(?) }");

                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, poiDescription);
                callStmt.execute();

                ResultSet rs = (ResultSet) callStmt.getObject(1);

                if (rs.next()){
                    return new PointOfInterest(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));

                }
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public PointOfInterest getPointOfInterest(double latitude, double longitude) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getPointOfInterestCoor(?,?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setDouble(2, latitude);
                callStmt.setDouble(3, longitude);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                PointOfInterest p = createPoi(rSet);

                closeAll();
                return p;
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

    public PointOfInterest createPoi(ResultSet rSet) {
        PointOfInterest poi = null;
        try {
            while (rSet.next()) {
                String poiID = rSet.getString(1);
                String textDescription = rSet.getString(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                int elevation = rSet.getInt(5);
                poi = registerPointOfInterest.newPointOfInterest(poiID,textDescription,latitude,longitude,elevation);
            }
            return poi;

        } catch (SQLException e) {
            Logger.getLogger(VehicleDB.class.getName()).log(Level.SEVERE, null, e);
            return poi;
        }
    }
}
