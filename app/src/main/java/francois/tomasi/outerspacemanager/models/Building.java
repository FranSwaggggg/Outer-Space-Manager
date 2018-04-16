package francois.tomasi.outerspacemanager.models;

public class Building {
    private int level;
    private int amountOfEffectByLevel;
    private int amountOfEffectLevel0;
    private int buildingId;
    private boolean building;
    private String effect;
    private String effectAdded;
    private int gasCostByLevel;
    private int gasCostLevel0;
    private String imageUrl;
    private int mineralCostByLevel;
    private int mineralCostLevel0;
    private String name;
    private int timeToBuildByLevel;
    private int timeToBuildLevel0;

    Building(int level, int amountOfEffectByLevel, int amountOfEffectLevel0, int buildingId,
             boolean building, String effect, String effectAdded, int gasCostByLevel, int gasCostLevel0,
             String imageUrl, int mineralCostByLevel, int mineralCostLevel0, String name,
             int timeToBuildByLevel, int timeToBuildLevel0) {
        this.level = level;
        this.amountOfEffectByLevel = amountOfEffectByLevel;
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
        this.buildingId = buildingId;
        this.building = building;
        this.effect = effect;
        this.effectAdded = effectAdded;
        this.gasCostByLevel = gasCostByLevel;
        this.gasCostLevel0 = gasCostLevel0;
        this.imageUrl = imageUrl;
        this.mineralCostByLevel = mineralCostByLevel;
        this.mineralCostLevel0 = mineralCostLevel0;
        this.name = name;
        this.timeToBuildByLevel = timeToBuildByLevel;
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    public int getLevel() { return level; }

    public int getAmountOfEffectByLevel() { return amountOfEffectByLevel; }

    public int getAmountOfEffectLevel0() { return amountOfEffectLevel0; }

    public int getBuildingId() { return buildingId; }

    public boolean isBuilding() { return building; }

    public String getEffect() { return effect == null ? effectAdded : effect; }

    public int getGasCostByLevel() { return gasCostByLevel; }

    public int getGasCostLevel0() { return gasCostLevel0; }

    public String getImageUrl() { return imageUrl; }

    public int getMineralCostByLevel() { return mineralCostByLevel; }

    public int getMineralCostLevel0() { return mineralCostLevel0; }

    public String getName() { return name; }

    public int getTimeToBuildByLevel() { return timeToBuildByLevel; }

    public int getTimeToBuildLevel0() { return timeToBuildLevel0; }
}
