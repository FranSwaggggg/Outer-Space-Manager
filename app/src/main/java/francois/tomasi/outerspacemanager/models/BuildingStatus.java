package francois.tomasi.outerspacemanager.models;

import java.util.UUID;

public class BuildingStatus {

    private UUID id;
    private String buildingId;
    private String building;
    private String dateConstruction;

    public UUID getId() { return id; }

    public String getBuildingId() { return buildingId; }

    public String getBuilding() { return building; }

    public String getDateConstruction() { return dateConstruction; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setDateConstruction(String dateConstruction) {
        this.dateConstruction = dateConstruction;
    }
}
