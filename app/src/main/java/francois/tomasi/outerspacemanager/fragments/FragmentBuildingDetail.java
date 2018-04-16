package francois.tomasi.outerspacemanager.fragments;

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
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Building;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.format;

public class FragmentBuildingDetail extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building_detail, container);

        v.findViewById(R.id.linearLayoutBuildingDetail).setVisibility(View.INVISIBLE);

        return v;
    }

    public void replaceBuilding(final int buildingId) {

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetBuildingsResponse> request = service.getBuildings(token);

        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBuildingsResponse> call,
                                   @NonNull Response<GetBuildingsResponse> response) {
                final Building building = response.body().getBuildings().get(buildingId);

                setData(getView(), building);
            }

            @Override
            public void onFailure(@NonNull Call<GetBuildingsResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(getView().findViewById(R.id.layoutBuildingDetail),
                        "Erreur réseau");
            }
        });
    }

    protected void setData(final View view, final Building building) {

        LinearLayout linearLayoutBuildingDetail = view.findViewById(R.id.linearLayoutBuildingDetail);

        ImageView imageViewBuilding = view.findViewById(R.id.imageViewBuilding);
        TextView textViewBuildingName = view.findViewById(R.id.textViewBuildingName);
        TextView textViewBuildingLevel = view.findViewById(R.id.textViewBuildingLevel);

        final LinearLayout layoutBuildingInfos = view.findViewById(R.id.layoutBuildingInfos);

        TextView textViewMineralCost = view.findViewById(R.id.textViewMineralCost);
        TextView textViewGasCost = view.findViewById(R.id.textViewGasCost);

        ImageView imageViewEffect = view.findViewById(R.id.imageViewEffect);
        TextView textViewEffectValueNow = view.findViewById(R.id.textViewEffectValueNow);
        TextView textViewEffectValueAfterUpgrade = view.findViewById(R.id.textViewEffectValueAfterUpgrade);

        TextView textViewBuildingTime = view.findViewById(R.id.textViewBuildingTime);

        final Button btnUpgradeBuilding = view.findViewById(R.id.btnUpgradeBuilding);
        final ProgressBar progressBarUpgradeBuilding = view.findViewById(R.id.progressBarUpgradeBuilding);

        final int mineralCost = building.getMineralCostLevel0() +
                (building.getMineralCostByLevel() * building.getLevel());
        final int gasCost = building.getGasCostLevel0() +
                (building.getGasCostByLevel() * building.getLevel());

        linearLayoutBuildingDetail.setVisibility(View.VISIBLE);
        layoutBuildingInfos.setVisibility(View.GONE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call,
                                   @NonNull Response<GetUserResponse> response) {
                final User user = new User(response.body());

                if ((mineralCost < user.getMinerals()) && (gasCost < user.getGas())) {
                    progressBarUpgradeBuilding.setVisibility(View.GONE);
                    btnUpgradeBuilding.setVisibility(View.VISIBLE);
                    if (!building.isBuilding()) {
                        layoutBuildingInfos.setVisibility(View.VISIBLE);
                        btnUpgradeBuilding.setEnabled(true);
                    } else {
                        layoutBuildingInfos.setVisibility(View.GONE);
                        btnUpgradeBuilding.setEnabled(false);
                    }
                } else {
                    btnUpgradeBuilding.setEnabled(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutBuildingDetail),
                        "Erreur réseau");
            }
        });

        Glide.with(getContext()).load(building.getImageUrl()).into(imageViewBuilding);
        textViewBuildingName.setText(building.getName());
        textViewBuildingLevel.setText(String.valueOf("Niveau " + building.getLevel()));

        Locale locale = getContext().getResources().getConfiguration().locale;

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

        SimpleDateFormat formatter = new SimpleDateFormat("mm'm'ss's'");
        String timeToBuildString = formatter.format(new Date(timeToBuild * 1000L));

        textViewBuildingTime.setText(String.valueOf(timeToBuildString));

        /*btnUpgradeBuilding.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String token = SharedPreferencesHelper.getToken(getContext());

                    Call<UpgradeBuildingResponse> request = service.upgradeBuilding(token, building.getBuildingId());

                    request.enqueue(new Callback<UpgradeBuildingResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UpgradeBuildingResponse> call, @NonNull Response<UpgradeBuildingResponse> response) {
                            recreate();
                        }

                        @Override
                        public void onFailure(@NonNull Call<UpgradeBuildingResponse> call, @NonNull Throwable t) {
                            SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutBuildingDetail), "Erreur réseau");
                        }
                    });
                }
            }
        );*/
    }
}
