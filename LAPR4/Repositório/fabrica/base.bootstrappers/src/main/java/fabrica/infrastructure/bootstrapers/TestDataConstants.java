package fabrica.infrastructure.bootstrapers;

import java.util.Calendar;

import eapli.framework.time.util.Calendars;

public final class TestDataConstants {

    //Deposits
    public static final String DEPOSIT_1 = "depo_1";
    public static final String DEPOSIT_2 = "depo_2";
    public static final String DEPOSIT_3 = "depo_3";
    public static final String DEPOSIT_4 = "depo_4";
    public static final String DEPOSIT_5 = "depo_5";

    public static final String USER_TEST1 = "user1";

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD1 = "Password1";

    @SuppressWarnings("squid:S2885")
    public static final Calendar DATE_TO_BOOK = Calendars.of(2017, 12, 01);

    private TestDataConstants() {
        // ensure utility
    }
}
