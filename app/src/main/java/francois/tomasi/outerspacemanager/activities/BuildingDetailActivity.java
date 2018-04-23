package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.fragments.FragmentBuildingDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;

public class BuildingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);

        FragmentBuildingDetail fragmentBuildingDetail = (FragmentBuildingDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingDetail);
        fragmentBuildingDetail.replaceBuilding(getIntent().getIntExtra(Constants.EXTRA_BUILDING_ID, 0));
    }
}
