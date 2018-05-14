package francois.tomasi.outerspacemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.SearchActivity;
import francois.tomasi.outerspacemanager.activities.SearchDetailActivity;
import francois.tomasi.outerspacemanager.fragments.FragmentSearchDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;
import francois.tomasi.outerspacemanager.models.Search;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private SearchActivity searchActivity;
    private static List<Search> searchs;

    public SearchAdapter(SearchActivity searchActivity, List<Search> searchs) {
        this.searchActivity = searchActivity;
        SearchAdapter.searchs = searchs;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private ImageView imageViewSearch;
        private TextView textViewSearchName;
        private TextView textViewSearchLevel;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();

            imageViewSearch = v.findViewById(R.id.imageViewSearch);
            textViewSearchName = v.findViewById(R.id.textViewSearchName);
            textViewSearchLevel = v.findViewById(R.id.textViewSearchLevel);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentSearchDetail fragB = (FragmentSearchDetail) searchActivity.getSupportFragmentManager().findFragmentById(R.id.fragmentSearchDetail);

                    if (fragB == null || !fragB.isInLayout()){
                        Intent intent = new Intent(context, SearchDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_SEARCH_ID, searchs.get(getAdapterPosition()).getSearchId());
                        context.startActivity(intent);
                    } else {
                        fragB.replaceSearch(searchs.get(getAdapterPosition()).getSearchId());
                    }
                }
            });
        }

        void display(Search search) {
            int resID = context.getResources().getIdentifier("search_" + search.getName().toLowerCase().replace(' ', '_') ,
                    "drawable", context.getPackageName());
            imageViewSearch.setImageResource(resID);
            textViewSearchName.setText(search.getName());
            textViewSearchLevel.setText(String.valueOf("Niveau " + search.getLevel()));
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_row_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Search search = searchs.get(position);
        holder.display(search);
    }

    @Override
    public int getItemCount() { return searchs.size(); }
}
