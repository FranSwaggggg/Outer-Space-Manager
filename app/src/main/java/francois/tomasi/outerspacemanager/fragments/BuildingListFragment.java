package francois.tomasi.outerspacemanager.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.BuildingActivity;
import francois.tomasi.outerspacemanager.adapters.BuildingAdapter;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuildingListFragment extends Fragment {
    private ListView listViewBuildings;
    public ArrayList<Building> buildingList;

    private ApiService service = ApiServiceFactory.create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building_list,container);
        listViewBuildings = v.findViewById(R.id.listViewBuildings);
        return v;
    }

    private void getBuildings(){

        Call<GetBuildingsResponse> request = this.service.getBuildings(SharedPreferencesHelper.getToken(getContext()));

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if (response.code() == 200) {
                    GetBuildingsResponse data = response.body();

                    final BuildingAdapter adapter = new BuildingAdapter(getContext(), data.getBuildings());

                    listViewBuildings.setOnItemClickListener((BuildingActivity)getActivity());
                    listViewBuildings.setAdapter(adapter);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Impossible de récupérer la liste des bâtiments", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            // Network error
            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getBuildings();
    }
}
