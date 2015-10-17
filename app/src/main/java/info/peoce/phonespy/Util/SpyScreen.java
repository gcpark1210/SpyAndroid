package info.peoce.phonespy.Util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peoce on 15/10/17.
 */
public class SpyScreen {
    static public void takeScreenshot(Activity activity) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//            String mPath = "mnt/sdcard/takeScreenShot_" + sdf.format(new Date()) + ".jpeg";
            String mPath = "mnt/sdcard/takeScreenShot_" + sdf.format(new Date()) + ".jpeg";
            View v1 = activity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

   static public void screenShot(Activity activity) {
       View view = activity.getWindow().getDecorView().getRootView();
       Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
               view.getHeight(), Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(bitmap);
       view.draw(canvas);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
       String mPath = "mnt/sdcard/screenShot" + sdf.format(new Date()) + ".jpeg";
       File imageFile = new File(mPath);
       try {

           FileOutputStream fos = new FileOutputStream(imageFile);
           int quality = 100;
           bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
           fos.flush();
           fos.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    //need root
       static public void screenShot_background(){
        try {
            Process sh = Runtime.getRuntime().exec("su", null, null);
            OutputStream os = sh.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            os.write(("/system/bin/screencap -p " + "mnt/sdcard/screenShot2_" + sdf.format(new Date()) + ".png").getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        }catch (Exception e){

        }
    }

    }


