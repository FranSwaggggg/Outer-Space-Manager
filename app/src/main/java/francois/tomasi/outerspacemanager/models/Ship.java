package francois.tomasi.outerspacemanager.models;

import francois.tomasi.outerspacemanager.responses.GetShipResponse;

public class Ship {

    private int amount;
    private int capacity;
    private int gasCost;
    private int life;
    private int maxAttack;
    private int minAttack;
    private int mineralCost;
    private String name;
    private int shipId;
    private int shield;
    private int spatioportLevelNeeded;
    private int speed;
    private int timeToBuild;

    public Ship(int amount, int capacity, int gasCost, int life, int maxAttack, int minAttack, int mineralCost, String name, int shipId, int shield, int spatioportLevelNeeded, int speed, int timeToBuild) {
        this.amount = amount;
        this.capacity = capacity;
        this.gasCost = gasCost;
        this.life = life;
        this.maxAttack = maxAttack;
        this.minAttack = minAttack;
        this.mineralCost = mineralCost;
        this.name = name;
        this.shipId = shipId;
        this.shield = shield;
        this.spatioportLevelNeeded = spatioportLevelNeeded;
        this.speed = speed;
        this.timeToBuild = timeToBuild;
    }

    public Ship(GetShipResponse s) {
        this.capacity = s.getCapacity();
        this.gasCost = s.getGasCost();
        this.life = s.getLife();
        this.maxAttack = s.getMaxAttack();
        this.minAttack = s.getMinAttack();
        this.mineralCost = s.getMineralCost();
        this.name = s.getName();
        this.shipId = s.getShipId();
        this.shield = s.getShield();
        this.spatioportLevelNeeded = s.getSpatioportLevelNeeded();
        this.speed = s.getSpeed();
        this.timeToBuild = s.getTimeToBuild();
    }

    public int getAmount() { return amount; }

    public int getCapacity() { return capacity; }

    public int getGasCost() { return gasCost; }

    public int getLife() { return life; }

    public int getMaxAttack() { return maxAttack; }

    public int getMinAttack() { return minAttack; }

    public int getMineralCost() { return mineralCost; }

    public String getName() { return name; }

    public int getShipId() { return shipId; }

    public int getShield() { return shield; }

    public int getSpatioportLevelNeeded() { return spatioportLevelNeeded; }

    public int getSpeed() { return speed; }

    public int getTimeToBuild() { return timeToBuild; }
}
