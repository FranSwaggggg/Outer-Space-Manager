package francois.tomasi.outerspacemanager.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.database.DAOBuildingStatus;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.responses.UpgradeBuildingResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.format;

public class FragmentBuildingDetail extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private LinearLayout layoutBuildingDetail;
    private LinearLayout layoutBuildingInfos;
    private LinearLayout layoutBuildingInProgress;

    private ProgressBar progressBarBuildingDetail;
    private ProgressBar progressBarBuildingInfos;

    private ImageView imageViewBuilding;
    private Button btnUpgradeBuilding;

    private TextView textViewBuildingName;
    private TextView textViewBuildingLevel;


    private TextView textViewMineralCost;
    private TextView textViewGasCost;

    private ImageView imageViewEffect;
    private TextView textViewEffectValueNow;
    private TextView textViewEffectValueAfterUpgrade;

    private TextView textViewBuildingTime;

    private TextView textViewNotEnoughtRessources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building_detail, container);

        layoutBuildingDetail = v.findViewById(R.id.layoutBuildingDetail);
        layoutBuildingInfos = v.findViewById(R.id.layoutBuildingInfos);
        layoutBuildingInProgress = v.findViewById(R.id.layoutBuildingInProgress);

        progressBarBuildingDetail = v.findViewById(R.id.progressBarBuildingDetail);
        progressBarBuildingInfos = v.findViewById(R.id.progressBarBuildingInfos);

        imageViewBuilding = v.findViewById(R.id.imageViewBuilding);
        btnUpgradeBuilding = v.findViewById(R.id.btnUpgradeBuilding);

        textViewBuildingName = v.findViewById(R.id.textViewBuildingName);
        textViewBuildingLevel = v.findViewById(R.id.textViewSearchLevel);

        textViewMineralCost = v.findViewById(R.id.textViewMineralCost);
        textViewGasCost = v.findViewById(R.id.textViewGasCost);

        imageViewEffect = v.findViewById(R.id.imageViewEffect);
        textViewEffectValueNow = v.findViewById(R.id.textViewEffectValueNow);
        textViewEffectValueAfterUpgrade = v.findViewById(R.id.textViewEffectValueAfterUpgrade);

        textViewBuildingTime = v.findViewById(R.id.textViewBuildingTime);

        textViewNotEnoughtRessources = v.findViewById(R.id.textViewNotEnoughtRessources);

        layoutBuildingDetail.setVisibility(View.INVISIBLE);
        layoutBuildingInfos.setVisibility(View.GONE);
        layoutBuildingInProgress.setVisibility(View.GONE);
        textViewNotEnoughtRessources.setVisibility(View.GONE);
        btnUpgradeBuilding.setEnabled(false);

        return v;
    }

    public void replaceBuilding(final int buildingId) {

        progressBarBuildingDetail.setVisibility(View.VISIBLE);
        layoutBuildingDetail.setVisibility(View.INVISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBuildingsResponse> call,
                                   @NonNull Response<GetBuildingsResponse> response) {
                if (response.code() == 200) {
                    final Building building = response.body().getBuildings().get(buildingId);

                    setData(building);
                    layoutBuildingDetail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.network_error));
            }
        });
    }

    protected void setData(final Building building) {

        Locale locale = getResources().getConfiguration().locale;

        progressBarBuildingDetail.setVisibility(View.INVISIBLE);
        progressBarBuildingInfos.setVisibility(View.VISIBLE);

        Glide.with(getContext()).load(building.getImageUrl()).into(imageViewBuilding);
        textViewBuildingName.setText(building.getName());
        textViewBuildingLevel.setText(String.valueOf("Niveau " + building.getLevel()));

        final int mineralCost = building.getMineralCostLevel0() +
                (building.getMineralCostByLevel() * building.getLevel());
        final int gasCost = building.getGasCostLevel0() +
                (building.getGasCostByLevel() * building.getLevel());

        textViewMineralCost.setText(format(locale, "%,d", mineralCost));
        textViewGasCost.setText(format(locale, "%,d", gasCost));

        int resID = getResources().getIdentifier("ic_" + building.getEffect() ,
                "drawable", getActivity().getPackageName());
        imageViewEffect.setImageResource(resID);

        int effectValueNow = building.getAmountOfEffectLevel0() +
                (building.getAmountOfEffectByLevel() * building.getLevel());
        textViewEffectValueNow.setText(format(locale, "%,d", effectValueNow));

        int effectValueAfterUpgrade = building.getAmountOfEffectLevel0() +
                (building.getAmountOfEffectByLevel() * (building.getLevel() + 1));
        textViewEffectValueAfterUpgrade.setText(format(locale, "%,d", effectValueAfterUpgrade));

        int timeToBuild = (building.getTimeToBuildLevel0() +
                (building.getTimeToBuildByLevel() * building.getLevel()));

        SimpleDateFormat formatter = new SimpleDateFormat("mm'm'ss's'", locale);
        String timeToBuildString = formatter.format(new Date(timeToBuild * 1000L));

        textViewBuildingTime.setText(String.valueOf(timeToBuildString));

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call,
                                   @NonNull Response<GetUserResponse> response) {
                final User user = new User(response.body());

                if (mineralCost > user.getMinerals() || gasCost > user.getGas() || building.isBuilding()) {

                    btnUpgradeBuilding.setEnabled(false);
                    layoutBuildingInfos.setVisibility(View.GONE);

                    if (building.isBuilding()) {
                        layoutBuildingInProgress.setVisibility(View.VISIBLE);
                    } else {
                        textViewNotEnoughtRessources.setText(R.string.not_enought_ressources);
                        textViewNotEnoughtRessources.setVisibility(View.VISIBLE);
                    }
                } else {
                    btnUpgradeBuilding.setEnabled(true);
                    layoutBuildingInfos.setVisibility(View.VISIBLE);
                }

                progressBarBuildingInfos.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.network_error));
            }
        });

        btnUpgradeBuilding.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String token = SharedPreferencesHelper.getToken(getContext());
                    Call<UpgradeBuildingResponse> request = service.upgradeBuilding(token, building.getBuildingId());

                    request.enqueue(new Callback<UpgradeBuildingResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UpgradeBuildingResponse> call,
                                               @NonNull Response<UpgradeBuildingResponse> response) {
                            switch (response.code()) {
                                case 200:
                                    DAOBuildingStatus daoBuildingStatus = new DAOBuildingStatus(getContext());
                                    daoBuildingStatus.open();
                                    int currentTime = (int) (new Date().getTime() / 1000);
                                    daoBuildingStatus.createBuildingStatus(building.getBuildingId(), "true", String.valueOf(currentTime));

                                    layoutBuildingInfos.setVisibility(View.INVISIBLE);
                                    layoutBuildingInProgress.setVisibility(View.VISIBLE);
                                    btnUpgradeBuilding.setEnabled(false);
                                    SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.imrovement_started));
                                    break;
                                case 401:
                                    if (response.message().equals("already_in_queue"))
                                        SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.building_already_in_queue));
                                    else if (response.message().equals("not_enough_resources"))
                                        SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.not_enought_ressources));
                                    break;
                                case 403:
                                    SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.invalid_token));
                                    break;
                                case 404:
                                    SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.unknown_building));
                                    break;
                                case 500:
                                    SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.server_error));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UpgradeBuildingResponse> call, @NonNull Throwable t) {
                            SnackBarHelper.createSnackBar(layoutBuildingDetail, getString(R.string.network_error));
                        }
                    });
                }
            }
        );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            getActivity().finish();
    }
}
