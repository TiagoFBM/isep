package lapr.project.controller;

import lapr.project.data.InvoiceDB;
import lapr.project.data.UserDB;
import lapr.project.model.Invoice;
import lapr.project.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class to class GenerateInvoiceController
 *
 * @author ines-
 */
class GenerateInvoiceControllerTest {

    private static GenerateInvoiceController controller;
    private static InvoiceDB db;
    private static UserDB udb;
    private static java.sql.Date initial1;
    private static java.sql.Date initial2;
    private static java.sql.Date initial3;
    private static java.sql.Date initial4;
    private static java.sql.Date initial5;

    /**
     *
     */
    @BeforeAll
    static void GenerateInvoiceControllerTest() {
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

        controller = new GenerateInvoiceController();
        db = mock(InvoiceDB.class);
        udb = mock(UserDB.class);
        controller.setInvoiceDB(db);
        controller.setUserDB(udb);

        Calendar cal = Calendar.getInstance();



        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 10 - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, 12);

        initial1 = new java.sql.Date(cal.getTimeInMillis());

        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 12- 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, 12);

        initial2 = new java.sql.Date(cal.getTimeInMillis());

        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 10- 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, 10);

        initial3 = new java.sql.Date(cal.getTimeInMillis());

        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 4- 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, 12);

        initial4 = new java.sql.Date(cal.getTimeInMillis());

        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 10- 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, 12);

        initial5 = new java.sql.Date(cal.getTimeInMillis());


    }

    @Test
    void generateInvoicesTest1() {

        User us1 = new User("Manuel", "123456789015345L", "Masculino", "testelapr3@gmail.com", "lapr_123456", 60, 170, 20, 0);
        User us2 = new User("Carla", "123656239015345L", "Feminino", "testelapr3@gmail.com", "lapr_123456", 60, 170, 20, 10);

        Invoice inv1 = new Invoice("Carla", "b01", initial1, initial1, "a", "local", 2.00);
        Invoice inv2 = new Invoice("Manuel", "sc10", initial2, initial2, "a", "local", 10.0);
        Invoice inv3 = new Invoice("Manuel", "sof11", initial3, initial3, "a", "local", 5.0);
        Invoice inv4 = new Invoice("Carla", "sc1", initial4, initial4, "a", "local", 2.50);
        Invoice inv5 = new Invoice("Manuel", "b02", initial5, initial5, "a", "local", 5.45);

        List<Invoice> listInv = new LinkedList<>();
        listInv.add(inv1);
        listInv.add(inv2);
        listInv.add(inv3);
        listInv.add(inv4);
        listInv.add(inv5);

        Map<String, StringBuilder> expected = new LinkedHashMap<>();

        StringBuilder InvoiceImpressed1 = new StringBuilder();
        InvoiceImpressed1.append("\n").append("Invoices: \n");
        InvoiceImpressed1.append(inv1.toString()).append("\n");
        InvoiceImpressed1.append(inv4.toString()).append("\n");
        InvoiceImpressed1.append("Custo Total: 3.5").append("\n");

        expected.put(us2.toString(), InvoiceImpressed1);

        StringBuilder InvoiceImpressed2 = new StringBuilder();
        InvoiceImpressed2.append("\n").append("Invoices: \n");
        InvoiceImpressed2.append(inv2.toString()).append("\n");
        InvoiceImpressed2.append(inv3.toString()).append("\n");
        InvoiceImpressed2.append(inv5.toString()).append("\n");
        InvoiceImpressed2.append("Custo Total: 20.45").append("\n");

        expected.put(us1.toString(), InvoiceImpressed2);

        when(db.getInvoices()).thenReturn(listInv);
        when(udb.getUserByName("Carla")).thenReturn(new User("Carla", "123456239015345L", "Feminino", "testelapr3@gmail.com", "lapr_123456", 60, 170, 20, 20));
        when(udb.getUserByName("Manuel")).thenReturn(new User("Manuel", "123656789015345L", "Masculino", "testelapr3@gmail.com", "lapr_123456", 60, 170, 20, 0));
        when(db.getUserPoints("Carla")).thenReturn(10);
        when(db.getUserPoints("Manuel")).thenReturn(0);
        when(db.deleteAllInvoices()).thenReturn(true);
        when(db.atualizatePointsUser("Carla", 10)).thenReturn(true);
        when(db.atualizatePointsUser("Manuel", 0)).thenReturn(false);
        Map<User, StringBuilder> result = controller.generateInvoices();
        assertEquals(expected.toString(), result.toString());
    }
    /*@Test
    void generateInvoicesTest2() {
        GenerateInvoiceController controller = new GenerateInvoiceController();
        when(db.getInvoices()).thenReturn(null);
        Map<User, StringBuilder> mapActual = new HashMap<>();
        Map<User, StringBuilder> mapExpected = controller.generateInvoices();
        Assert.assertEquals(mapExpected, mapActual);
     }*/

    @Test
    void generateInvoicesMonthUser(){
        Invoice inv1 = new Invoice("Manuel", "sc10", initial2,initial2, "a", "local", 10.0);
        Invoice inv2 = new Invoice("Manuel", "sof11", initial2, initial2, "a", "local", 5.0);
        LinkedList<Invoice> list = new LinkedList<>();
        list.add(inv1);
        list.add(inv2);

        when(db.getInvoices(10,"Manuel")).thenReturn(list);
        List<Invoice> actual = controller.generateInvoices(10, "Manuel");
        Assert.assertEquals(actual, list);

    }

    @Test
    void generateInvoicesUser(){
        List<String> list = new LinkedList<>();
        StringBuilder s1 = new StringBuilder("Vehicle1").append(initial1.toString()).append(initial2.toString()).append(10).append(10).append(20).append(20).append(initial2.getTime() - initial1.getTime()).append(20);
        list.add(s1.toString());
        when(db.getInvoices("Manuel")).thenReturn(list);
        List<String> actual = controller.generateInvoices("Manuel");
        Assert.assertEquals(actual, list);


    }

    @Test
    void getUserPreviousPoints(){
        int pointsExpected = 10;
        when(db.getUserPoints("Manuel")).thenReturn(10);
        int pointsActual = controller.getPreviousPoints("Manuel");
        Assert.assertEquals(pointsActual, pointsExpected);
    }

    @Test
    void getEarnedPoints(){
        double pointsEarnedExpected = 5;
        when(db.getEarnedPoints("Manuel")).thenReturn(5);
        double pointsActualEarned = controller.getEarnedPoints("Manuel");
        Assert.assertEquals(pointsActualEarned, pointsEarnedExpected,1);
    }

}
