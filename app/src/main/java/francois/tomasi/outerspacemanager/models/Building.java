package francois.tomasi.outerspacemanager.models;

import java.util.Date;
import java.util.UUID;

public class Building {

    private UUID id;
    private Integer level;
    private Integer amountOfEffectByLevel;
    private Integer amountOfEffectLevel0;
    private Integer buildingId;
    private Boolean building;
    private String effect;
    private String effectAdded;
    private Integer gasCostByLevel;
    private Integer gasCostLevel0;
    private String imageUrl;
    private Integer mineralCostByLevel;
    private Integer mineralCostLevel0;
    private String name;
    private Integer timeToBuildByLevel;
    private Integer timeToBuildLevel0;

    public UUID getId() { return id; }

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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setAmountOfEffectByLevel(Integer amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public void setAmountOfEffectLevel0(Integer amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public void setIsBuilding(Boolean building) {
        this.building = building;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setEffectAdded(String effectAdded) {
        this.effectAdded = effectAdded;
    }

    public void setGasCostByLevel(Integer gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
    }

    public void setGasCostLevel0(Integer gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMineralCostByLevel(Integer mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public void setMineralCostLevel0(Integer mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeToBuildByLevel(Integer timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public void setTimeToBuildLevel0(Integer timeToBuildLevel0) {
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

    public Integer getBuildingTimeLeftWithoutEffects(String timeLaunched){
        int currentTime = (int) (new Date().getTime() / 1000);
        Integer timeBetween = currentTime - Integer.parseInt(timeLaunched);
        return getTimeToBuild(false) - timeBetween;
    }

    public Integer getCostMineral (Building building){
        double cost;
        if (building.getLevel() == 0)
            cost = building.getMineralCostLevel0();
        else
            cost = building.getMineralCostLevel0() + (building.getMineralCostByLevel() * building.getLevel());

        return (int) Math.round(cost);
    }

    public Integer getCostGas (Building building){
        double cost;
        if (building.getLevel() == 0)
            cost = building.getGasCostLevel0();
        else
            cost = building.getGasCostLevel0() + (building.getGasCostByLevel() * building.getLevel());

        return (int) Math.round(cost);
    }
}
