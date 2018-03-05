package francois.tomasi.outerspacemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        final ListView listViewBuildings = findViewById(R.id.listViewBuildings);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String token = settings.getString(Constants.TOKEN, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                BuildingAdapter adapter = new BuildingAdapter(BuildingActivity.this, response.body().getBuildings());
                listViewBuildings.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

            }
        });
    }
}
