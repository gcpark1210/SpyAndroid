package info.peoce.phonespy;

import android.app.Application;
import android.content.Context;

import info.peoce.phonespy.Activity.MainActivity;

/**
 * Created by peoce on 15/10/17.
 */
public class App extends Application {
    static Context context;
    public static String[] tap_appList = {"weixin", "mm", "qq", "QQ", "mobileqq", "camera"};
    public static MainActivity.MyHandler myHandler;

    static public void setHandler(MainActivity.MyHandler handler) {
        myHandler = handler;
    }

    static public MainActivity.MyHandler getMyHandler() {
        if (myHandler != null) {
            return myHandler;
        }
        return null;
    }

    static public Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
