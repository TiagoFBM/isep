package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import org.junit.Test;

public class ForcedStopMessageTest {

    @Test
    public void testCreateForcedStopmessage(){
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        String codErro = "1000";
        new ForcedStopMessage("20190202151045", m, codErro);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForcedStopmessageNullMachine(){
        String codErro = "1000";
        new ForcedStopMessage("20190202151045", null, codErro);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForcedStopmessageNullErrorCode(){
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        String codErro = "1000";
        new ForcedStopMessage("20190202151045", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForcedStopmessageEmptyErrorCode(){
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        String codErro = "1000";
        new ForcedStopMessage("20190202151045", null, "");
    }

}