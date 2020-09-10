package lapr.project.data;
import lapr.project.model.User;
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

public class UserDB extends DataHandler {

    public UserDB(){
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
    }

    public boolean addUserToDataBase(User user, int permission) {
        CallableStatement callStmt = null;
        try {
            openConnection();

            try {
                callStmt = getConnection().prepareCall("{ call addUser(?,?,?,?,?,?,?,?,?) }");

                callStmt.setString(1, user.getUsername());
                callStmt.setString(2, user.getGender());
                callStmt.setString(3, user.getEmail());
                callStmt.setString(4, user.getPassword());
                callStmt.setInt(5, user.getWeight());
                callStmt.setInt(6, user.getHeight());
                callStmt.setDouble(7, user.getCyclingAverageSpeed());
                callStmt.setDouble(8, permission);
                callStmt.setString(9,user.getVisa());

                callStmt.execute();

                closeAll();
                return true;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int getUserWeight(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getUserWeight(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, username);
                callStmt.execute();
                return callStmt.getInt(1);
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

    public User getUserByName(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getUserByName(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2, username);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                if (rSet.next()) {
                    return new User(rSet.getString(1), rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6), rSet.getInt(7), rSet.getDouble(8), rSet.getInt(9));
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

    public double getUserAverageSpeed(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{ ? = call getUserAverageSpeed(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, username);
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
}
