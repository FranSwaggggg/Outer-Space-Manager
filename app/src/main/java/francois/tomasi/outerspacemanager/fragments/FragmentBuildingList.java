package francois.tomasi.outerspacemanager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.BuildingActivity;
import francois.tomasi.outerspacemanager.adapters.BuildingAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
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
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_building_list, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getContext());

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
                adapter = new BuildingAdapter((BuildingActivity) getActivity(), response.body().getBuildings());
                recyclerViewBuildings.setAdapter(adapter);

                recyclerViewBuildings.setVisibility(View.VISIBLE);
                progressBarBuildings.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutBuilding), "Erreur réseau");
                progressBarBuildings.setVisibility(View.GONE);
            }
        });
    }
}
