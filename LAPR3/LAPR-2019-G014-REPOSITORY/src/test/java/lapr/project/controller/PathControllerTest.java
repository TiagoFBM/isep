package lapr.project.controller;

import lapr.project.data.PathDB;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathControllerTest {

    private static PathDB pdb;
    private static PathController controller = new PathController();
    @BeforeAll
    static void ParkControllerTest(){
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

        pdb = new PathDB();
        pdb = mock(PathDB.class);
        controller.setPathDB(pdb);

    }

    @Test
    void registerPath1() {

        when(pdb.registerPath(10, 10, 20, 20, 5, 60, 30)).thenReturn(true);
        boolean flag = controller.registerPath(10,10, 20,20, 5, 60, 30);
        Assert.assertTrue(flag);
    }

    @Test
    void registerPath2() {

        when(pdb.registerPath(10, 10, 20, 20, 5, 60, 30)).thenReturn(false);
        boolean flag = controller.registerPath(10,10, 20,20, 5, 60, 30);
        Assert.assertFalse(flag);
    }
}