package francois.tomasi.outerspacemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.models.Building;

public class BuildingAdapter extends ArrayAdapter<Building> {
    private final Context context;
    private final List<Building> buildings;

    public BuildingAdapter(Context context, List<Building> buildings) {
        super(context, R.layout.adapter_row_building, buildings);
        this.context = context;
        this.buildings = buildings;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_row_building, parent, false);
        }

        BuildingViewHolder viewHolder = (BuildingViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BuildingViewHolder();
            viewHolder.image = convertView.findViewById(R.id.imageViewBuilding);
            viewHolder.name = convertView.findViewById(R.id.textViewBuildingName);
            viewHolder.level = convertView.findViewById(R.id.textViewLevel);
            convertView.setTag(viewHolder);
        }

        final Building building = getItem(position);

        if (building != null) {
            Glide.with(getContext()).load(building.getImageUrl()).into(viewHolder.image);
            viewHolder.name.setText(building.getName());
            viewHolder.level.setText("Niveau " + building.getLevel());
        }

        return convertView;
    }

    private class BuildingViewHolder {
        public ImageView image;
        public TextView name;
        public TextView level;
    }
}
