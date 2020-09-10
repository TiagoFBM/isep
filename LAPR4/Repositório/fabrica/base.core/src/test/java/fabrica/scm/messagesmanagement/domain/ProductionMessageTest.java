package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.Product;
import fabrica.productordersmanagement.domain.ProductionOrder;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProductionMessageTest {

    @Test
    public void testCreateProductionMessageWithoutLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321",Calendars.of(2012,12, 12),"operation987");
        int quant = 1000;
        new ProductionMessage("20190202151045", m, p, quant);
    }

    @Test
    public void testCreateProductionMessageWithLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321",Calendars.of(2012,12, 12),"operation987");
        int quant = 1000;
        String lotID = "L041";
        new ProductionMessage("20190202151045", m, p, quant,lotID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionMessageWithNullMachine(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        new ProductionMessage("20190202151045", null, p, quant);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionMessageWithNullProduct(){
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321",Calendars.of(2012,12, 12),"operation987");
        int quant = 1000;
        new ProductionMessage("20190202151045", m, null, quant);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionMessageWithEmptyLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        new ProductionMessage("20190202151045", null, p, quant,"");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionMessageWithNullLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        new ProductionMessage("20190202151045", null, p, quant,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionMessageWithNegativeQuantity(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321",Calendars.of(2012,12, 12),"operation987");
        String lotID = "L041";
        int quant = -5;
        new ProductionMessage("20190202151045", m, p, quant,lotID);
    }


}