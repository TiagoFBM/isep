package lapr.project.model;

import lapr.project.utils.Utils;

import java.util.Objects;

public class User {
    private String username;
    private String visa;
    private String gender;
    private String email;
    private String password;
    private int weight;
    private int height;
    private double cyclingAverageSpeed;
    private int status;
    private int userPoints;

    public User(String username, String visa, String gender, String email, String passowrd, int weight, int height, double cyclingAverageSpeed, int userPoints) {
        this.username = username;
        this.visa = visa;
        this.gender = gender;
        this.email = email;
        this.password = passowrd;
        this.weight = weight;
        this.height = height;
        this.cyclingAverageSpeed = cyclingAverageSpeed;
        this.status = Utils.USER_PERMISSION;
        this.userPoints = userPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getCyclingAverageSpeed() {
        return cyclingAverageSpeed;
    }

    public void setCyclingAverageSpeed(double cyclingAverageSpeed) {
        this.cyclingAverageSpeed = cyclingAverageSpeed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserPoints(){
        return userPoints;
    }

    public void setUserPoints(int userPoints){
        this.userPoints = userPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  visa == user.visa &&
                weight == user.weight &&
                height == user.height &&
                Double.compare(user.cyclingAverageSpeed, cyclingAverageSpeed) == 0 &&
                status == user.status &&
                username.equals(user.username) &&
                gender.equals(user.gender) &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, visa, gender, email, password, weight, height, cyclingAverageSpeed, status);
    }

    @Override
    public String toString() {
        return "User\n" +
                "Username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", cyclingAverageSpeed=" + cyclingAverageSpeed +"\n";
    }
}
