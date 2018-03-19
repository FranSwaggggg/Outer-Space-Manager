package francois.tomasi.outerspacemanager.responses;

import java.util.ArrayList;

import francois.tomasi.outerspacemanager.models.Building;

public class GetBuildingsResponse {
    private int size;
    private ArrayList<Building> buildings;

    public GetBuildingsResponse(int size, ArrayList<Building> buildings) {
        this.size = size;
        this.buildings = buildings;
    }

    public int getSize() { return size; }

    public ArrayList<Building> getBuildings() { return buildings; }
}
