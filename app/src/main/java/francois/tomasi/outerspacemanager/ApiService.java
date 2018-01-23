package francois.tomasi.outerspacemanager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);

    @POST("auth/login")
    Call<ConnectUserResponse> connectUser(@Body User user);

    @GET("users/get")
    Call<GetUserResponse> getUser(@Header(Constants.HEADER_TOKEN) String token);

    @GET("buildings/list")
    Call<GetBuildingsResponse> getBuildings(@Header(Constants.HEADER_TOKEN) String token);

    @GET("fleet/list")
    Call<GetFleetResponse> getFleet(@Header(Constants.HEADER_TOKEN) String token);
}
