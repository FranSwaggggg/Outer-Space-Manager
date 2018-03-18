package francois.tomasi.outerspacemanager.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.R;

public class BuildingDetailFragment extends Fragment {

    private Building building;
    private ListView listViewBuildings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building_detail, container);
        listViewBuildings = v.findViewById(R.id.listViewBuildings);

        // Binding

        return v;
    }

    public void updateBuilding(Building building) {
        this.building = building;
    }
}
