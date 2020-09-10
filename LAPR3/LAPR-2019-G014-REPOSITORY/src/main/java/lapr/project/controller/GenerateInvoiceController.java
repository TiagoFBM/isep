package lapr.project.controller;

import lapr.project.data.InvoiceDB;
import lapr.project.data.UserDB;
import lapr.project.model.Invoice;
import lapr.project.model.User;
import lapr.project.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateInvoiceController {

    private InvoiceDB idb;
    private UserDB udb;
    private final Map<String, List<Invoice>> mapInvoices = new HashMap<>();
    private final Map<User, StringBuilder> mapInvoicesUsers = new HashMap<>();

    public GenerateInvoiceController() {
        this.idb = new InvoiceDB();
        this.udb = new UserDB();
    }

    public void setInvoiceDB(InvoiceDB db) {
        this.idb = db;
    }

    public void setUserDB(UserDB db){this.udb = db;}

    public Map<User, StringBuilder> generateInvoices() {

        double totalCost;
        int travelPoints;
        StringBuilder InvoiceImpressed;
        List<Invoice> invoiceList = idb.getInvoices();
        if (invoiceList!=null){
            for (Invoice invoice : invoiceList) {
                if (mapInvoices.containsKey(invoice.getUsername())) {
                    mapInvoices.get(invoice.getUsername()).add(invoice);
                } else {
                    mapInvoices.put(invoice.getUsername(), new ArrayList<>());
                    mapInvoices.get(invoice.getUsername()).add(invoice);
                }
            }

            for (String username : mapInvoices.keySet()) {
                InvoiceImpressed = new StringBuilder();
                totalCost = 0;
                InvoiceImpressed.append("\n").append("Invoices: \n");

                for (Invoice inv : mapInvoices.get(username)) {
                    InvoiceImpressed.append(inv.toString() + "\n");
                    totalCost = totalCost + inv.getTotalCost();
                }
                travelPoints = idb.getUserPoints(username);
                while (travelPoints>= Utils.VALUES_TO_SWAP_POINTS && totalCost >= 1 ){
                    totalCost = totalCost - 1;
                    travelPoints = travelPoints - Utils.VALUES_TO_SWAP_POINTS;
                }
                InvoiceImpressed.append("Custo Total: ").append(totalCost).append("\n");
                mapInvoicesUsers.put(udb.getUserByName(username), InvoiceImpressed);
                idb.atualizatePointsUser(username, travelPoints);
            }

            /*boolean flag = emailController.sendEmail(mapInvoicesUsers);

            if (flag==true){
                idb.deleteAllInvoices();
            }*/

        }

        return mapInvoicesUsers;
    }

    public List<Invoice> generateInvoices(int month, String username) {
        return idb.getInvoices(month, username);
    }

    public List<String> generateInvoices (String username){return idb.getInvoices(username);}

    public int getPreviousPoints(String username) {
        return idb.getUserPoints(username);
    }

    public double getEarnedPoints(String username) {
         return idb.getEarnedPoints(username);
    }
}
