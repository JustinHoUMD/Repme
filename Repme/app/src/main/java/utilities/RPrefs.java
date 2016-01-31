package utilities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

/**
 * Created by me on 1/30/16.
 */
public class RPrefs {
    public static final String TAG = "RPrefs";
    public static void putInt(String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Repme.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Repme.getAppContext());
        int val = prefs.getInt(key, 0);
        return val;
    }

    public static int getScore() {
        int sum = 0;
        Map<String,?> keys =  PreferenceManager.getDefaultSharedPreferences(Repme.getAppContext()).getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
            sum+=Integer.valueOf(entry.getValue().toString());
            Log.d(TAG, "sum: "+sum);
        }
        return sum;

    }
}
