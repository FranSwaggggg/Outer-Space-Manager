package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.adapters.GalaxyAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.responses.GetUsersResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalaxyActivity extends AppCompatActivity {

    private ApiService service = ApiServiceFactory.create();

    ListView listViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);

        listViewUsers = findViewById(R.id.listViewUsers);

        String token = SharedPreferencesHelper.getToken(getApplicationContext());

        Call<GetUsersResponse> request = service.getAllUsers(token, 0, 20);

        request.enqueue(new Callback<GetUsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUsersResponse> call, @NonNull Response<GetUsersResponse> response) {

                final GalaxyAdapter adapter = new GalaxyAdapter(GalaxyActivity.this, response.body().getUsers());
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<GetUsersResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
