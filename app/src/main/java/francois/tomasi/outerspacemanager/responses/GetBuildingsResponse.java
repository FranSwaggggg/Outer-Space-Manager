package francois.tomasi.outerspacemanager.responses;

import java.util.List;

import francois.tomasi.outerspacemanager.models.Building;

public class GetBuildingsResponse {
    private int size;
    private List<Building> buildings;

    public GetBuildingsResponse(int size, List<Building> buildings) {
        this.size = size;
        this.buildings = buildings;
    }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    public List<Building> getBuildings() { return buildings; }

    public void setBuildings(List<Building> buildings) { this.buildings = buildings; }
}