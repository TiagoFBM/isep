package fabrica.scm.messagesmanagement.domain;

import eapli.framework.time.util.Calendars;
import fabrica.factoryfloor.machinemanagement.domain.Machine;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class FailureTest {

    private Failure fail = new Failure(new Date(), FailureType.INVALIDDEPOSIT, "msg");

    @Test
    public void archiveFailure() {
        fail.archiveFailure();
        Assert.assertEquals(fail.obtainFailureState(),FailureState.ARCHIVED);
    }

    @Test
    public void sameAsSameObject() {
        Machine m = new Machine("DD4",(short)123,"ABC1234","Manuf123","model321", Calendars.of(2012,12, 12),"operation987");
        Assert.assertFalse(fail.sameAs(m));
    }

    @Test
    public void testToString() {
        System.out.println(fail.toString());
        Assert.assertTrue(fail.toString().equalsIgnoreCase(fail.toString()));
    }
}