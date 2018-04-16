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

import com.bumptech.glide.Glide;

import java.util.List;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.activities.BuildingActivity;
import francois.tomasi.outerspacemanager.activities.BuildingDetailActivity;
import francois.tomasi.outerspacemanager.fragments.FragmentBuildingDetail;
import francois.tomasi.outerspacemanager.helpers.Constants;
import francois.tomasi.outerspacemanager.models.Building;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    private BuildingActivity buildingActivity;
    private static List<Building> buildings;

    public BuildingAdapter(BuildingActivity buildingActivity, List<Building> buildings) {
        this.buildingActivity = buildingActivity;
        BuildingAdapter.buildings = buildings;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private ImageView imageViewBuilding;
        private TextView textViewBuildingName;
        private TextView textViewBuildingLevel;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();

            imageViewBuilding = v.findViewById(R.id.imageViewBuilding);
            textViewBuildingName = v.findViewById(R.id.textViewBuildingName);
            textViewBuildingLevel = v.findViewById(R.id.textViewBuildingLevel);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentBuildingDetail fragB = (FragmentBuildingDetail) buildingActivity.getSupportFragmentManager().findFragmentById(R.id.fragmentBuildingDetail);

                    if (fragB == null || !fragB.isInLayout()){
                        Intent intent = new Intent(context, BuildingDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_BUILDING_ID, buildings.get(getAdapterPosition()).getBuildingId());
                        context.startActivity(intent);
                    } else {
                        fragB.replaceBuilding(buildings.get(getAdapterPosition()).getBuildingId());
                    }
                }
            });
        }

        void display(Building building) {
            Glide.with(context).load(building.getImageUrl()).into(imageViewBuilding);
            textViewBuildingName.setText(building.getName());
            textViewBuildingLevel.setText(String.valueOf("Niveau " + building.getLevel()));
        }
    }


    @NonNull
    @Override
    public BuildingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_row_building, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Building building = buildings.get(position);
        holder.display(building);
    }

    @Override
    public int getItemCount() { return buildings.size(); }
}
