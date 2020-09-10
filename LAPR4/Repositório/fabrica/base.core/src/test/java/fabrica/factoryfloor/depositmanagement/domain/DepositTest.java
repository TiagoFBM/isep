package fabrica.factoryfloor.depositmanagement.domain;

import fabrica.factoryfloor.depositmanagement.domain.Deposit;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepositTest {

    private static final String CODE="1";
    private static final String DESC="1st Deposit";

    @Test
    public void ensureDepositWithCodeDesc() {
        new Deposit(CODE, DESC);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCode() {
        new Deposit(null, DESC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDesc() {
        new Deposit(CODE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescMustNotBeEmpty() {
        new Deposit(CODE, "");
    }

}