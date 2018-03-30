package francois.tomasi.outerspacemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.models.User;

import static java.lang.String.format;

public class GalaxyAdapter extends RecyclerView.Adapter<GalaxyAdapter.ViewHolder> {

    private static List<User> users;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        private TextView textViewPosition;
        private TextView textViewUsername;
        private TextView textViewPoints;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();

            textViewPosition = v.findViewById(R.id.textViewPosition);
            textViewUsername = v.findViewById(R.id.textViewUsername);
            textViewPoints = v.findViewById(R.id.textViewPoints);
        }

        public void display(User user) {
            textViewPosition.setText(String.valueOf((getAdapterPosition() + 1) + "."));
            textViewUsername.setText(user.getUsername());

            Locale locale = context.getResources().getConfiguration().locale;
            textViewPoints.setText(String.valueOf(format(locale, "%,d", user.getPoints()) + " Pts"));
        }
    }

    public GalaxyAdapter(List<User> users) { GalaxyAdapter.users = users; }

    @NonNull
    @Override
    public GalaxyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_row_galaxy, parent, false);
        return new GalaxyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalaxyAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.display(user);
    }

    @Override
    public int getItemCount() { return users.size(); }
}
