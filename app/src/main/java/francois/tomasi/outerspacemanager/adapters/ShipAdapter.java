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
import francois.tomasi.outerspacemanager.activities.FleetActivity;
import francois.tomasi.outerspacemanager.activities.FleetDetailActivity;
import francois.tomasi.outerspacemanager.fragments.FragmentShipDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;
import francois.tomasi.outerspacemanager.models.Ship;

public class ShipAdapter extends RecyclerView.Adapter<ShipAdapter.ViewHolder> {

    private FleetActivity fleetActivity;
    private static List<Ship> ships;

    public ShipAdapter(FleetActivity fleetActivity, List<Ship> ships) {
        this.fleetActivity = fleetActivity;
        ShipAdapter.ships = ships;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private ImageView imageViewShip;
        private TextView textViewShipName;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();

            imageViewShip = v.findViewById(R.id.imageViewShip);
            textViewShipName = v.findViewById(R.id.textViewShipName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentShipDetail fragB = (FragmentShipDetail) fleetActivity.getSupportFragmentManager().findFragmentById(R.id.fragmentShipDetail);

                    if (fragB == null || !fragB.isInLayout()){
                        Intent intent = new Intent(context, FleetDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_SHIP_ID, ships.get(getAdapterPosition()).getShipId());
                        context.startActivity(intent);
                    } else {
                        fragB.replaceShip(ships.get(getAdapterPosition()).getShipId());
                    }
                }
            });
        }

        void display(Ship ship) {
            int resID = context.getResources().getIdentifier("ship_" + ship.getShipId() ,
                    "drawable", context.getPackageName());
            imageViewShip.setImageResource(resID);
            textViewShipName.setText(ship.getName());
        }
    }

    @NonNull
    @Override
    public ShipAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_row_ship, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ship ship = ships.get(position);
        holder.display(ship);
    }

    @Override
    public int getItemCount() { return ships.size(); }
}
