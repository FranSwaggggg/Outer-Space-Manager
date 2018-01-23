package francois.tomasi.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTxtUsername;
    private EditText editTxtEmail;
    private EditText editTxtPassword;

    private TextView alreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        final Button btnSignIn = findViewById(R.id.btnSignIn);
        final Button btnConnexion = findViewById(R.id.btnConnexion);

        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPassword);

        alreadyAccount = findViewById(R.id.alreadyAccount);

        editTxtUsername.setText("FranSwaggggg");
        editTxtPassword.setText("licencedim");

        btnCreateAccount.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = editTxtUsername.getText().toString();
                    String email = editTxtEmail.getText().toString();
                    String password = editTxtPassword.getText().toString();

                    if (!(username.matches("") || email.matches("") || password.matches(""))) {
                        User user = new User(username, email, password);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService service = retrofit.create(ApiService.class);
                        Call<CreateUserResponse> request = service.createUser(user);

                        request.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                Log.i("token", response.body().getToken());
                                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("token", response.body().getToken());
                                editor.apply();
                            }

                            @Override
                            public void onFailure(Call<CreateUserResponse> call, Throwable t) {

                            }
                        });
                    } else {
                        Log.i("error", "Champs incomplet");
                    }
                }
            }
        );

        btnSignIn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTxtEmail.setVisibility(View.GONE);
                    alreadyAccount.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.GONE);
                    btnConnexion.setVisibility(View.VISIBLE);
                }
            }
        );

        btnConnexion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = editTxtUsername.getText().toString();
                        String password = editTxtPassword.getText().toString();

                        final User user = new User(username, password);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService service = retrofit.create(ApiService.class);
                        Call<ConnectUserResponse> request = service.connectUser(user);

                        request.enqueue(new Callback<ConnectUserResponse>() {
                            @Override
                            public void onResponse(Call<ConnectUserResponse> call, Response<ConnectUserResponse> response) {
                                if (response.code() > 199 && response.code() < 301) {
                                    Log.i("token", response.body().getToken());
                                    SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString(Constants.TOKEN, response.body().getToken());
                                    editor.apply();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra(Constants.USER_CONNECTED, user);
                                    startActivity(intent);
                                    finish();
                                } else if (response.code() > 499 && response.code() < 601) {
                                    Log.i("error", "Erreur serveur");
                                } else {
                                    Log.i("error", "Erreur");
                                }
                            }

                            @Override
                            public void onFailure(Call<ConnectUserResponse> call, Throwable t) {
                                Log.i("error", "Failure");
                            }
                        });
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnConnexion = findViewById(R.id.btnConnexion);

        editTxtEmail.setVisibility(View.VISIBLE);
        alreadyAccount.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.VISIBLE);
        btnConnexion.setVisibility(View.GONE);
    }
}
