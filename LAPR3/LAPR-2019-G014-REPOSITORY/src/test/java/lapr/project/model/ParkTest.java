package lapr.project.model;

import lapr.project.controller.ParkController;
import lapr.project.data.ParkDB;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkTest {

    static RegisterPark reg;
    static ParkDB db;
    static Park p1;
    static ParkController controller;

    @BeforeAll
    static void ParkTest() {
        reg = new RegisterPark();
        db = new ParkDB();
        controller = new ParkController();
        p1 = reg.newPark("Park_01", -81.123212, 41.152712, 12, "Parque da Trindade", 20, 15, 250, 12);
    }

    @Test
    void getDescriptionPark() {
        String expected = "Park_01";
        Assert.assertEquals(expected, p1.getDescriptionPark());
    }

    @Test
    void setDescriptionPark() {
        String newDesc = "01";

        p1.setDescriptionPark(newDesc);
        Assert.assertEquals(newDesc, p1.getDescriptionPark());
    }

    @Test
    void getLatitude() {
        double expected = -81.123212;
        p1.setLatitude(expected);

        assertEquals(expected, p1.getLatitude());
    }

    @Test
    void setLatitude() {
        double expected = 12.1212121;

        p1.setLatitude(expected);
        assertEquals(expected, p1.getLatitude());
    }

    @Test
    void getLongitude() {
        double expected = 41.152712;

        p1.setLongitude(expected);

        assertEquals(expected, p1.getLongitude());
    }

    @Test
    void setLongitude() {
        double expected = 19.123421;

        p1.setLongitude(expected);
        assertEquals(expected, p1.getLongitude());
    }

    @Test
    void getElevation() {
        int expected = 12;
        p1.setElevation(expected);

        assertEquals(expected, p1.getElevation());

    }

    @Test
    void setElevation() {
        int expected = 2;

        p1.setElevation(expected);
        assertEquals(expected, p1.getElevation());
    }

    @Test
    void getTextDescription() {
        String expected = "Parque da Trindade";
        p1.setTextDescription(expected);

        Assert.assertEquals(expected, p1.getTextDescription());
    }

    @Test
    void setTextDescription() {
        String expected = "ola";

        p1.setTextDescription(expected);
        Assert.assertEquals(expected, p1.getTextDescription());
    }

    @Test
    void getMaxBicycles() {
        int expected = 20;
        p1.setMaxBicycles(expected);

        assertEquals(expected, p1.getMaxBicycles());
    }

    @Test
    void setMaxBicycles() {
        int expected = 32;

        p1.setMaxBicycles(expected);
        assertEquals(expected, p1.getMaxBicycles());
    }

    @Test
    void getMaxScooters() {
        int expected = 15;
        p1.setMaxScooters(expected);

        assertEquals(expected, p1.getMaxScooters());
    }

    @Test
    void setMaxScooters() {
        int expected = 51;

        p1.setMaxScooters(expected);
        assertEquals(expected, p1.getMaxScooters());
    }

    @Test
    void getInputVoltage() {
        int expected = 250;
        assertEquals(expected, p1.getInputVoltage());
    }

    @Test
    void setInputVoltage() {
        int expected = 250;

        p1.setInputVoltage(expected);
        assertEquals(expected, p1.getInputVoltage());
    }

    @Test
    void getInputCurrent() {
        int expected = 12;
        assertEquals(expected, p1.getInputCurrent());
    }

    @Test
    void setInputCurrent() {
        int expected = 4;

        p1.setInputCurrent(expected);
        assertEquals(expected, p1.getInputCurrent());
    }

    @Test
    void getStatus() {
        int expected = 1;
        p1.setStatus(expected);

        assertEquals(expected, p1.getStatus());
    }

    @Test
    void setStatus() {
        int expected = 0;

        p1.setStatus(expected);
        assertEquals(expected, p1.getStatus());
    }
}
