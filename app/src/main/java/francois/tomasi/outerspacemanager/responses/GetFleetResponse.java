package francois.tomasi.outerspacemanager.responses;

import java.util.List;

import francois.tomasi.outerspacemanager.models.Ship;

public class GetFleetResponse {
    private int size;
    private List<Ship> ships;

    public GetFleetResponse(int size, List<Ship> ships) {
        this.size = size;
        this.ships = ships;
    }

    public int getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
