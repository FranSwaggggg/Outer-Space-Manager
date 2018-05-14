package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.fragments.FragmentSearchDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;

public class SearchDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        FragmentSearchDetail fragmentSearchDetail = (FragmentSearchDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentSearchDetail);
        fragmentSearchDetail.replaceSearch(getIntent().getIntExtra(Constants.EXTRA_SEARCH_ID, 0));
    }
}
