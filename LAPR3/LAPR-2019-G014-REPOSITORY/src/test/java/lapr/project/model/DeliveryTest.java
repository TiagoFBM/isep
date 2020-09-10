/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;


/**
 *
 * @author Daniel Lourenço
 */
class DeliveryTest {

    private static Delivery delivery;
    private static Delivery delivery1;
    private static java.sql.Date date1;

    @BeforeAll
    static void DeliveryTest(){
        int year = 2019;
        int month = 12;
        int day = 12;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
        // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        date1 = new java.sql.Date(cal.getTimeInMillis());

        delivery = new Delivery(1, 2, 1, date1, "Park1", 10);
        delivery1 = new Delivery(1, 2, 1, date1, "Park1", 10);

    }

    /**
     * Test of getDescriptionDelivery method, of class Delivery.
     */
    @Test
    void testGetDescriptionDelivery() {
        Delivery instance = new Delivery(1, 1, 1, date1, "Park1", 10);
        int expResult = 1;
        int result = instance.getDescriptionDelivery();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of setDescriptionDelivery method, of class Delivery.
     */
    @Test
    void testSetDescriptionDelivery() {
        int descriptionDelivery = 2;
        delivery.setDescriptionDelivery(descriptionDelivery);
        Assert.assertEquals(descriptionDelivery, delivery.getDescriptionDelivery());
    }

    /**
     * Test of getDescriptionRent method, of class Delivery.
     */

    @Test
    void testGetDescriptionRent() {
        Delivery instance = new Delivery(1, 1, 2,date1, "Park1", 10);
        int expResult = 1;
        int result = instance.getDescriptionRent();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of setDescriptionRent method, of class Delivery.
     */
    @Test
    void testSetDescriptionRent() {
        int descriptionRent = 9;
        delivery.setDescriptionRent(descriptionRent);
        Assert.assertEquals(descriptionRent, delivery.getDescriptionRent());
    }


    @Test
    void testsetDescriptionCost(){
        int expected = 2;
        delivery.setDescriptionCost(expected);
        int actual = delivery.getDescriptionCost();
        Assert.assertEquals(expected,actual);
    }

    /**
     * Test of getDateTimeEnd method, of class Delivery.
     */
    @Test
    void testGetDateTimeEnd() {
        Delivery instance = new Delivery(2, 2, 1, date1, "Park1", 10);
        Date expResult = date1;
        Date result = instance.getDateTimeEnd();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of setDateTimeEnd method, of class Delivery.
     */
    @Test
    void testSetDateTimeEnd() {
        Date dateTimeEnd = date1;
        delivery.setDateTimeEnd(dateTimeEnd);
        Assert.assertEquals(dateTimeEnd,delivery.getDateTimeEnd());
    }

    @Test
    void testSetDescriptionDestination(){
        String expected = "Park2";
        delivery.setDescriptionPointDestination(expected);
        String actual = delivery.getDescriptionPointDestination();
        Assert.assertEquals(actual,expected);
    }

    @Test
    void testSetFinalCost(){
        double expected = 20;
        delivery.setFinalCost(expected);
        double actual = delivery.getFinalCost();
        Assert.assertEquals(expected,actual, 0);
    }

    @Test
    void equals1(){
        Assert.assertTrue(delivery.equals(delivery));
    }

    @Test
    void equals2(){
        Delivery instance = null;
        Assert.assertFalse(delivery.equals(instance));
    }

    @Test
    void equals3(){
        Vehicle v1 = new Bicycle("PT050", 12, 1.10, 0.3, 15);
        Assert.assertFalse(delivery.equals(v1));
    }

    @Test
    void equals4(){
        Delivery instance = new Delivery(3, 1, 1, date1, "Park1", 10);
        Assert.assertFalse(instance.equals(delivery));
    }

     @Test
    void equals5(){
        Delivery instance = new Delivery(1, 1, 1, date1, "Park1", 10);
        Assert.assertFalse(instance.equals(delivery));
    }
    
     @Test
    void equals6(){
        Delivery instance = new Delivery(2, 1, 2, date1, "Park1", 10);
        Assert.assertFalse(instance.equals(delivery));
    }
    
     @Test
    void equals7(){
        Delivery instance = new Delivery(1, 4, 1, date1, "Park1", 10);
        Assert.assertFalse(instance.equals(delivery));
    }
    
    @Test
    void equals8(){
        Delivery instance = new Delivery(5, 2, 1, date1, "Park2", 10);
        Assert.assertFalse(instance.equals(delivery));
    }
    
    @Test
    void equals9(){
        Delivery instance = new Delivery(1, 8, 1, date1, "Park1", 15);
        Assert.assertFalse(instance.equals(delivery));
    }

    @Test
    void equals10(){
        Delivery instance = new Delivery(1, 2, 1, date1, "Park1", 10);
        Assert.assertTrue(delivery1.equals(instance));
    }

    @Test
    void hashCodeTest(){
        Assert.assertEquals(delivery.hashCode(), delivery.hashCode());
    }

    @Test
    void toStringTest(){
        Delivery instance = new Delivery(1, 1, 3, date1, "PARK", 10);
        String expected ="Delivery\n" +
                "descriptionDelivery='1', descriptionRent='1', descriptionCost=3, dateTimeEnd='2019-12-12', descriptionPointDestination='PARK', finalCost=10.0";
        String actual = instance.toString();
        Assert.assertEquals(expected.trim(),actual.trim());
    }

}
