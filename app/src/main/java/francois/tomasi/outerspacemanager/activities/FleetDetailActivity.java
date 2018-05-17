package francois.tomasi.outerspacemanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.fragments.FragmentShipDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;

public class FleetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);

        FragmentShipDetail fragmentShipDetail = (FragmentShipDetail) getSupportFragmentManager().findFragmentById(R.id.fragmentShipDetail);
        fragmentShipDetail.replaceShip(getIntent().getIntExtra(Constants.EXTRA_SHIP_ID, 0));
    }
}
