package lapr.project.controller;

import lapr.project.data.PointOfInterestDB;
import lapr.project.model.PointOfInterest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddTouristicPointOfInterestControllerTest {

    private static AddTouristicPointOfInterestController controller;
    private static PointOfInterestDB db;

    @BeforeAll
    static void AddTouristicPointOfInterestController() {
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = new AddTouristicPointOfInterestController();
        db = mock(PointOfInterestDB.class);
        controller.setPoiDb(db);
    }

    @Test
    void createNewPOI1() {
        when(db.getMostRecentPointOfInterestCode()).thenReturn(0);

        PointOfInterest poi1 = new PointOfInterest("POI_1","01", 12.2, 14.42, 11 );
        PointOfInterest poi2 = controller.createNewPOI("01", 12.2, 14.42, 11);
        Assert.assertEquals(poi1.toString(),poi2.toString());
    }

    @Test
    void createNewPOI2() {
        when(db.getMostRecentPointOfInterestCode()).thenReturn(-1);

        PointOfInterest poi2 = controller.createNewPOI("01", 12.2, 14.42, 11);
        Assert.assertNull(poi2);
    }

    @Test
    void registerPointOfInterestTest1() {
        PointOfInterest poi1 = new PointOfInterest("POI_1","01", 12.2, 14.42, 11 );
        when(db.registerPointOfInterest(poi1)).thenReturn(true);
        boolean result = controller.registerPointOfInterest(poi1);
        Assert.assertTrue(result);
    }

    @Test
    void getPointOfInterest() {
        PointOfInterest poi1 = new PointOfInterest("POI_1","01", 12.2, 14.42, 11 );
        when(db.getPointOfInterest(poi1.getLatitude(),poi1.getLongitude())).thenReturn(poi1);
        Assert.assertEquals(poi1,controller.getPointOfInterest(poi1.getLatitude(), poi1.getLongitude()));
    }
}