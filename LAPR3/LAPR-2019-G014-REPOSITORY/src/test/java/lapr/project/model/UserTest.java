package lapr.project.model;

import lapr.project.controller.RegisterUserController;
import lapr.project.data.UserDB;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class UserTest {

    static RegisterUser reg;
    static UserDB db;
    static User us1;
    static User us2;
    static User us3;
    static RegisterUserController regController;

    @BeforeAll
    static void UserTest() {
        reg = new RegisterUser();
        regController = new RegisterUserController();
        db = new UserDB();
        us1 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        us3 = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        us2 = new User("User2", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);

    }

    @Test
    void setUsername() {
        String strExpected = "UserOne";
        us1.setUsername("UserOne");
        String strActual = us1.getUsername();
        Assertions.assertEquals(strActual, strExpected);
    }

    @Test
    void setVisa() {
        String expected = "123456789019321";
        us1.setVisa("123456789019321");
        String actual = us1.getVisa();
        Assert.assertEquals(expected, actual);
    }

    @Test
    void setGender() {
        String expected = "Masculino";
        us1.setGender("Masculino");
        String actual = us1.getGender();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setEmail() {
        String expected = "userOne@gmail.com";
        us1.setEmail("userOne@gmail.com");
        String actual = us1.getEmail();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setPassword() {
        String expected = "qwerty";
        us1.setPassword("qwerty");
        String actual = us1.getPassword();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setWeight() {
        int expected = 65;
        us1.setWeight(65);
        int actual = us1.getWeight();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setHeight() {
        int expected = 175;
        us1.setHeight(175);
        int actual = us1.getHeight();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setCyclingAverageSpeed() {
        double expected = 23;
        us1.setCyclingAverageSpeed(23);
        double actual = us1.getCyclingAverageSpeed();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setStatus() {
        int expected = 1;
        us1.setStatus(1);
        int actual = us1.getStatus();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void setUserPoints(){
        int expected = 10;
        us1.setUserPoints(10);
        Assert.assertEquals(expected, us1.getUserPoints());
    }

    /**
     * Test of method equals if two users are equals.
     */
    @Test
    void equals1() {
        Assert.assertTrue(us2.equals(new User("User2", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0)));
    }

    /**
     * Test of method equals if two users are equals.
     */
    @Test
    void equals2() {
        boolean result = us2.equals(us1);
        assertFalse(result);
    }

    /**
     * Test of method equals if user is null.
     */
    @Test
    void equals3() {
        boolean expResult = false;
        boolean result = us1.equals(null);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of method equals if two objects have diferent classes.
     */
    @Test
    void equals4() {
        Vehicle v = new Vehicle("V01", 1, 40, 10);
        boolean expResult = false;
        boolean result = us1.equals(v);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of method equals if two objects have diferent visa.
     */
    @Test
    void equals5() {
        User us3 = new User(" User3", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        boolean expResult = false;
        boolean result = us1.equals(us3);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of method equals if two objects have diferent usarname and gender.
     */
    @Test
    void equals6() {
        User us3 = new User(" User3", "123456789012345", "Masculino", "user3@gmail.com", "123456", 60, 170, 20, 0);
        boolean expResult = false;
        boolean result = us1.equals(us3);
        Assertions.assertEquals(expResult, result);
    }

    @Test
    void equals7(){
        User user = new User("User1", "123456789012345", "Feminino", "user1@gmail.com", "123456", 60, 170, 20, 0);
        Assert.assertTrue(user.equals(us3));
    }

    @Test
    void hashCodeTest() {
        Assert.assertEquals(us1.hashCode(), us1.hashCode());
    }

    @Test
    void toStringTest(){
        String expected = "User\n" +
                "Username='User2', gender='Feminino', email='user1@gmail.com', weight=60, height=170, cyclingAverageSpeed=20.0";
        String actual = us2.toString();
        Assert.assertEquals(expected.trim(),actual.trim());
    }

}
