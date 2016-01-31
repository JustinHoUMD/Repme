package utilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by me on 1/30/16.
 */
public class Repme extends Application{
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Repme.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Repme.context;
    }
}
