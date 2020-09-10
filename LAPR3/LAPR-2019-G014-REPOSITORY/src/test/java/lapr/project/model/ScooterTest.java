package lapr.project.model;

import lapr.project.controller.VehicleController;
import lapr.project.data.VehicleDB;
import lapr.project.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScooterTest {

    static RegisterVehicle reg;
    static VehicleDB db;
    static Scooter s;
    static VehicleController controller;

    @BeforeAll
    static void VehicleTest(){
        reg = new RegisterVehicle();
        db = new VehicleDB();
        controller = new VehicleController();
        s = reg.newScooter("ePT050",12,1.10,0.3,"city",1,0.75,150);
    }

    @Test
    void setPotency(){
        int potency = 170;
        s.setPotency(potency);
        assertEquals(potency,s.getPotency());
    }

    @Test
    void getType() {
        String type = "city";

        assertEquals(type,s.getType());
    }

    @Test
    void setType() {
        String type = "off-road";

        s.setType(type);

        assertEquals(type,s.getType());
    }

    @Test
    void getMaxBatery() {
        s.setMaxBatery(2);
        int maxBatery = 2;

        assertEquals(maxBatery,s.getMaxBatery());
    }

    @Test
    void setMaxBatery() {
        int maxBatery = 2;

        s.setMaxBatery(maxBatery);

        assertEquals(maxBatery,s.getMaxBatery());
    }

    @Test
    void getActualBatery() {
        s.setMaxBatery(2);
        int maxBatery = 2;
        assertEquals(maxBatery,s.getMaxBatery());
    }

    @Test
    void setActualBatery() {
        int maxBatery = 2;

        s.setActualBatery(maxBatery);

        assertEquals(maxBatery,s.getActualBatery());
    }

    @Test
    void hashCodeTest(){
        Assert.assertEquals(s.hashCode(), s.hashCode());
    }
}