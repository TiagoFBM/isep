package lapr.project.model;

public class RegisterUser {

    public User newUser(String username, String visa, String gender, String email, String passowrd, int weight, int height, double cyclingAverageSpeed,int userPoints){
        return new User(username,visa,gender,email,passowrd,weight,height,cyclingAverageSpeed, userPoints);
    }
}
