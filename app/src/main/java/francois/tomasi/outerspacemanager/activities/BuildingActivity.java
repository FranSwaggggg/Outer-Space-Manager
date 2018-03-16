package francois.tomasi.outerspacemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.fragments.BuildingDetailFragment;
import francois.tomasi.outerspacemanager.fragments.BuildingListFragment;
import francois.tomasi.outerspacemanager.models.Building;

public class BuildingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static String SEARCH_EXTRA = "ship";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuildingListFragment buildingListFragment = (BuildingListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingList);
        BuildingDetailFragment buildingDetailFragment = (BuildingDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingDetail);

        Building building = buildingListFragment.buildingList.get(position);

        if (buildingDetailFragment == null || !buildingDetailFragment.isInLayout()) {
            Intent i = new Intent(getApplicationContext(), BuildingDetailActivity.class);

            Gson gson = new Gson();
            i.putExtra(BuildingActivity.SEARCH_EXTRA, gson.toJson(building));
            startActivity(i);
        } else {
            buildingDetailFragment.updateBuilding(building);
        }
    }

    /*
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
                                    Toast toast = Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG);
                                    toast.show();

                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuildingListFragment buildingListFragment = (BuildingListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingList);
        BuildingDetailFragment buildingDetailFragment = (BuildingDetailFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingDetail);

        if (buildingDetailFragment == null || !buildingDetailFragment.isInLayout()){
            Intent i = new Intent(getApplicationContext(), BuildingDetailActivity.class);
            startActivity(i);
        } else {
            // Si le fragment est déja chargé
            // buildingDetailFragment.fillTextView(buildingListFragment.listItems[position]);
        }
    }

    */
}
