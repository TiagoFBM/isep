package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.Product;
import fabrica.productordersmanagement.domain.ProductionOrder;
import org.junit.Test;

import java.util.*;

public class ActivityEndMessageTest {

    private static final String idCode = "123";
    private static final Calendar emissionDate = Calendars.of(2020,01, 20);
    private static final Calendar previsionDate = Calendars.of(2020, 06,06);
    private static final List<String> orders = new LinkedList<>();
    private static final String order1 = "001";
    private static final String order2 = "002";
    private static final String order3 = "003";
    private static final String order4 = "004";
    private static final String order5 = "005";
    private static final String order6 = "006";
    private static final String internalCode = "DD4";
    private static final short identificationNumber = 123;
    private static final String serialNumber = "ABC1234";
    private static final String manufacturer = "Manuf123";
    private static final String model = "model321";
    private static final Calendar installationDate = Calendars.of(2012,12, 12);
    private static final String operation = "operation987";

    @Test
    public void testCreateActivityEndMessageWithProductionOrder(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityEndMessage("20190202151045", m1, order);

    }

    @Test
    public void testCreateActivityEndMessageWithoutProductionOrder(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityEndMessage("20190202151045", m1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityEndMessageWithNullProductionOrder(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityEndMessage("20190202151045", m1, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityEndMessageWithNullMachine(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order3);
        orders.add(order4);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        new ActivityEndMessage("20190202151045", null, order);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityEndMessageWithNullSendDate(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order5);
        orders.add(order6);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityEndMessage(null, m1, order);

    }



}