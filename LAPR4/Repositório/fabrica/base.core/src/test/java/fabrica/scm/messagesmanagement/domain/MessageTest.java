package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import fabrica.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class MessageTest {

    Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
    String codErro = "1000";
    private ForcedStopMessage message = new ForcedStopMessage("20190202151045",m,codErro);

    @Test
    public void sameAsSameObject() {
        Assert.assertTrue(message.sameAs(message));
    }

    @Test
    public void sameAsNotInstanceOf() {
        Assert.assertTrue(m.sameAs(m));
    }

    @Test
    public void sameAsDiferentConstructor() {
        Machine m = new Machine("DD5",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        String codErro = "5";
        ForcedStopMessage message2 = new ForcedStopMessage("20190202151145",m,codErro);

        Assert.assertNotEquals(message, message2);
    }

    @Test
    public void obtainDate() {
        String sendDateTime="20190202151045";
        Date expected = DateUtils.getDate(sendDateTime.trim().substring(0, 4), sendDateTime.trim().substring(4, 6), sendDateTime.trim().substring(6, 8),
                sendDateTime.trim().substring(8, 10), sendDateTime.trim().substring(10, 12), sendDateTime.trim().substring(12));
        Assert.assertEquals(message.obtainDate(), expected);
    }

    @Test
    public void obtainMachine() {
        Assert.assertEquals(message.obtainMachine(),m);
    }
}