package francois.tomasi.outerspacemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.StrictMath.round;
import static java.lang.String.format;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private int gasModifierValue = 0;
    private int mineralsModifierValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();

        final Button btnBuildings = findViewById(R.id.btnBuildings);
        final Button btnFleet = findViewById(R.id.btnFleet);
        final Button btnResearch = findViewById(R.id.btnResearch);
        final Button btnShipyard = findViewById(R.id.btnShipyard);
        final Button btnGalaxy = findViewById(R.id.btnGalaxy);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

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
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes-vous sur de vouloir vous déconnecter ?")
                .setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(Constants.TOKEN);
                    editor.apply();

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
    public void onRefresh() {
        setData();
    }

    protected void setData() {
        final TextView txtUsername = findViewById(R.id.txtUsername);
        final TextView txtPoints = findViewById(R.id.txtPoints);
        final TextView txtGasValue = findViewById(R.id.txtGasValue);
        final TextView txtMineralsValue = findViewById(R.id.txtMineralsValue);
        final ProgressBar loaderUserInfos = findViewById(R.id.loaderUserInfos);

        final LinearLayout layoutUserInfos = findViewById(R.id.layoutUserInfos);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary, getTheme()));

        Intent intent = getIntent();
        final User oldUser = (User) intent.getSerializableExtra(Constants.USER_CONNECTED);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String token = settings.getString(Constants.TOKEN, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                final GetUserResponse data = response.body();

                User user = new User(oldUser, data.getGas(), data.getGasModifier(), data.getMinerals(), data.getMineralsModifier(), data.getPoints());

                gasModifierValue = data.getGasModifier();
                mineralsModifierValue = data.getMineralsModifier();

                txtUsername.setText(user.getUsername());
                txtPoints.setText(format("%,d", user.getPoints()) + " pts");
                txtGasValue.setText(Integer.toString(round(user.getGas())));
                txtMineralsValue.setText(Integer.toString(round(user.getMinerals())));

                loaderUserInfos.setVisibility(View.GONE);
                layoutUserInfos.setVisibility(View.VISIBLE);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {

            }
        });
    }
}
