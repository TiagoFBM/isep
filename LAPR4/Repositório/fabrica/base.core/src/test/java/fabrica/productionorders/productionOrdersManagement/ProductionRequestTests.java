package fabrica.productionorders.productionOrdersManagement;


import eapli.framework.time.util.Calendars;
import fabrica.production.domain.Product;
import fabrica.productordersmanagement.domain.Request;
import fabrica.productordersmanagement.domain.ProductionOrder;
import org.junit.Assert;
import org.junit.Test;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ProductionRequestTests {

    private static final String idCode = "123";
    private static final Calendar emissionDate = Calendars.of(2020,01, 20);
    private static final Calendar previsionDate = Calendars.of(2020, 06,06);
    private static List<String> orders;
    private static final String order1 = "001";
    private static final String order2 = "002";
    private static final String order3 = "003";


    @Test
    public void testCreateProductionOrder(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode, emissionDate, previsionDate, p, quant , unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithNullCode(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(null, emissionDate, previsionDate, p, quant,unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithNullEmissionDate(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,null, previsionDate, p, quant,unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithNullPrevisionDate(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,emissionDate, null, p, quant,unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithNullProduct(){
        orders = new LinkedList<>();
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,emissionDate, previsionDate, null, quant, unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithZeroQuantity(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,emissionDate, previsionDate, p, 0, unity,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithNullUnit(){
        orders = new LinkedList<>();
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,emissionDate, previsionDate, p, quant,null,orders);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProductionOrderWithWrongDates(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        new ProductionOrder(idCode,previsionDate, emissionDate, p, quant,unity,orders);
    }

    @Test
    public void addOrderIDToProductionOrder(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        ProductionOrder po = new ProductionOrder(idCode,emissionDate, previsionDate, p, quant,unity,orders);
        String code = "EC1";
        Request id = Request.valueOf(code);
        po.addOrderID(id);
    }

    @Test
    public void addOrderIDToProductionOrderRepetead(){
        Product p = new Product("12345", "54321","briefDescription", "fullDescription", "KILOGRAMS", "productCategory");
        int quant = 1000;
        String unity = "KILOGRAMS";
        ProductionOrder po = new ProductionOrder(idCode,emissionDate, previsionDate, p, quant,unity,orders);
        String code = "EC1";
        Request id = Request.valueOf(code);
        po.addOrderID(id);
        boolean actual = po.addOrderID(id);
        Assert.assertFalse(actual);
    }

}
