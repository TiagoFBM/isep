package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.production.domain.Product;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductionDeliveryMessageTest {

    @Test
    public void testCreateProductionDeliveryWithoutLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = 1000;
        new ProductionDeliveryMessage("20190202151045", m, p, quant,d);
    }

    @Test
    public void testCreateProductionDeliveryWithtLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = 1000;
        String lotID = "L041";
        new ProductionDeliveryMessage("20190202151045", m, p, quant,d,lotID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionDeliveryWithNullDeposit(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        int quant = 1000;
        String lotID = "L041";
        new ProductionDeliveryMessage("20190202151045", m, p, quant,null,lotID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionDeliveryWithNegativeQuantity(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = -5;
        String lotID = "L041";
        new ProductionDeliveryMessage("20190202151045", m, p, quant,d,lotID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionDeliveryWithEmptyLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = 1000;
        new ProductionDeliveryMessage("20190202151045", m, p, quant,d,"");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionDeliveryWithNullLot(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = 1000;
        new ProductionDeliveryMessage("20190202151045", m, p, quant,d,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionDeliveryWithoutInvalidTime(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Deposit d = new Deposit("1", "1st Deposit");
        int quant = 1000;
        new ProductionDeliveryMessage("151045", m, p, quant,d);
    }


}