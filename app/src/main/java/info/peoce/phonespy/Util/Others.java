package info.peoce.phonespy.Util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.List;

import info.peoce.phonespy.App;

/**
 * Created by peoce on 15/10/17.
 */
public class Others {
     public boolean checkServerAlive(String serverName) {
        ActivityManager am = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfoList = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : runningServiceInfoList) {
            if (serverName.equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForeHeadApp(String [] appNames) {
        ActivityManager manager = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
        ComponentName componentInfo = task.get(0).topActivity;
//        Log.i("TAG", componentInfo.getPackageName() + "____" + componentInfo.getClassName());
//        if (componentInfo.getPackageName().equals(PackageName)) return true;
//        return false;
        for(String appname:appNames){
            if(componentInfo.getClassName().contains(appname)){
                return true;
            }
        }
        return false;
    }
}
