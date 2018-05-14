package francois.tomasi.outerspacemanager.services;

import francois.tomasi.outerspacemanager.helpers.Constants;
import francois.tomasi.outerspacemanager.models.User;
import francois.tomasi.outerspacemanager.responses.AttackUserResponse;
import francois.tomasi.outerspacemanager.responses.ConnectUserResponse;
import francois.tomasi.outerspacemanager.responses.CreateShipResponse;
import francois.tomasi.outerspacemanager.responses.CreateUserResponse;
import francois.tomasi.outerspacemanager.responses.GetBuildingsResponse;
import francois.tomasi.outerspacemanager.responses.GetFleetResponse;
import francois.tomasi.outerspacemanager.responses.GetSearchesResponse;
import francois.tomasi.outerspacemanager.responses.GetShipResponse;
import francois.tomasi.outerspacemanager.responses.GetShipsResponse;
import francois.tomasi.outerspacemanager.responses.GetUserResponse;
import francois.tomasi.outerspacemanager.responses.GetUsersResponse;
import francois.tomasi.outerspacemanager.responses.UpgradeBuildingResponse;
import francois.tomasi.outerspacemanager.responses.UpgradeSearchResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    /* ===== USER ===== */

    // Create user
    @POST("auth/create")
    Call<CreateUserResponse> createUser(
            @Body User user);

    // Connect user
    @POST("auth/login")
    Call<ConnectUserResponse> connectUser(
            @Body User user);

    // Get data from connected user
    @GET("users/get")
    Call<GetUserResponse> getUser(
            @Header(Constants.HEADER_TOKEN) String token);

    // Get all users
    @GET("users/{from}/{limit}")
    Call<GetUsersResponse> getAllUsers(
            @Header(Constants.HEADER_TOKEN) String token,
            @Path("from") int from,
            @Path("limit") int limit);


    /* ===== BUILDING ===== */

    // Get buildings list of connected user
    @GET("buildings/list")
    Call<GetBuildingsResponse> getBuildings(
            @Header(Constants.HEADER_TOKEN) String token);

    // Upgrade building with his ID
    @POST("buildings/create/{buildingId}")
    Call<UpgradeBuildingResponse> upgradeBuilding(
            @Header(Constants.HEADER_TOKEN) String token,
            @Path("buildingId") int buildingId);


    /* ===== SEARCH ===== */

    // Get searches list of connected user
    @GET("searches/list")
    Call<GetSearchesResponse> getSearches(
            @Header(Constants.HEADER_TOKEN) String token);

    // Upgrade search with his ID
    @POST("searches/create/{searchId}")
    Call<UpgradeSearchResponse> upgradeSearch(
            @Header(Constants.HEADER_TOKEN) String token,
            @Path("searchId") int searchId);


    /* ===== FLEET ===== */

    // Get fleet list of connected user
    @GET("fleet/list")
    Call<GetFleetResponse> getFleet(
            @Header(Constants.HEADER_TOKEN) String token);

    // Get available ships list
    @POST("fleet/attack/{userName}")
    Call<AttackUserResponse> attackUser(
            @Header(Constants.HEADER_TOKEN) String token,
            @Body Object[] ships,
            @Path("userName") int userName);


    /* ===== SHIP ===== */

    // Get ship by its ID
    @GET("ships")
    Call<GetShipsResponse> getShips(
            @Header(Constants.HEADER_TOKEN) String token);

    // Get available ships list
    @GET("ships/{shipId}")
    Call<GetShipResponse> getShip(
            @Header(Constants.HEADER_TOKEN) String token,
            @Path("shipId") int shipId);

    // Get available ships list
    @POST("ships/create/{shipId}")
    Call<CreateShipResponse> createShip(
            @Header(Constants.HEADER_TOKEN) String token,
            @Body int amount,
            @Path("shipId") int shipId);
}
