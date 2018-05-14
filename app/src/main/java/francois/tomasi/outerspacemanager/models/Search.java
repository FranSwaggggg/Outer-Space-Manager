package francois.tomasi.outerspacemanager.models;

import java.util.UUID;

public class Search {

    private UUID id;
    private int amountOfEffectByLevel;
    private int amountOfEffectLevel0;
    private boolean building;
    private String effect;
    private int gasCostByLevel;
    private int gasCostLevel0;
    private int level;
    private int mineralCostByLevel;
    private int mineralCostLevel0;
    private String name;
    private int searchId;
    private int timeToBuildByLevel;
    private int timeToBuildLevel0;

    public UUID getId() { return id; }

    public int getAmountOfEffectByLevel() { return amountOfEffectByLevel; }

    public int getAmountOfEffectLevel0() { return amountOfEffectLevel0; }

    public boolean isBuilding() { return building; }

    public String getEffect() { return effect; }

    public int getGasCostByLevel() { return gasCostByLevel; }

    public int getGasCostLevel0() { return gasCostLevel0; }

    public int getLevel() { return level; }

    public int getMineralCostByLevel() { return mineralCostByLevel; }

    public int getMineralCostLevel0() { return mineralCostLevel0; }

    public String getName() { return name; }

    public int getSearchId() { return searchId; }

    public int getTimeToBuildByLevel() { return timeToBuildByLevel; }

    public int getTimeToBuildLevel0() { return timeToBuildLevel0; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAmountOfEffectByLevel(int amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public void setAmountOfEffectLevel0(int amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
    }

    public void setIsBuilding(boolean building) {
        this.building = building;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setGasCostByLevel(int gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
    }

    public void setGasCostLevel0(int gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMineralCostByLevel(int mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public void setMineralCostLevel0(int mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public void setTimeToBuildByLevel(int timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public void setTimeToBuildLevel0(int timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    // ============== CUSTOM ==============

    public Integer getTimeToBuild(boolean isItForDatabase) {
        Integer timeWithoutSpeedBuilding;

        if (!isItForDatabase)
            timeWithoutSpeedBuilding = timeToBuildLevel0 + level * timeToBuildByLevel;
        else
            timeWithoutSpeedBuilding =  timeToBuildLevel0 + (level - 1) * timeToBuildByLevel;

        return timeWithoutSpeedBuilding;
    }
}
