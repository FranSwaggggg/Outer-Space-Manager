package francois.tomasi.outerspacemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import francois.tomasi.outerspacemanager.R;
import francois.tomasi.outerspacemanager.models.User;

import static java.lang.String.format;

public class GalaxyAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> users;

    public GalaxyAdapter(Context context, List<User> users) {
        super(context, R.layout.adapter_row_galaxy, users);
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_row_galaxy, parent, false);
        }

        UserViewHolder viewHolder = (UserViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new UserViewHolder();
            viewHolder.username = convertView.findViewById(R.id.textViewUsername);
            viewHolder.points = convertView.findViewById(R.id.textViewPoints);
            convertView.setTag(viewHolder);
        }

        final User user = getItem(position);

        if (user != null) {
            Locale locale = getContext().getResources().getConfiguration().locale;

            viewHolder.username.setText((position + 1) + ". " + user.getUsername());
            viewHolder.points.setText(format(locale, "% d", user.getPoints()) + " Pts");
        }

        return convertView;
    }

    private class UserViewHolder {
        public TextView username;
        public TextView points;
    }
}
