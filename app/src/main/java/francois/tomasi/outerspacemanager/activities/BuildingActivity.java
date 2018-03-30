package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.adapters.BuildingAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuildingActivity extends AppCompatActivity{

    private ApiService service = ApiServiceFactory.create();

    private ProgressBar progressBarBuildings;
    private RecyclerView recyclerViewBuildings;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        layoutManager = new LinearLayoutManager(this);

        progressBarBuildings = findViewById(R.id.progressBarBuildings);
        recyclerViewBuildings = findViewById(R.id.recyclerViewBuildings);
        recyclerViewBuildings.setHasFixedSize(true);
        recyclerViewBuildings.setLayoutManager(layoutManager);

        String token = SharedPreferencesHelper.getToken(getApplicationContext());

        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBuildingsResponse> call, @NonNull Response<GetBuildingsResponse> response) {
                adapter = new BuildingAdapter(response.body().getBuildings());
                recyclerViewBuildings.setAdapter(adapter);

                recyclerViewBuildings.setVisibility(View.VISIBLE);
                progressBarBuildings.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(findViewById(R.id.layoutBuilding), "Erreur réseau");
                progressBarBuildings.setVisibility(View.GONE);
            }
        });
    }
}
