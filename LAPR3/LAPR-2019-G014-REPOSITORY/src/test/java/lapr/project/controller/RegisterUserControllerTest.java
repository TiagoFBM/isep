package lapr.project.controller;

import lapr.project.data.UserDB;
import lapr.project.model.User;
import lapr.project.utils.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegisterUserControllerTest {

    private static RegisterUserController controller;
    private static UserDB db;

    @BeforeAll
    static void VehicleController() {
        try {
            Properties properties =
                    new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = new RegisterUserController();
        db = mock(UserDB.class);
        controller.setUserDB(db);
    }

    @Test
    void newUser() {
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        User us2 = controller.newUser("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        Assert.assertEquals(us1, us2);
    }

    @Test
    void registerUser1() {
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(db.addUserToDataBase(us1, Utils.USER_PERMISSION)).thenReturn(true);

        boolean result = controller.registerUser(us1);

        Assert.assertTrue(result);
    }

    @Test
    void registerUser2() {
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(db.addUserToDataBase(us1, Utils.USER_PERMISSION)).thenReturn(false);

        boolean result = controller.registerUser(us1);

        Assert.assertFalse(result);
    }

    @Test
    void getUser() {
        User us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

        when(db.getUserByName(us1.getUsername())).thenReturn(us1);

        Assert.assertEquals(us1,controller.getUser(us1.getUsername()));
    }

}