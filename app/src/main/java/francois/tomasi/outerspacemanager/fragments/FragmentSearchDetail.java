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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.database.DAOSearchStatus;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Search;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.GetSearchesResponse;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.responses.UpgradeSearchResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.format;

public class FragmentSearchDetail extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private LinearLayout layoutSearchDetail;
    private LinearLayout layoutSearchInfos;
    private LinearLayout layoutBuildingInProgress;

    private ProgressBar progressBarSearchDetail;
    private ProgressBar progressBarSearchInfos;

    private ImageView imageViewSearch;
    private Button btnUpgradeSearch;

    private TextView textViewSearchName;
    private TextView textViewSearchLevel;


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
        View v = inflater.inflate(R.layout.fragment_search_detail, container);

        layoutSearchDetail = v.findViewById(R.id.layoutSearchDetail);
        layoutSearchInfos = v.findViewById(R.id.layoutSearchInfos);
        layoutBuildingInProgress = v.findViewById(R.id.layoutBuildingInProgress);

        progressBarSearchDetail = v.findViewById(R.id.progressBarSearchDetail);
        progressBarSearchInfos = v.findViewById(R.id.progressBarSearchInfos);

        imageViewSearch = v.findViewById(R.id.imageViewSearch);
        btnUpgradeSearch = v.findViewById(R.id.btnUpgradeSearch);

        textViewSearchName = v.findViewById(R.id.textViewSearchName);
        textViewSearchLevel = v.findViewById(R.id.textViewSearchLevel);

        textViewMineralCost = v.findViewById(R.id.textViewMineralCost);
        textViewGasCost = v.findViewById(R.id.textViewGasCost);

        imageViewEffect = v.findViewById(R.id.imageViewEffect);
        textViewEffectValueNow = v.findViewById(R.id.textViewEffectValueNow);
        textViewEffectValueAfterUpgrade = v.findViewById(R.id.textViewEffectValueAfterUpgrade);

        textViewBuildingTime = v.findViewById(R.id.textViewBuildingTime);

        textViewNotEnoughtRessources = v.findViewById(R.id.textViewNotEnoughtRessources);

        layoutSearchDetail.setVisibility(View.INVISIBLE);
        layoutSearchInfos.setVisibility(View.GONE);
        layoutBuildingInProgress.setVisibility(View.GONE);
        textViewNotEnoughtRessources.setVisibility(View.GONE);
        btnUpgradeSearch.setEnabled(false);

        return v;
    }

    public void replaceSearch(final int searchId) {

        progressBarSearchDetail.setVisibility(View.VISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetSearchesResponse> request = service.getSearches(token);

        request.enqueue(new Callback<GetSearchesResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetSearchesResponse> call,
                                   @NonNull Response<GetSearchesResponse> response) {
                if (response.code() == 200) {
                    final Search search = response.body().getSearches().get(searchId);

                    layoutSearchDetail.setVisibility(View.VISIBLE);
                    setData(search);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSearchesResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.network_error));
            }
        });
    }

    protected void setData(final Search search) {

        Locale locale = getResources().getConfiguration().locale;

        progressBarSearchDetail.setVisibility(View.INVISIBLE);
        progressBarSearchInfos.setVisibility(View.VISIBLE);

        int resID = getResources().getIdentifier("search_" + search.getName().toLowerCase().replace(' ', '_') ,
                "drawable", getActivity().getPackageName());
        imageViewSearch.setImageResource(resID);

        textViewSearchName.setText(search.getName());
        textViewSearchLevel.setText(String.valueOf("Niveau " + search.getLevel()));

        final int mineralCost = search.getMineralCostLevel0() +
                (search.getMineralCostByLevel() * search.getLevel());
        final int gasCost = search.getGasCostLevel0() +
                (search.getGasCostByLevel() * search.getLevel());

        textViewMineralCost.setText(format(locale, "%,d", mineralCost));
        textViewGasCost.setText(format(locale, "%,d", gasCost));

        resID = getResources().getIdentifier("ic_" + search.getEffect() ,
                "drawable", getActivity().getPackageName());
        imageViewEffect.setImageResource(resID);

        int effectValueNow = search.getAmountOfEffectLevel0() +
                (search.getAmountOfEffectByLevel() * search.getLevel());
        textViewEffectValueNow.setText(format(locale, "%,d", effectValueNow));

        int effectValueAfterUpgrade = search.getAmountOfEffectLevel0() +
                (search.getAmountOfEffectByLevel() * (search.getLevel() + 1));
        textViewEffectValueAfterUpgrade.setText(format(locale, "%,d", effectValueAfterUpgrade));

        int timeToBuild = (search.getTimeToBuildLevel0() +
                (search.getTimeToBuildByLevel() * search.getLevel()));

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

                if (mineralCost > user.getMinerals() || gasCost > user.getGas() || search.isBuilding()) {

                    btnUpgradeSearch.setEnabled(false);
                    layoutSearchInfos.setVisibility(View.GONE);

                    if (search.isBuilding()) {
                        layoutBuildingInProgress.setVisibility(View.VISIBLE);
                    } else {
                        textViewNotEnoughtRessources.setText(R.string.not_enought_ressources);
                        textViewNotEnoughtRessources.setVisibility(View.VISIBLE);
                    }
                } else {
                    btnUpgradeSearch.setEnabled(true);
                    layoutSearchInfos.setVisibility(View.VISIBLE);
                }

                progressBarSearchInfos.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.network_error));
            }
        });

        btnUpgradeSearch.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String token = SharedPreferencesHelper.getToken(getContext());
                    Call<UpgradeSearchResponse> request = service.upgradeSearch(token, search.getSearchId());

                    request.enqueue(new Callback<UpgradeSearchResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UpgradeSearchResponse> call,
                                               @NonNull Response<UpgradeSearchResponse> response) {
                            switch (response.code()) {
                                case 200:
                                    DAOSearchStatus daoSearchStatus = new DAOSearchStatus(getContext());
                                    daoSearchStatus.open();
                                    int currentTime = (int) (new Date().getTime() / 1000);
                                    daoSearchStatus.createSearchStatus(search.getSearchId(), "true", String.valueOf(currentTime));

                                    layoutSearchInfos.setVisibility(View.INVISIBLE);
                                    layoutBuildingInProgress.setVisibility(View.VISIBLE);
                                    btnUpgradeSearch.setEnabled(false);
                                    SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.imrovement_started));
                                    break;
                                case 401:
                                    if (response.message().equals("already_in_queue"))
                                        SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.building_already_in_queue));
                                    else if (response.message().equals("not_enough_resources"))
                                        SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.not_enought_ressources));
                                    break;
                                case 403:
                                    SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.invalid_token));
                                    break;
                                case 404:
                                    SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.unknown_search));
                                    break;
                                case 500:
                                    SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.server_error));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UpgradeSearchResponse> call, @NonNull Throwable t) {
                            SnackBarHelper.createSnackBar(layoutSearchDetail, getString(R.string.network_error));
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
