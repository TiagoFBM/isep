package lapr.project.controller;

import lapr.project.data.UserDB;
import lapr.project.model.RegisterUser;
import lapr.project.model.User;
import lapr.project.utils.Utils;

public class RegisterUserController {

    private UserDB udb;
    private final RegisterUser reg;

    public RegisterUserController() {
        udb = new UserDB();
        reg = new RegisterUser();
    }

    public void setUserDB(UserDB db){
        this.udb = db;
    }

    public User newUser(String username, String visa, String gender, String email, String password, int weight, int height, double cyclingAverageSpeed, int userPoints) {
        return reg.newUser(username, visa, gender, email, password, weight, height, cyclingAverageSpeed, userPoints);
    }

    public boolean registerUser(User user) {
        return udb.addUserToDataBase(user, Utils.USER_PERMISSION);
    }

    public User getUser(String id){
        return udb.getUserByName(id);
    }

}
