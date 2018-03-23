package francois.tomasi.outerspacemanager.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesHelper {

    public static String getToken(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        return prefs.getString(Constants.TOKEN, "");
    }

    public static void setToken(Context ctx, String token){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.TOKEN, token);
        editor.commit();
    }

    public static void clearToken(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.TOKEN);
        editor.commit();
    }

    public static long getExpires(Context context){
        SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, 0);
        return settings.getLong(Constants.EXPIRES, 0L);
    }

    public static void setExpires(Context context, long value){
        SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(Constants.EXPIRES, value);
        editor.commit();
    }

    public static void clearExpires(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.EXPIRES);
        editor.commit();
    }
}
