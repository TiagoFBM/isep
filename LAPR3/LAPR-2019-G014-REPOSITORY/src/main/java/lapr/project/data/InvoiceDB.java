package lapr.project.data;

import lapr.project.model.Invoice;
import lapr.project.model.PointOfInterest;
import oracle.jdbc.OracleTypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDB extends DataHandler {

    public InvoiceDB() {
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

    private List<Invoice> createListOfInvoicesFromResultSet(ResultSet rSet) {
        ArrayList<Invoice> lstInvoices = new ArrayList<>();
        try {
            while (rSet.next()) {
                Invoice invoice = new Invoice(rSet.getString(1), rSet.getString(2), rSet.getDate(3), rSet.getDate(4), rSet.getString(5), rSet.getString(6), rSet.getDouble(7));
                lstInvoices.add(invoice);
            }
            return lstInvoices;
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }

    public List<Invoice> getInvoices(){
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{? = call getInvoices()}");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                return createListOfInvoicesFromResultSet(rSet);

            }finally {
                if (callStmt!=null){
                    callStmt.close();
                }
                closeAll();
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }


    public boolean deleteAllInvoices() {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{call deleteAllInvoices()}");
                callStmt.execute();
                return true;
            } finally {
                if (callStmt!=null){
                    callStmt.close();
                }
                closeAll();
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return false;
    }

    public int getUserPoints(String username){
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall(" { ? =call getUserPoints(?) }");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2, username);
                callStmt.execute();
                return callStmt.getInt(1);
            }finally {
                if  (callStmt!=null){
                    closeAll();
                }
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return 0;
    }

    public boolean atualizatePointsUser(String username, int travelPointsUsed) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{call updateUserPoints(?,?)}");
                callStmt.setString(1,username);
                callStmt.setInt(2, travelPointsUsed);
                callStmt.execute();
                return true;
            }finally {
                if  (callStmt!=null){
                    closeAll();
                }
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return false;
    }

    public List<Invoice> getInvoices(int month, String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{? = call getInvoicesMonthUser(?,?)}");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setInt(2,month);
                callStmt.setString(3, username);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                return createListOfInvoicesFromResultSet(rSet);

            } finally {
                if (callStmt!=null){
                    callStmt.close();
                }
                closeAll();
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return new LinkedList<>();
    }

    public int getEarnedPoints(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{? = call getEarnedPoints(?)}");
                callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
                callStmt.setString(2,username);
                callStmt.execute();
                return callStmt.getInt(1);

            } finally {
                if (callStmt!=null){
                    callStmt.close();
                }
                closeAll();
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return -1;
    }

    public List<String> getInvoices(String username) {
        CallableStatement callStmt = null;
        try {
            openConnection();
            try {
                callStmt = getConnection().prepareCall("{? = call getInvoicesUser(?)}");
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2,username);
                callStmt.execute();
                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                return createListOfCurrentDebtFromResultSet(rSet);

            } finally {
                if (callStmt!=null){
                    callStmt.close();
                }
                closeAll();
            }
        }catch (SQLException e){
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE,null,e);
        }
        return new LinkedList<>();
    }

    private LinkedList<String> createListOfCurrentDebtFromResultSet(ResultSet rSet) {
        LinkedList<String> lstInvoices = new LinkedList<>();
        try {
            while (rSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                Invoice invoice = new Invoice(rSet.getString(1),
                        rSet.getString(2), rSet.getDate(3), rSet.getDate(4),
                        rSet.getString(5), rSet.getString(6), rSet.getDouble(15));
                PointOfInterest p1 = new PointOfInterest(rSet.getString(5), rSet.getString(7),
                        rSet.getDouble(8), rSet.getDouble(9), rSet.getInt(10));
                PointOfInterest p2 = new PointOfInterest(rSet.getString(6), rSet.getString(11),
                        rSet.getDouble(12), rSet.getDouble(13), rSet.getInt(14));
                // DescriptionVehicle,
                // vehicle unlock time
                // vehicle lock time
                // origin park latitude
                // origin park longitude
                // destination park latitude
                // destination park longitude
                // total time spent in seconds
                // charged value
                stringBuilder.append(invoice.getDescriptionVehicle()).append(";").append(invoice.getDateTimeBegin()).append(";").append(invoice.getDateTimeFinal()).append(";").append(p1.getLatitude()).append(";").append(p1.getLongitude()).append(";").append(p1.getElevation()).append(";").append(p2.getLatitude()).append(";").append(p2.getLongitude()).append(";").append(p2.getElevation());
                lstInvoices.add(stringBuilder.toString());
                return lstInvoices;
            }

        } catch (SQLException e) {
            Logger.getLogger(InvoiceDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
