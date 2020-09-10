package lapr.project.model;

import lapr.project.controller.VehicleController;
import lapr.project.data.VehicleDB;
import lapr.project.utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BicycleTest {

    static RegisterVehicle reg;
    static VehicleDB db;
    static Bicycle b;
    static VehicleController controller;

    @BeforeAll
    static void VehicleTest() {
        reg = new RegisterVehicle();
        db = new VehicleDB();
        controller = new VehicleController();
        b = reg.newBicycle("ePT050", 12,1.10, 0.3, 15);
    }

    @Test
    void setWheelSize() {
        int newSize = 12;

        b.setWheelSize(newSize);

        assertEquals(newSize,b.getWheelSize());
    }
}