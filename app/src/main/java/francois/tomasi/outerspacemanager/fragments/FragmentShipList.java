package francois.tomasi.outerspacemanager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Date;
import java.util.List;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.FleetActivity;
import francois.tomasi.outerspacemanager.adapters.ShipAdapter;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Ship;
import francois.tomasi.outerspacemanager.responses.GetFleetResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentShipList extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private ProgressBar progressBarShips;
    private RecyclerView recyclerViewShips;
    private RecyclerView.Adapter adapter;

    private Date currentDate;
    private List<Ship> listShips;
    //private List<ShipStatus> listSearchesShips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ship_list, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewShips = view.findViewById(R.id.recyclerViewShips);
        recyclerViewShips.setHasFixedSize(true);
        recyclerViewShips.setLayoutManager(layoutManager);
        recyclerViewShips.setVisibility(View.INVISIBLE);

        progressBarShips = view.findViewById(R.id.progressBarShips);
        progressBarShips.setVisibility(View.VISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetFleetResponse> request = service.getFleet(token);

        request.enqueue(new Callback<GetFleetResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetFleetResponse> call, @NonNull Response<GetFleetResponse> response) {

                listShips = response.body().getShips();
                /*currentDate = Calendar.getInstance().getTime();

                DAOSearchStatus daoSearchStatus = new DAOSearchStatus(getContext());
                Environment.getExternalStorageDirectory();
                daoSearchStatus.open();
                listShipsStatus = daoSearchStatus.getAllSearchStatus();

                // open search DB
                DAOSearch daoSearch = new DAOSearch(getContext());
                daoSearch.open();
                // clear all searches DB
                daoSearch.deleteAllSearches();

                for (Ship ship : listShips) {
                    // add search to DB
                    daoSearch.createSearch(search);

                    //Clear search construction in DB
                    for (SearchStatus searchStatus : listSearchesStatus) {
                        // if search in database and construction is done
                        if (searchStatus.getSearchId() != null) {
                            if (Objects.equals(String.valueOf(search.getSearchId()), searchStatus.getSearchId())) {
                                int currentTime = (int) (new Date().getTime() / 1000);
                                if (currentTime - Integer.parseInt(searchStatus.getDateSearching()) > search.getTimeToBuild(false)) {
                                    if (!daoSearchStatus.deleteSearchState(search.getSearchId()))
                                        SnackBarHelper.createSnackBar(view, getString(R.string.error_on_delete_in_database));
                                    listSearchesStatus.remove(searchStatus);
                                }
                            }
                        }
                    }
                }*/

                adapter = new ShipAdapter((FleetActivity) getActivity(), listShips);
                recyclerViewShips.setAdapter(adapter);

                recyclerViewShips.setVisibility(View.VISIBLE);
                progressBarShips.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetFleetResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutShip), getString(R.string.network_error));
                progressBarShips.setVisibility(View.GONE);
            }
        });
    }
}
