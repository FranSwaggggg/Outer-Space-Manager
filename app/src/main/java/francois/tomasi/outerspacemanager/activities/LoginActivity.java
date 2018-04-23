package francois.tomasi.outerspacemanager.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private ApiService service = ApiServiceFactory.create();

    private EditText editTxtUsername;
    private EditText editTxtEmail;
    private EditText editTxtPassword;

    private LinearLayout linearLayoutSignIn;
    private LinearLayout linearLayoutSignUp;
    private LinearLayout linearLayoutLoadingLogin;

    private Button btnConnect;
    private Button btnCreateAccount;

    private TextView txtViewNoAcount;
    private TextView txtViewAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                /*
                Cela signifie que la permission à déjà était demandé et l'utilisateur l'a refusé
                Vous pouvez aussi expliquer à l'utilisateur pourquoi cette permission est nécessaire
                et la redemander
                */
            } else {
                // Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPassword);

        linearLayoutSignIn = findViewById(R.id.linearLayoutSignIn);
        linearLayoutSignUp = findViewById(R.id.linearLayoutSignUp);
        linearLayoutLoadingLogin = findViewById(R.id.linearLayoutLoadingLogin);

        btnConnect = findViewById(R.id.btnConnect);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        txtViewNoAcount = findViewById(R.id.txtViewNoAcount);
        txtViewAlreadyAccount = findViewById(R.id.txtViewAlreadyAccount);


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
                            SnackBarHelper.createSnackBar(findViewById(R.id.layoutLogin), getString(R.string.network_error));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est acceptée
                } else {
                    // La permission est refusée
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
