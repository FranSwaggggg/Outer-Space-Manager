package francois.tomasi.outerspacemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtUsername = findViewById(R.id.txtUsername);
        final TextView txtPoints = findViewById(R.id.txtPoints);
        final ProgressBar loaderDataUser = findViewById(R.id.loaderDataUser);

        Intent intent = getIntent();
        final User oldUser = (User) intent.getSerializableExtra(Constants.USER_CONNECTED);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        String token = settings.getString(Constants.TOKEN, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<GetUserResponse> request = service.getUser(token);

        request.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                GetUserResponse data = response.body();
                User user = new User(oldUser, data.getGas(), data.getGasModifier(), data.getMinerals(), data.getMineralsModifier(), data.getPoints());

                txtUsername.setText(user.getUsername());
                txtPoints.setText("Points : " + user.getPoints());

                loaderDataUser.setVisibility(View.GONE);
                txtUsername.setVisibility(View.VISIBLE);
                txtPoints.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Êtes-vous sur de vouloir vous déconnecter ?").setPositiveButton("Oui", dialogClickListener)
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

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}
