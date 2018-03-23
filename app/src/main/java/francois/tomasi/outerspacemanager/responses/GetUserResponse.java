package francois.tomasi.outerspacemanager.responses;

public class GetUserResponse {
    private float gas;
    private int gasModifier;
    private float minerals;
    private int mineralsModifier;
    private int points;
    private String username;

    public GetUserResponse(float gas, int gasModifier, float minerals, int mineralsModifier, int points, String username) {
        this.gas = gas;
        this.gasModifier = gasModifier;
        this.minerals = minerals;
        this.mineralsModifier = mineralsModifier;
        this.points = points;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
}
