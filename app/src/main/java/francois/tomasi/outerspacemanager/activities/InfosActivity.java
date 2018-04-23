package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.StrictMath.round;
import static java.lang.String.format;

public class InfosActivity extends AppCompatActivity {

    private ApiService service = ApiServiceFactory.create();

    private TextView txtViewUsername;
    private TextView txtViewPoints;
    private TextView txtGasValue;
    private TextView txtMineralsValue;
    private TextView txtGasModifierValue;
    private TextView txtMineralsModifierValue;

    private ProgressBar loaderUserInfos;

    private LinearLayout layoutUserInfos;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        txtViewUsername = findViewById(R.id.txtViewUsername);
        txtViewPoints = findViewById(R.id.txtViewPoints);
        txtGasValue = findViewById(R.id.txtGasValue);
        txtMineralsValue = findViewById(R.id.txtMineralsValue);
        txtGasModifierValue = findViewById(R.id.txtGasModifierValue);
        txtMineralsModifierValue = findViewById(R.id.txtMineralsModifierValue);
        loaderUserInfos = findViewById(R.id.loaderUserInfos);
        layoutUserInfos = findViewById(R.id.layoutUserInfos);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() { setData(); }
            }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        setData();
    }

    protected void setData() {
        loaderUserInfos.setVisibility(View.VISIBLE);
        layoutUserInfos.setVisibility(View.INVISIBLE);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.darkGrey, getTheme()));

        String token = SharedPreferencesHelper.getToken(getApplicationContext());
        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call, @NonNull Response<GetUserResponse> response) {
                final User data = new User(response.body());
                Locale locale = getApplicationContext().getResources().getConfiguration().locale;

                txtViewUsername.setText(data.getUsername());
                txtViewPoints.setText(String.valueOf(format(locale,"%,d", round(data.getPoints())) + " pts"));
                txtGasValue.setText(format(locale,"%,d", round(data.getGas())));
                txtMineralsValue.setText(format(locale,"%,d", round(data.getMinerals())));
                txtGasModifierValue.setText(format(locale,"%,d", round(data.getGasModifier())));
                txtMineralsModifierValue.setText(format(locale,"%,d", round(data.getMineralsModifier())));

                loaderUserInfos.setVisibility(View.GONE);
                layoutUserInfos.setVisibility(View.VISIBLE);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(findViewById(R.id.linearLayoutInfos), getString(R.string.network_error));
                loaderUserInfos.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
