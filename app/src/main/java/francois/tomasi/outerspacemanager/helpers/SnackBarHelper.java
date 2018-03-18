package francois.tomasi.outerspacemanager.helpers;

import android.support.design.widget.Snackbar;
import android.view.View;

public abstract class SnackBarHelper {
    public static void createSnackBar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.show();
    }
}
