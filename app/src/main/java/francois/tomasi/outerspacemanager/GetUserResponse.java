package francois.tomasi.outerspacemanager;

class GetUserResponse {
    private float gas;
    private int gasModifier;
    private float minerals;
    private int mineralsModifier;
    private int points;
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
