package francois.tomasi.outerspacemanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);

    @GET("auth/create")
    Call<User> getUser();
}
