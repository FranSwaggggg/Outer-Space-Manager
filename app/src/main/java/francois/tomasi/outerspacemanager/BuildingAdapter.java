package francois.tomasi.outerspacemanager;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static java.lang.String.format;

public class BuildingAdapter extends ArrayAdapter<Building> {
    private final Context context;
    private final List<Building> buildings;

    private OnClickButtonListItem mOnClickButtonListItem;

    public void setOnEventListener(OnClickButtonListItem listener)
    {
        mOnClickButtonListItem = listener;
    }

    public BuildingAdapter(Context context, List<Building> buildings) {
        super(context, R.layout.row_building, buildings);
        this.context = context;
        this.buildings = buildings;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_building, parent, false);
        }

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BuildingViewHolder();
            viewHolder.image = convertView.findViewById(R.id.imageViewBuilding);
            viewHolder.name = convertView.findViewById(R.id.textViewBuildingName);
            viewHolder.level = convertView.findViewById(R.id.textViewLevel);
            viewHolder.progressBarUpgradeBuilding = convertView.findViewById(R.id.progressBarUpgradeBuilding);
            viewHolder.layoutBuildingData = convertView.findViewById(R.id.layoutBuildingData);
            viewHolder.gasCost = convertView.findViewById(R.id.textViewGasCost);
            viewHolder.mineralCost = convertView.findViewById(R.id.textViewMineralCost);
            viewHolder.buttonUpgrade = convertView.findViewById(R.id.btnUpgradeBuilding);
            convertView.setTag(viewHolder);
        }

        final Building building = getItem(position);

        Glide.with(getContext()).load(building.getImageUrl()).into(viewHolder.image);
        viewHolder.name.setText(building.getName());
        viewHolder.level.setText("Niveau " + building.getLevel());
        viewHolder.gasCost.setText(format("%,d", building.getGasCostLevel0() + building.getGasCostByLevel() * building.getLevel()));
        viewHolder.mineralCost.setText(format("%,d", building.getMineralCostLevel0() + building.getMineralCostByLevel() * building.getLevel()));
        viewHolder.buttonUpgrade.setText("Améliorer au niveau " + (building.getLevel() + 1));

        viewHolder.buttonUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickButtonListItem.OnClick(building.getBuildingId());
            }
        });

        if (building.isBuilding()) {
            viewHolder.layoutBuildingData.setVisibility(View.INVISIBLE);
            viewHolder.progressBarUpgradeBuilding.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layoutBuildingData.setVisibility(View.VISIBLE);
            viewHolder.progressBarUpgradeBuilding.setVisibility(View.INVISIBLE);
        }

        viewHolder.buttonUpgrade.setEnabled(!building.isBuilding());

        return convertView;
    }

    private class BuildingViewHolder {
        public ImageView image;
        public TextView name;
        public TextView level;
        public ProgressBar progressBarUpgradeBuilding;
        public LinearLayout layoutBuildingData;
        public TextView gasCost;
        public TextView mineralCost;
        public Button buttonUpgrade;
    }
}
