package francois.tomasi.outerspacemanager;

class Building {
    private int level;
    private int amountOfEffectByLevel;
    private int amountOfEffectLevel0;
    private int buildingId;
    private boolean building;
    private String effect;
    private int gasCostByLevel;
    private int gasCostLevel0;
    private String imageUrl;
    private int mineralCostByLevel;
    private int mineralCostLevel0;
    private String name;
    private int timeToBuildByLevel;
    private int timeToBuildLevel0;

    Building(int level, int amountOfEffectByLevel, int amountOfEffectLevel0, int buildingId,
             boolean building, String effect, int gasCostByLevel, int gasCostLevel0,
             String imageUrl, int mineralCostByLevel, int mineralCostLevel0, String name,
             int timeToBuildByLevel, int timeToBuildLevel0) {
        this.level = level;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.buildingId = buildingId;
        this.building = building;
        this.effect = effect;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.imageUrl = imageUrl;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.name = name;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public void setAmountOfEffectByLevel(int amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public int getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public void setAmountOfEffectLevel0(int amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getGasCostByLevel() {
        return gasCostByLevel;
    }

    public void setGasCostByLevel(int gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
    }

    public int getGasCostLevel0() {
        return gasCostLevel0;
    }

    public void setGasCostLevel0(int gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public void setMineralCostByLevel(int mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public int getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public void setMineralCostLevel0(int mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public void setTimeToBuildByLevel(int timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public int getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }

    public void setTimeToBuildLevel0(int timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }
}
