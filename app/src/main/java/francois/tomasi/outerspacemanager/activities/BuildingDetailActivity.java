package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;

public class BuildingDetailActivity extends AppCompatActivity {

    private Building building;
    private ApiService service = ApiServiceFactory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();

        building = new Gson().fromJson(getIntent().getSerializableExtra("building").toString(), Building.class);
        setData();
    }

    protected void setData() {
        final ImageView imageViewBuilding = findViewById(R.id.imageViewBuilding);
        final TextView textViewBuildingName = findViewById(R.id.textViewBuildingName);
        final TextView textViewBuildingLevel = findViewById(R.id.textViewBuildingLevel);

        final TextView textViewMineralCost = findViewById(R.id.textViewMineralCost);
        final TextView textViewGasCost = findViewById(R.id.textViewGasCost);

        final ImageView imageViewEffect = findViewById(R.id.imageViewEffect);
        final TextView textViewEffectValueNow = findViewById(R.id.textViewEffectValueNow);
        final TextView textViewEffectValueAfterUpgrade = findViewById(R.id.textViewEffectValueAfterUpgrade);
        //final ProgressBar loaderBuildingDetail = findViewById(R.id.loaderBuildingDetail);

        Glide.with(getApplicationContext()).load(building.getImageUrl()).into(imageViewBuilding);
        textViewBuildingName.setText(building.getName());
        textViewBuildingLevel.setText("Niveau " + building.getLevel());

        int mineralCost = building.getMineralCostLevel0() + (building.getMineralCostByLevel() * building.getLevel());
        int gasCost = building.getGasCostLevel0() + (building.getGasCostByLevel() * building.getLevel());

        textViewMineralCost.setText(String.valueOf(mineralCost));
        textViewGasCost.setText(String.valueOf(gasCost));

        int resID = getResources().getIdentifier("ic_" + building.getEffect() , "drawable", getPackageName());
        imageViewEffect.setImageResource(resID);

        int effectValueNow = building.getAmountOfEffectLevel0() + (building.getAmountOfEffectByLevel() * building.getLevel());
        textViewEffectValueNow.setText(String.valueOf(effectValueNow));

        int effectValueAfterUpgrade = building.getAmountOfEffectLevel0() + (building.getAmountOfEffectByLevel() * (building.getLevel() + 1));
        textViewEffectValueAfterUpgrade.setText(String.valueOf(effectValueAfterUpgrade));

        /*
        String token = SharedPreferencesHelper.getToken(getApplicationContext());

        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call, @NonNull Response<GetUserResponse> response) {
                final GetUserResponse data = response.body();

                txtGasValue.setText(format(getApplicationContext().getResources().getConfiguration().locale,
                        "%,d", round(data.getGas())));
                txtMineralsValue.setText(format(getApplicationContext().getResources().getConfiguration().locale,
                        "%,d", round(data.getMinerals())));
                txtGasModifierValue.setText(format(getApplicationContext().getResources().getConfiguration().locale,
                        "%,d", round(data.getGasModifier())));
                txtMineralsModifierValue.setText(format(getApplicationContext().getResources().getConfiguration().locale,
                        "%,d", round(data.getMineralsModifier())));

                loaderUserInfos.setVisibility(View.GONE);
                layoutUserInfos.setVisibility(View.VISIBLE);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(findViewById(R.id.linearLayoutInfos), "Erreur réseau");
                loaderUserInfos.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        */
    }
}
