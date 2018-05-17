package francois.tomasi.outerspacemanager.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Ship;
import francois.tomasi.outerspacemanager.responses.GetShipResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.format;

public class FragmentShipDetail extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private LinearLayout layoutShipDetail;
    private LinearLayout layoutShipInfos;

    private ProgressBar progressBarShipDetail;
    private ProgressBar progressBarShipInfos;

    private ImageView imageViewShip;

    private TextView textViewShipName;

    private TextView textViewMinAttack;
    private TextView textViewMaxAttack;

    private TextView textViewSpeed;
    private TextView textViewLife;
    private TextView textViewShield;
    private TextView textViewCapacity;

    private TextView textViewBuildingTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_detail, container);

        layoutShipDetail = v.findViewById(R.id.layoutShipDetail);
        layoutShipInfos = v.findViewById(R.id.layoutShipInfos);

        progressBarShipDetail = v.findViewById(R.id.progressBarShipDetail);
        progressBarShipInfos = v.findViewById(R.id.progressBarShipInfos);

        imageViewShip = v.findViewById(R.id.imageViewShip);

        textViewShipName = v.findViewById(R.id.textViewShipName);

        textViewMinAttack = v.findViewById(R.id.textViewMinAttack);
        textViewMaxAttack = v.findViewById(R.id.textViewMaxAttack);

        textViewSpeed = v.findViewById(R.id.textViewSpeed);
        textViewLife = v.findViewById(R.id.textViewLife);
        textViewShield = v.findViewById(R.id.textViewShield);
        textViewCapacity = v.findViewById(R.id.textViewCapacity);

        textViewBuildingTime = v.findViewById(R.id.textViewBuildingTime);

        layoutShipDetail.setVisibility(View.INVISIBLE);
        layoutShipInfos.setVisibility(View.GONE);

        return v;
    }

    public void replaceShip(final int shipId) {

        progressBarShipDetail.setVisibility(View.VISIBLE);
        layoutShipDetail.setVisibility(View.INVISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetShipResponse> request = service.getShip(token, shipId);

        request.enqueue(new Callback<GetShipResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetShipResponse> call,
                                   @NonNull Response<GetShipResponse> response) {
                if (response.code() == 200) {
                    final Ship ship = new Ship(response.body());

                    setData(ship);
                    layoutShipDetail.setVisibility(View.VISIBLE);
                    layoutShipInfos.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetShipResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(layoutShipDetail, getString(R.string.network_error));
            }
        });
    }

    protected void setData(final Ship ship) {

        Locale locale = getResources().getConfiguration().locale;

        progressBarShipDetail.setVisibility(View.INVISIBLE);
        progressBarShipInfos.setVisibility(View.INVISIBLE);

        /*int resID = getResources().getIdentifier("search_" + ship.getName().toLowerCase().replace(' ', '_') ,
                "drawable", getActivity().getPackageName());
        imageViewShip.setImageResource(resID);*/

        textViewShipName.setText(ship.getName());

        textViewMinAttack.setText(format(locale, "%,d", ship.getMinAttack()));
        textViewMaxAttack.setText(format(locale, "%,d", ship.getMaxAttack()));

        textViewSpeed.setText(format(locale, "%,d", ship.getSpeed()));
        textViewLife.setText(format(locale, "%,d", ship.getLife()));
        textViewShield.setText(format(locale, "%,d", ship.getShield()));
        textViewCapacity.setText(format(locale, "%,d", ship.getCapacity()));

        SimpleDateFormat formatter = new SimpleDateFormat("mm'm'ss's'", locale);
        String timeToBuildString = formatter.format(new Date(ship.getTimeToBuild() * 1000L));

        textViewBuildingTime.setText(String.valueOf(timeToBuildString));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            getActivity().finish();
    }
}
