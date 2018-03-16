package francois.tomasi.outerspacemanager.responses;

import java.util.List;

import francois.tomasi.outerspacemanager.models.Ship;

public class GetShipsResponse {
    private Double currentUserMinerals;
    private Double currentUserGas;
    private int size;
    private List<Ship> ships;

    public GetShipsResponse(Double currentUserMinerals, Double currentUserGas, int size, List<Ship> ships) {
        this.currentUserMinerals = currentUserMinerals;
        this.currentUserGas = currentUserGas;
        this.size = size;
        this.ships = ships;
    }

    public Double getCurrentUserMinerals() {
        return currentUserMinerals;
    }

    public Double getCurrentUserGas() {
        return currentUserGas;
    }

    public int getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
