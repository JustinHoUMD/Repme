package utilities;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;

/**
 * Created by me on 1/30/16.
 */
public class Repme extends Application{
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Repme.context = getApplicationContext();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }

    public static Context getAppContext() {
        return Repme.context;
    }
}
