package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ActivityResumptionMessageTest {

    private static final String internalCode = "DD4";
    private static final short identificationNumber = 123;
    private static final String serialNumber = "ABC1234";
    private static final String manufacturer = "Manuf123";
    private static final String model = "model321";
    private static final Calendar installationDate = Calendars.of(2012,12, 12);
    private static final String operation = "operation987";

    @Test
    public void testCreateActivityResumptionMessage(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityResumptionMessage("20190202151045", m1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityResumptionMessageWithNullMachine(){
        new ActivityResumptionMessage("20190202151045", null);

    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateActivityResumptionMessageWithNullSendDate(){
        Machine m1 = new Machine(internalCode, identificationNumber, serialNumber, manufacturer, model, installationDate, operation);
        new ActivityResumptionMessage(null, m1);

    }

}