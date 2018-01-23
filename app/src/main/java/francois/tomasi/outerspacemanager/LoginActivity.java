package francois.tomasi.outerspacemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button btnConnexion;
    private EditText editTxtUsername;
    private EditText editTxtEmail;
    private EditText editTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnConnexion = findViewById(R.id.btnConnexion);
        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        btnConnexion.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = new User(editTxtUsername.getText().toString(),
                            editTxtEmail.getText().toString(),
                            editTxtPassword.getText().toString());

                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("https://outer-space-manager.herokuapp.com/api/v1")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                    ApiService service = retrofit.create(ApiService.class);
                    Call<CreateUserResponse> request = service.createUser(user);

                    request.enqueue(new Callback<CreateUserResponse>() {
                        @Override
                        public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                            Log.i("token", response.body().getToken());
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.commit();
                        }

                        @Override
                        public void onFailure(Call<CreateUserResponse> call, Throwable t) {

                        }
                    });
                }
            }
        );
    }
}
