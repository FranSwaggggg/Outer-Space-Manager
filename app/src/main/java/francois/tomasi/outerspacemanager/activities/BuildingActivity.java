package francois.tomasi.outerspacemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.adapters.BuildingAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuildingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ApiService service = ApiServiceFactory.create();

    ListView listViewBuildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        listViewBuildings = findViewById(R.id.listViewBuildings);
        listViewBuildings.setOnItemClickListener(this);

        String token = SharedPreferencesHelper.getToken(getApplicationContext());

        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBuildingsResponse> call, @NonNull Response<GetBuildingsResponse> response) {

                final BuildingAdapter adapter = new BuildingAdapter(BuildingActivity.this, response.body().getBuildings());
                listViewBuildings.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(BuildingActivity.this, BuildingDetailActivity.class);
        startActivity(intent);
    }
}
