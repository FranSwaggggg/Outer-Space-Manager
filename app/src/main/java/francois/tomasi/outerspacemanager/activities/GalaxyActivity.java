package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.adapters.GalaxyAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.GetUsersResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalaxyActivity extends AppCompatActivity {

    private ApiService service = ApiServiceFactory.create();

    private ProgressBar progressBarGalaxy;
    private RecyclerView recyclerViewUsers;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;

    private List<User> users;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);

        layoutManager = new LinearLayoutManager(this);

        progressBarGalaxy = findViewById(R.id.progressBarGalaxy);
        progressBarGalaxy.setVisibility(View.VISIBLE);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setVisibility(View.INVISIBLE);

        String token = SharedPreferencesHelper.getToken(getApplicationContext());
        Call<GetUsersResponse> request = service.getAllUsers(token, 0, 20);

        request.enqueue(new Callback<GetUsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUsersResponse> call, @NonNull Response<GetUsersResponse> response) {
                users = response.body().getUsers();
                size = response.body().getSize();

                adapter = new GalaxyAdapter(users);
                recyclerViewUsers.setAdapter(adapter);

                recyclerViewUsers.setVisibility(View.VISIBLE);
                progressBarGalaxy.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetUsersResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(findViewById(R.id.layoutGalaxy), getString(R.string.network_error));
            }
        });
    }
}
