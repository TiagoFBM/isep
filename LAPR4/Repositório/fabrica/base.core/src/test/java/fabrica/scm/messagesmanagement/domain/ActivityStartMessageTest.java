package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.Product;
import fabrica.productordersmanagement.domain.ProductionOrder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityStartMessageTest {
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
    private static final String order7 = "007";
    private static final String order8 = "008";
    private static final String internalCode = "DD4";
    private static final short identificationNumber = 123;
    private static final String serialNumber = "ABC1234";
    private static final String manufacturer = "Manuf123";
    private static final String model = "model321";
    private static final Calendar installationDate = Calendars.of(2012,12, 12);
    private static final String operation = "operation987";

    @Test
    public void testCreateActivityStartMessageWithProductionOrder(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityStartMessage("20190202151045", m1, order);

    }

    @Test
    public void testCreateActivityStartMessageWithoutProductionOrder(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityStartMessage("20190202151045", m1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityStartMessageWithNullProductionOrder(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityStartMessage("20190202151045", m1, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityStartMessageWithNullMachine(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order3);
        orders.add(order4);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        new ActivityStartMessage("20190202151045", null, order);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityStartMessageWithNullSendDate(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order5);
        orders.add(order6);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityStartMessage(null, m1, order);

    }
    @Test
    public void obtainProductionOrder() {
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order7);
        orders.add(order8);
        ProductionOrder order = new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        ActivityStartMessage startMessage = new ActivityStartMessage("20190202151045", m1, order);
        Assert.assertEquals(startMessage.obtainProductionOrder(),order);

    }
}