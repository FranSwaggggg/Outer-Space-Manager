package francois.tomasi.outerspacemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.helpers.Constants;
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

        final TextInputLayout wrapperEmail = findViewById(R.id.wrapperEmail);

        final EditText editTxtUsername = findViewById(R.id.editTxtUsername);
        final EditText editTxtEmail = findViewById(R.id.editTxtEmail);
        final EditText editTxtPassword = findViewById(R.id.editTxtPassword);

        final LinearLayout linearLayoutSignIn = findViewById(R.id.linearLayoutSignIn);
        final LinearLayout linearLayoutSignUp = findViewById(R.id.linearLayoutSignUp);
        final LinearLayout layoutLoadingLogin = findViewById(R.id.layoutLoadingLogin);

        Button btnConnect = findViewById(R.id.btnConnect);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);


        // DEV CONFIG
        editTxtUsername.setText("FranSwaggggg");
        editTxtEmail.setText("francoistomasi@hotmail.fr");
        editTxtPassword.setText("licencedim");

        btnConnect.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = editTxtUsername.getText().toString();
                    String password = editTxtPassword.getText().toString();

                    final User user = new User(username, password);

                    layoutLoadingLogin.setVisibility(View.VISIBLE);
                    linearLayoutSignIn.setVisibility(View.GONE);

                    Call<ConnectUserResponse> request = service.connectUser(user);

                    request.enqueue(new Callback<ConnectUserResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ConnectUserResponse> call, @NonNull Response<ConnectUserResponse> response) {
                            if (Objects.equals(response.code(), 200)) {
                                SharedPreferencesHelper.setToken(getApplicationContext(), response.body().getToken());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra(Constants.USER_CONNECTED, user);
                                startActivity(intent);
                                finish();
                            } else {
                                SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Le mot de passe ou le pseudo sont erronés", Snackbar.LENGTH_LONG);
                                layoutLoadingLogin.setVisibility(View.GONE);
                                linearLayoutSignIn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ConnectUserResponse> call, @NonNull Throwable t) {
                            SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Erreur réseau", Snackbar.LENGTH_LONG);
                            layoutLoadingLogin.setVisibility(View.GONE);
                            linearLayoutSignIn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        );

        btnSignUp.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapperEmail.setVisibility(View.VISIBLE);
                    linearLayoutSignIn.setVisibility(View.GONE);
                    linearLayoutSignUp.setVisibility(View.VISIBLE);
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

                        layoutLoadingLogin.setVisibility(View.VISIBLE);
                        linearLayoutSignUp.setVisibility(View.GONE);

                        Call<CreateUserResponse> request = service.createUser(user);

                        request.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<CreateUserResponse> call, @NonNull Response<CreateUserResponse> response) {
                                if (Objects.equals(response.code(), 200)) {
                                    SharedPreferencesHelper.setToken(getApplicationContext(), response.body().getToken());
                                    SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Votre compte à bien été créé", Snackbar.LENGTH_LONG);
                                    findViewById(R.id.btnSignIn).callOnClick();
                                } else {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), jObjError.getString("message"), Snackbar.LENGTH_LONG);
                                        layoutLoadingLogin.setVisibility(View.GONE);
                                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<CreateUserResponse> call, @NonNull Throwable t) {
                                SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Erreur réseau", Snackbar.LENGTH_LONG);
                                layoutLoadingLogin.setVisibility(View.GONE);
                                linearLayoutSignUp.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), "Champs incomplets", Snackbar.LENGTH_LONG);
                        layoutLoadingLogin.setVisibility(View.GONE);
                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                    }
                }
            }
        );

        btnSignIn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapperEmail.setVisibility(View.GONE);
                    linearLayoutSignIn.setVisibility(View.VISIBLE);
                    linearLayoutSignUp.setVisibility(View.GONE);
                }
            }
        );
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnSignIn).callOnClick();
    }
}
