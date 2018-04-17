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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

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
        final TextView txtViewUsername = findViewById(R.id.txtViewUsername);
        final TextView txtViewPoints = findViewById(R.id.txtViewPoints);
        final TextView txtGasValue = findViewById(R.id.txtGasValue);
        final TextView txtMineralsValue = findViewById(R.id.txtMineralsValue);
        final TextView txtGasModifierValue = findViewById(R.id.txtGasModifierValue);
        final TextView txtMineralsModifierValue = findViewById(R.id.txtMineralsModifierValue);
        final ProgressBar loaderUserInfos = findViewById(R.id.loaderUserInfos);
        loaderUserInfos.setVisibility(View.VISIBLE);

        final LinearLayout layoutUserInfos = findViewById(R.id.layoutUserInfos);
        layoutUserInfos.setVisibility(View.INVISIBLE);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
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
                SnackBarHelper.createSnackBar(findViewById(R.id.linearLayoutInfos), "Erreur réseau");
                loaderUserInfos.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
