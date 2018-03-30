package francois.tomasi.outerspacemanager.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.StrictMath.round;
import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private ApiService service = ApiServiceFactory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnInfos = findViewById(R.id.btnInfos);
        final Button btnBuildings = findViewById(R.id.btnBuildings);
        final Button btnFleet = findViewById(R.id.btnFleet);
        final Button btnResearch = findViewById(R.id.btnResearch);
        final Button btnShipyard = findViewById(R.id.btnShipyard);
        final Button btnGalaxy = findViewById(R.id.btnGalaxy);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() { setData(); }
            }
        );

        btnInfos.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, InfosActivity.class);
                    startActivity(intent);
                }
            }
        );

        btnBuildings.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BuildingActivity.class);
                    startActivity(intent);
                }
            }
        );

        btnFleet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, FleetActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btnGalaxy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, GalaxyActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        setData();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    SharedPreferencesHelper.clearToken(getApplicationContext());
                    SharedPreferencesHelper.clearExpires(getApplicationContext());

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // Button No clicked
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes-vous sur de vouloir vous déconnecter ?")
                .setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    protected void setData() {
        final TextView txtGasValue = findViewById(R.id.txtGasValue);
        final TextView txtMineralsValue = findViewById(R.id.txtMineralsValue);
        final ProgressBar loaderUserInfos = findViewById(R.id.loaderUserInfos);

        final LinearLayout layoutUserInfos = findViewById(R.id.layoutUserInfos);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.darkGrey, getTheme()));

        String token = SharedPreferencesHelper.getToken(getApplicationContext());

        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call, @NonNull Response<GetUserResponse> response) {
                final GetUserResponse data = response.body();

                Locale locale = getApplicationContext().getResources().getConfiguration().locale;

                txtGasValue.setText(format(locale,"%,d", round(data.getGas())));
                txtMineralsValue.setText(format(locale,"%,d", round(data.getMinerals())));

                loaderUserInfos.setVisibility(View.GONE);
                layoutUserInfos.setVisibility(View.VISIBLE);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(findViewById(R.id.layoutMain), "Erreur réseau");
                loaderUserInfos.setVisibility(View.GONE);
            }
        });
    }
}
