package francois.tomasi.outerspacemanager;

import java.io.Serializable;

public class User implements Serializable {

    private final String email;
    private final String username;
    private final String password;

    private float gas;
    private int gasModifier;
    private float minerals;
    private int mineralsModifier;
    private int points;

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

    public void setGas(float gas) {
        this.gas = gas;
    }

    public int getGasModifier() {
        return gasModifier;
    }

    public void setGasModifier(int gasModifier) {
        this.gasModifier = gasModifier;
    }

    public float getMinerals() {
        return minerals;
    }

    public void setMinerals(float minerals) {
        this.minerals = minerals;
    }

    public int getMineralsModifier() {
        return mineralsModifier;
    }

    public void setMineralsModifier(int mineralsModifier) {
        this.mineralsModifier = mineralsModifier;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
