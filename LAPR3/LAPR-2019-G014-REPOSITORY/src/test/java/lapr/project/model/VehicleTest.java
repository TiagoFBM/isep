package lapr.project.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {

    private static RegisterVehicle reg;
    private static Vehicle v1;
    private static Vehicle s1;

    @BeforeAll
    static void VehicleTests() {
        reg = new RegisterVehicle();
        v1 = reg.newBicycle("PT050", 12,1.10, 0.3, 15);
        s1 = reg.newScooter("ePT050", 12,1.10, 0.3, "city", 1, 0.75, 120);
    }

    @Test
    void setStatus() {
        int expected = 0;
        v1.setStatus(expected);
        Assert.assertEquals(expected, v1.getStatus());
    }

    @Test
    void getDescriptionVehicle() {
        String descB = "PT050";

        assertEquals(descB, v1.getDescriptionVehicle());
    }

    @Test
    void setDescriptionVehicle() {
        String newDesc = "01";

        v1.setDescriptionVehicle(newDesc);

        assertEquals(newDesc, v1.getDescriptionVehicle());
    }

    @Test
    void setWeight() {
        int newWeight = 15;

        v1.setWeight(newWeight);

        assertEquals(newWeight, v1.getWeight());
    }

    @Test
    void getWeight() {
        v1.setWeight(15);

        int weight = 15;

        assertEquals(weight, v1.getWeight());
    }

    @Test
    void getAerodynamic() {
        v1.setAerodynamic(1.15);

        double aero = 1.15;

        assertEquals(aero, v1.getAerodynamic());
    }

    @Test
    void setAerodynamic() {
        double newAero = 1.15;

        v1.setAerodynamic(newAero);

        assertEquals(newAero, v1.getAerodynamic());
    }

    @Test
    void getFrontalArea() {
        double area = 0.3;

        assertEquals(area, v1.getFrontalArea());
    }

    @Test
    void setFrontalArea() {
        double newArea = 0.5;

        v1.setFrontalArea(newArea);

        assertEquals(newArea, v1.getFrontalArea());
    }

    @Test
    void hashCodeTest() {
        Assert.assertEquals(v1.hashCode(), v1.hashCode());
    }

    @Test
    void hashCodeTestBike(){ Assert.assertEquals(v1.hashCode(), v1.hashCode()); }

    @Test
    void hashCodeTestScooter(){ Assert.assertEquals(s1.hashCode(), s1.hashCode()); }

    /**
     * Test of method equals if two bicycles are equals.
     */
    @Test
    void equals1() {
        Assert.assertTrue(v1.equals(v1));
    }

    /**
     * Test of method equals if two bicycles are equals.
     */
    @Test
    void equals2() {
        Assert.assertTrue(v1.equals(reg.newBicycle("PT050", 12, 1.10, 0.3, 15)));
    }

    /**
     * Test of method equals if bicycle aren't equals.
     */
    @Test
    void equals3() {
        Bicycle v2 = new Bicycle("",0,0,0,0);
        Assert.assertFalse(v1.equals(v2));
    }

    /**
     * Test of method equals if object is null.
     */
    @Test
    void equals4() {
        Assert.assertFalse(v1.equals(null));
    }

    /**
     * Test of method equals if two objects have diferent classes.
     */
    @Test
    void equals5() {
        Assert.assertFalse(v1.equals(s1));
    }

    /**
     * Test of method equals if two bicycles have diferent wheel_size.
     */
    @Test
    void equals6() {
        Bicycle v2 = new Bicycle("PT050", 12, 1.10, 0.3, 10);
        Assert.assertFalse(v1.equals(v2));
    }

    /**
     * Test of method equals if two scooters are equals.
     */
    @Test
    void equals7() {
        Assert.assertTrue(s1.toString().equals(s1.toString()));
    }

     /**
     * Test of method equals if two scooters are equals.
     */
    @Test
    void equals8() {
        Assert.assertTrue(s1.equals(reg.newScooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.75, 120)));
    }

    /**
     * Test of method equals if two scooters aren't equals.
     */
    @Test
    void equals9() {
        Scooter s2 = new Scooter("",0,0,0,"city",0,0,0);
        Assert.assertFalse(s1.equals(s2));
    }
    
    /**
     * Test of method equals if scooter is null.
     */
    @Test
    void equals10() {
        Assert.assertFalse(s1.equals(null));
    }

    /**
     * Test of method equals if two objects have diferent classes.
     */
    @Test
    void equals11() {
        Assert.assertFalse(s1.toString().equals(v1.toString()));
    }

    /**
     * Test of method equals if two scooters have diferent maxBatery.
     */
    @Test
    void equals12() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.3, "city", 2, 0.75,120);
        Assert.assertFalse(s1.equals(s2));
    }
    
    /**
     * Test of method equals if two scooters have diferent actualBatery.
     */
    @Test
    void equals13() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.3, "city", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    }
    
    /**
     * Test of method equals if two scooters have diferent actualBatery.
     */
    @Test
    void equals14() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.3, "off-road", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    }   
    
     /**
     * Test of method equals if two scooters have diferent coordenates.
     */
    @Test
    void equals15() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.3, "off-road", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    } 
    
     /**
     * Test of method equals if two scooters have diferent coordenates.
     */
    @Test
    void equals16() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.3, "off-road", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    } 
        
     /**
     * Test of method equals if two scooters have diferent aerodynamic.
     */
    @Test
    void equals17() {
        Scooter s2 = new Scooter("ePT050", 12, 1.50, 0.3, "off-road", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    } 
    
    /**
     * Test of method equals if two scooters have diferent frontalArea.
     */
    @Test
    void equals18() {
        Scooter s2 = new Scooter("ePT050", 12, 1.10, 0.7, "off-road", 1, 0.50,120);
        Assert.assertFalse(s1.equals(s2));
    }

    @Test
    void equals19(){
        Bicycle bike = new Bicycle("ePT050", 12,1.10, 0.3, 15);
        Assert.assertFalse(s1.equals(bike));
    }
}
