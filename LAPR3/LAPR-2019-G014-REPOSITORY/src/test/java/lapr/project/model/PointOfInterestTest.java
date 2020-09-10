package lapr.project.model;

import lapr.project.controller.AddTouristicPointOfInterestController;
import lapr.project.data.PointOfInterestDB;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointOfInterestTest {

    static RegisterPointOfInterest reg;
    static PointOfInterestDB db;
    static PointOfInterest poi1;
    static PointOfInterest poi2;
    static AddTouristicPointOfInterestController regController;

    @BeforeAll
    static void PointOfInterestTest() {
        reg = new RegisterPointOfInterest();
        regController = new AddTouristicPointOfInterestController();
        db = new PointOfInterestDB();
        poi1 = new PointOfInterest("1","01", 12.2, 14.42, 11 );
        poi2 = new PointOfInterest("2","02", 13.1, 11.61, 9 );
    }

    @Test
    void equalsTest1() {

        Assert.assertTrue(poi1.equals(new PointOfInterest("1","01", 12.2, 14.42, 11)));
    }

    @Test
    void equalsTest2() {
        boolean result = poi2.equals(poi1);
        assertFalse(result);
    }

    @Test
    void equalsTest3() {

        Vehicle v = new Vehicle("V01", 1, 40, 10);
        boolean expResult = false;
        boolean result = poi1.equals(v);
        Assertions.assertEquals(expResult, result);
    }

    @Test
    void equalsTest4() {
        boolean expResult = false;
        boolean result = poi1.equals(null);
        Assertions.assertEquals(expResult, result);
    }

    @Test
    void equalsTest5(){
        PointOfInterest p = new PointOfInterest("1","01", 12.2, 14.42, 11 );
        Assert.assertTrue(p.equals(poi1));
    }


    @Test
    void hashCodeTest() {

        Assert.assertEquals(poi1.hashCode(), poi1.hashCode());
    }

    @Test
    void setPoiDescription() {
        String newDesc = "03";
        poi1.setPoiDescription(newDesc);
        Assert.assertEquals(newDesc, poi1.getPoiDescription());
    }

    @Test
    void getLatitude() {
        double expected = 12.2;
        poi1.setLatitude(expected);

        assertEquals(expected, poi1.getLatitude());
    }

    @Test
    void setLatitude() {
        double newLat = 14.9;
        poi1.setLatitude(newLat);
        assertEquals(newLat, poi1.getLatitude());
    }

    @Test
    void getLongitude() {

        double expected = 14.42;
        assertEquals(expected, poi1.getLongitude());
    }

    @Test
    void setLongitude() {
        double newLong = 12.11;
        poi1.setLongitude(newLong);
        assertEquals(newLong, poi1.getLongitude());
    }

    @Test
    void getElevation() {
        int expected = 11;
        assertEquals(expected, poi1.getElevation());
    }

    @Test
    void setElevation() {
        int newEleveation = 4;
        poi1.setElevation(newEleveation);

        assertEquals(newEleveation, poi1.getElevation());
    }

    @Test
    void testToString(){
        PointOfInterest p1 = new PointOfInterest("1","01", 12.2, 14.42, 11 );
        Assert.assertEquals(p1.toString(), poi1.toString());
    }
}