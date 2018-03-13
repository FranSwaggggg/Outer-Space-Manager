package francois.tomasi.outerspacemanager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // Create user
    @POST("auth/create")
    Call<CreateUserResponse> createUser(@Body User user);

    // Connect user
    @POST("auth/login")
    Call<ConnectUserResponse> connectUser(@Body User user);

    // Get data from connected user
    @GET("users/get")
    Call<GetUserResponse> getUser(@Header(Constants.HEADER_TOKEN) String token);

    // Get buildings list of connected user
    @GET("buildings/list")
    Call<GetBuildingsResponse> getBuildings(@Header(Constants.HEADER_TOKEN) String token);

    // Upgrade building with his ID
    @POST("buildings/create/{buildingId}")
    Call<UpgradeBuildingResponse> upgradeBuilding(@Header(Constants.HEADER_TOKEN) String token, @Path("buildingId") int buildingId);

    // Get fleet list of connected user
    @GET("fleet/list")
    Call<GetFleetResponse> getFleet(@Header(Constants.HEADER_TOKEN) String token);
}
