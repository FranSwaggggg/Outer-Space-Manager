package francois.tomasi.outerspacemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.ConnectUserResponse;
import francois.tomasi.outerspacemanager.responses.CreateUserResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ApiService service = ApiServiceFactory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTxtUsername = findViewById(R.id.editTxtUsername);
        final EditText editTxtEmail = findViewById(R.id.editTxtEmail);
        final EditText editTxtPassword = findViewById(R.id.editTxtPassword);

        final LinearLayout linearLayoutSignIn = findViewById(R.id.linearLayoutSignIn);
        final LinearLayout linearLayoutSignUp = findViewById(R.id.linearLayoutSignUp);
        final LinearLayout linearLayoutLoadingLogin = findViewById(R.id.linearLayoutLoadingLogin);

        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        TextView txtViewNoAcount = findViewById(R.id.txtViewNoAcount);
        TextView txtViewAlreadyAccount = findViewById(R.id.txtViewAlreadyAccount);


        // DEV CONFIG
        editTxtUsername.setText("FranSwaggggg");
        editTxtEmail.setText("francoistomasi@hotmail.fr");
        editTxtPassword.setText("licencedim");

        if(!Objects.equals(SharedPreferencesHelper.getToken(getApplicationContext()), "")
                && SharedPreferencesHelper.getExpires(getApplicationContext()) > System.currentTimeMillis() / 1000) {
            goToMainActivity();
        }

        btnConnect.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutLoadingLogin.setVisibility(View.VISIBLE);
                    linearLayoutSignIn.setVisibility(View.GONE);

                    String username = editTxtUsername.getText().toString();
                    String password = editTxtPassword.getText().toString();

                    final User user = new User(username, password);

                    Call<ConnectUserResponse> request = service.connectUser(user);

                    request.enqueue(new Callback<ConnectUserResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ConnectUserResponse> call, @NonNull Response<ConnectUserResponse> response) {
                            if (Objects.equals(response.code(), 200)) {
                                SharedPreferencesHelper.setToken(getApplicationContext(), response.body().getToken());
                                SharedPreferencesHelper.setExpires(getApplicationContext(), response.body().getExpires());

                                goToMainActivity();
                                finish();
                            } else {
                                SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Le mot de passe ou le pseudo sont erronés");
                                linearLayoutLoadingLogin.setVisibility(View.GONE);
                                linearLayoutSignIn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ConnectUserResponse> call, @NonNull Throwable t) {
                            SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Erreur réseau");
                            linearLayoutLoadingLogin.setVisibility(View.GONE);
                            linearLayoutSignIn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        );

        txtViewNoAcount.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutSignUp.setVisibility(View.VISIBLE);
                    linearLayoutSignIn.setVisibility(View.GONE);
                    editTxtEmail.setVisibility(View.VISIBLE);
                }
            }
        );

        btnCreateAccount.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = editTxtUsername.getText().toString();
                    String email = editTxtEmail.getText().toString();
                    String password = editTxtPassword.getText().toString();

                    if (!(username.matches("") || email.matches("") || password.matches(""))) {
                        User user = new User(username, email, password);

                        linearLayoutSignUp.setVisibility(View.GONE);
                        linearLayoutLoadingLogin.setVisibility(View.VISIBLE);

                        Call<CreateUserResponse> request = service.createUser(user);

                        request.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<CreateUserResponse> call, @NonNull Response<CreateUserResponse> response) {
                                if (Objects.equals(response.code(), 200)) {
                                    SharedPreferencesHelper.setToken(getApplicationContext(), response.body().getToken());
                                    SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Votre compte à bien été créé");

                                    linearLayoutLoadingLogin.setVisibility(View.GONE);
                                    findViewById(R.id.txtViewAlreadyAccount).callOnClick();
                                } else {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), jObjError.getString("message"));
                                        linearLayoutLoadingLogin.setVisibility(View.GONE);
                                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<CreateUserResponse> call, @NonNull Throwable t) {
                                SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Impossible de joindre le serveur");
                                linearLayoutLoadingLogin.setVisibility(View.GONE);
                                linearLayoutSignUp.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Champs incomplets");
                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                        linearLayoutLoadingLogin.setVisibility(View.GONE);
                    }
                }
            }
        );

        txtViewAlreadyAccount.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutSignUp.setVisibility(View.GONE);
                    linearLayoutSignIn.setVisibility(View.VISIBLE);
                    editTxtEmail.setVisibility(View.GONE);
                }
            }
        );
    }

    public void goToMainActivity(){
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    }
}
