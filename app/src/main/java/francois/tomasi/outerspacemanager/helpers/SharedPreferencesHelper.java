package francois.tomasi.outerspacemanager.helpers;


import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesHelper {

    public static String getToken(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        return prefs.getString(Constants.TOKEN, null);
    }

    public static void setToken(Context ctx, String token){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
    }

    public static void clearToken(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.TOKEN);
        editor.apply();
    }
}
