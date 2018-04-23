package francois.tomasi.outerspacemanager.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.BuildingActivity;
import francois.tomasi.outerspacemanager.adapters.BuildingAdapter;
import francois.tomasi.outerspacemanager.database.DAOBuilding;
import francois.tomasi.outerspacemanager.database.DAOBuildingStatus;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.models.BuildingStatus;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBuildingList extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private ProgressBar progressBarBuildings;
    private RecyclerView recyclerViewBuildings;
    private RecyclerView.Adapter adapter;

    private Date currentDate;
    private List<Building> listBuildings;
    private List<BuildingStatus> listBuildingsStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_building_list, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewBuildings = view.findViewById(R.id.recyclerViewBuildings);
        recyclerViewBuildings.setHasFixedSize(true);
        recyclerViewBuildings.setLayoutManager(layoutManager);
        recyclerViewBuildings.setVisibility(View.INVISIBLE);

        progressBarBuildings = view.findViewById(R.id.progressBarBuildings);
        progressBarBuildings.setVisibility(View.VISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBuildingsResponse> call, @NonNull Response<GetBuildingsResponse> response) {

                listBuildings = response.body().getBuildings();
                currentDate = Calendar.getInstance().getTime();

                DAOBuildingStatus daoBuildingStatus = new DAOBuildingStatus(getContext());
                Environment.getExternalStorageDirectory();
                daoBuildingStatus.open();
                listBuildingsStatus = daoBuildingStatus.getAllBuildingStatus();

                // open building DB
                DAOBuilding daoBuilding = new DAOBuilding(getContext());
                daoBuilding.open();
                // clear all buildings DB
                daoBuilding.deleteAllBuildings();

                for (Building building : listBuildings) {
                    // add building to DB
                    daoBuilding.createBuilding(building);

                    //Clear building construction in DB
                    for (BuildingStatus buildingStatus : listBuildingsStatus) {
                        // if building in database and construction is done
                        if (buildingStatus.getBuildingId() != null) {
                            if (Objects.equals(String.valueOf(building.getBuildingId()), buildingStatus.getBuildingId())) {
                                int currentTime = (int) (new Date().getTime() / 1000);
                                if (currentTime - Integer.parseInt(buildingStatus.getDateConstruction()) > building.getTimeToBuild(false)) {
                                    if (!daoBuildingStatus.deleteBuildingState(building.getBuildingId()))
                                        SnackBarHelper.createSnackBar(view, "Error when delete in database");
                                    listBuildingsStatus.remove(buildingStatus);
                                }
                            }
                        }
                    }
                }

                adapter = new BuildingAdapter((BuildingActivity) getActivity(), listBuildings);
                recyclerViewBuildings.setAdapter(adapter);

                recyclerViewBuildings.setVisibility(View.VISIBLE);
                progressBarBuildings.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutBuilding), getString(R.string.network_error));
                progressBarBuildings.setVisibility(View.GONE);
            }
        });
    }
}
