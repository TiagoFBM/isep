package lapr.project.data;

import lapr.project.model.Delivery;
import lapr.project.model.RegisterDelivery;
import lapr.project.utils.Utils;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryDB extends DataHandler {

    RegisterDelivery rd = new RegisterDelivery();

    public DeliveryDB() {
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

    public boolean addDeliveryToDataBase(Delivery delivery) {
        CallableStatement callStmt = null;

        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ call addDelivery(?,?,?,?,?,?) }");

                callStmt.setInt(1, delivery.getDescriptionDelivery());
                callStmt.setInt(2, delivery.getDescriptionRent());
                callStmt.setInt(3, delivery.getDescriptionCost());
                callStmt.setDate(4, delivery.getDateTimeEnd());
                callStmt.setString(5, delivery.getDescriptionPointDestination());
                callStmt.setDouble(6, delivery.getFinalCost());
                callStmt.execute();
                closeAll();
                return true;
            } finally {
                if (callStmt != null) {
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Delivery getDelivery(int descriptionDelivery) {

        CallableStatement callStmt = null;
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getDelivery(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2, descriptionDelivery);
                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if (rSet.next()) {
                    int description = rSet.getInt(1);
                    int descriptionRent = rSet.getInt(2);
                    int descriptionCost = rSet.getInt(3);
                    Date dateTimeEnd = rSet.getDate(4);
                    String descriptionPointDestination = rSet.getString(5);
                    double finalCost = rSet.getDouble(6);
                    return rd.newDelivery(description, descriptionRent, descriptionCost, dateTimeEnd, descriptionPointDestination, finalCost);
                }
            } finally {
                if (callStmt != null){
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        throw new IllegalArgumentException("No Delivery with description:" + descriptionDelivery);
    }


    public long calculateTravelCost(int description) {
        double distance;
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                if (getDelivery(description)!=null){
                    distance = calculateDistanceTrip(description);
                    System.out.println(distance);
                    callStmt = getConnection().prepareCall("{? = call setTravelCost(?, ?) }");
                    callStmt.registerOutParameter(1, OracleTypes.DATE);
                    callStmt.setInt(2, description);
                    callStmt.setDouble(3,distance);
                    callStmt.execute();
                    return callStmt.getDate(1).getTime();

                }
            }finally {
                if (callStmt!=null){
                    closeAll();
                }

            }
        }catch (SQLException e){
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return -1;
    }

    private double calculateDistanceTrip(int description) {
        LinkedList<Double> list = new LinkedList<Double>();
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                if (getDelivery(description)!=null){
                    callStmt = getConnection().prepareCall("{? = call getCoordsFromTrip(?) }");
                    callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    callStmt.setInt(2, description);
                    callStmt.execute();
                    ResultSet rSet = (ResultSet) callStmt.getObject(1);
                    while (rSet.next()){
                        //Source Latitude
                        list.add(rSet.getDouble(1));
                        //Source Longitude
                        list.add(rSet.getDouble(2));
                        // Destiny Latitude
                        list.add(rSet.getDouble(3));
                        // Destiny Longitude
                        list.add(rSet.getDouble(4));
                    }
                    return Utils.calculateDistanceBetweenCoordinates(list.get(0), list.get(2), list.get(1), list.get(3));
                }
            }finally {
                if (callStmt!=null){
                    closeAll();
                }

            }
        }catch (SQLException e){
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return -1;

    }

    public List<String> getAllDeliveries(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getAllDeliveries(?) }");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2,username);
                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                return createListOfStringsFromResultSet(rSet);
            } finally {
                if (callStmt != null){
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return new LinkedList<>();
    }

    private List<String> createListOfStringsFromResultSet(ResultSet rSet) {
        StringBuilder stringBuilder = new StringBuilder();
        LinkedList<String> list = new LinkedList<>();
        try {
            while (rSet.next()){
                stringBuilder.append(rSet.getString(1))
                        .append(rSet.getString(2))
                        .append(rSet.getDate(3))
                        .append(rSet.getDate(4))
                        .append(rSet.getDouble(5))
                        .append(rSet.getDouble(6))
                        .append(rSet.getDouble(7))
                        .append(rSet.getDouble(8))
                        .append(rSet.getDouble(9))
                        .append(rSet.getDouble(10))
                        .append(rSet.getDouble(10) - rSet.getDouble(5))
                        .append(rSet.getInt(11));
                list.add(stringBuilder.toString());
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return new LinkedList<>();
    }

    public int getCostsID(int descriptionRent) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {

                callStmt = getConnection().prepareCall("{ ? = call getCostsID(?) }");
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setInt(2, descriptionRent);
                callStmt.execute();
                return callStmt.getInt(1);
            } finally {
                if (callStmt != null){
                    closeAll();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DeliveryDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return 1;
    }
}
