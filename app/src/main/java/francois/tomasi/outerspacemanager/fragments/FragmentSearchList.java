package francois.tomasi.outerspacemanager.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.SearchActivity;
import francois.tomasi.outerspacemanager.adapters.SearchAdapter;
import francois.tomasi.outerspacemanager.database.DAOSearch;
import francois.tomasi.outerspacemanager.database.DAOSearchStatus;
import francois.tomasi.outerspacemanager.helpers.SharedPreferencesHelper;
import francois.tomasi.outerspacemanager.helpers.SnackBarHelper;
import francois.tomasi.outerspacemanager.models.Search;
import francois.tomasi.outerspacemanager.models.SearchStatus;
import francois.tomasi.outerspacemanager.responses.GetSearchesResponse;
import francois.tomasi.outerspacemanager.services.ApiService;
import francois.tomasi.outerspacemanager.services.ApiServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearchList extends Fragment {

    private ApiService service = ApiServiceFactory.create();

    private ProgressBar progressBarSearches;
    private RecyclerView recyclerViewSearches;
    private RecyclerView.Adapter adapter;

    private Date currentDate;
    private List<Search> listSearches;
    private List<SearchStatus> listSearchesStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewSearches = view.findViewById(R.id.recyclerViewSearches);
        recyclerViewSearches.setHasFixedSize(true);
        recyclerViewSearches.setLayoutManager(layoutManager);
        recyclerViewSearches.setVisibility(View.INVISIBLE);

        progressBarSearches = view.findViewById(R.id.progressBarSearches);
        progressBarSearches.setVisibility(View.VISIBLE);

        String token = SharedPreferencesHelper.getToken(getContext());
        Call<GetSearchesResponse> request = service.getSearches(token);

        request.enqueue(new Callback<GetSearchesResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetSearchesResponse> call, @NonNull Response<GetSearchesResponse> response) {

                listSearches = response.body().getSearches();
                currentDate = Calendar.getInstance().getTime();

                DAOSearchStatus daoSearchStatus = new DAOSearchStatus(getContext());
                Environment.getExternalStorageDirectory();
                daoSearchStatus.open();
                listSearchesStatus = daoSearchStatus.getAllSearchStatus();

                // open search DB
                DAOSearch daoSearch = new DAOSearch(getContext());
                daoSearch.open();
                // clear all searches DB
                daoSearch.deleteAllSearches();

                for (Search search : listSearches) {
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
                }

                adapter = new SearchAdapter((SearchActivity) getActivity(), listSearches);
                recyclerViewSearches.setAdapter(adapter);

                recyclerViewSearches.setVisibility(View.VISIBLE);
                progressBarSearches.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<GetSearchesResponse> call, @NonNull Throwable t) {
                SnackBarHelper.createSnackBar(view.findViewById(R.id.layoutSearch), getString(R.string.network_error));
                progressBarSearches.setVisibility(View.GONE);
            }
        });
    }
}
