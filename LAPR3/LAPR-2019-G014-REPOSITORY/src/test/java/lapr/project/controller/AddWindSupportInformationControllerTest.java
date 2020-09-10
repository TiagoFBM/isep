package lapr.project.controller;

import lapr.project.data.PathDB;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddWindSupportInformationControllerTest {

    private static AddWindSupportInformationController controller;
    private static PathDB pdb;

    @BeforeAll
    static void AddWindSupportInformationController() {
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

        controller = new AddWindSupportInformationController();
        pdb = mock(PathDB.class);
        controller.setPathDB(pdb);
    }


    @Test
    void getActivePaths() {
        Map<String, List<String>> mapPaths = new HashMap<>();
        List<String> lstStr = new LinkedList<>();
        lstStr.add("02");
        lstStr.add("03");
        mapPaths.put("01", lstStr);
        when(pdb.getActivePaths()).thenReturn(mapPaths);
        Assert.assertEquals(mapPaths,controller.getActivePaths());

        Map<String, List<String>> mapPaths1 = new HashMap<>();
        List<String> lstStr1 = new LinkedList<>();
        lstStr1.add("01");
        lstStr1.add("03");
        lstStr1.add("04");
        mapPaths.put("02", lstStr1);
        when(pdb.getActivePaths()).thenReturn(mapPaths);
        Assert.assertEquals(mapPaths,controller.getActivePaths());
    }

    @Test
    void addWindInformationToPath() {
        int descriptionPath = 01;
        double windDirectionDegrees = 23.44;
        double avarageWindSpeed = 14.38;
        when(pdb.addWindInformationToPath(descriptionPath, windDirectionDegrees, avarageWindSpeed)).thenReturn(true);
        boolean result = controller.addWindInformationToPath(descriptionPath, windDirectionDegrees, avarageWindSpeed);
        Assert.assertTrue(result);
        int descriptionPath2 = 02;
        double windDirectionDegrees2 = 21.44;
        double avarageWindSpeed2 = 11.39;
        when(pdb.addWindInformationToPath(descriptionPath2, windDirectionDegrees2, avarageWindSpeed2)).thenReturn(true);
        boolean result2 = controller.addWindInformationToPath(descriptionPath2, windDirectionDegrees2, avarageWindSpeed2);
        Assert.assertTrue(result2);
    }
}