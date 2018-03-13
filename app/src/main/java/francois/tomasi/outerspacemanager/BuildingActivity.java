package francois.tomasi.outerspacemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Objects;

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

                final BuildingAdapter adapter = new BuildingAdapter(BuildingActivity.this, response.body().getBuildings());

                adapter.setOnEventListener(new OnClickButtonListItem() {
                    @Override
                    public void OnClick(int id) {
                        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                        String token = settings.getString(Constants.TOKEN, "");

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.URL_API)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService service = retrofit.create(ApiService.class);
                        Call<UpgradeBuildingResponse> request = service.upgradeBuilding(token, id);

                        request.enqueue(new Callback<UpgradeBuildingResponse>() {
                            @Override
                            public void onResponse(Call<UpgradeBuildingResponse> call, Response<UpgradeBuildingResponse> response) {
                                UpgradeBuildingResponse data = response.body();
                                if (Objects.equals(data.getCode(), "200")) {
                                    Toast toast = Toast.makeText(getApplicationContext(), data.getCode() + data.getMessage(), Toast.LENGTH_LONG);
                                    toast.show();

                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), data.getCode() + data.getMessage(), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpgradeBuildingResponse> call, Throwable t) {

                            }
                        });
                    }
                });

                listViewBuildings.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {

            }
        });
    }
}
