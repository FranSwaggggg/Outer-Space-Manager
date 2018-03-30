package francois.tomasi.outerspacemanager.models;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String username;
    private String password;

    private float gas;
    private int gasModifier;
    private float minerals;
    private int mineralsModifier;
    private int points;

    private String imageUrl;

    public User(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.email = null;
        this.username = username;
        this.password = password;
    }

    public User(User user, float gas, int gasModifier, float minerals, int mineralsModifier, int points) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.gas = gas;
        this.gasModifier = gasModifier;
        this.minerals = minerals;
        this.mineralsModifier = mineralsModifier;
        this.points = points;
    }

    public User(String username, int points, String imageUrl) {
        this.username = username;
        this.points = points;
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public float getGas() {
        return gas;
    }

    public int getGasModifier() {
        return gasModifier;
    }

    public float getMinerals() {
        return minerals;
    }

    public int getMineralsModifier() {
        return mineralsModifier;
    }

    public int getPoints() {
        return points;
    }

    public String getImageUrl() { return imageUrl; }
}
