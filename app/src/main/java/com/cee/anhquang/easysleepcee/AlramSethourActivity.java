package com.cee.anhquang.easysleepcee;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 * Created by DAQ on 10/13/2017.
 */

public class AlramSethourActivity extends Activity{

    Button btnOk, btnPause;
    TextView txtShowhour;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    private void Anhxa(){
        btnOk = (Button) findViewById(R.id.btnOk);
        btnPause = (Button) findViewById(R.id.btnPause);
        txtShowhour = (TextView) findViewById(R.id.txtShowhour);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bo toolbar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alramsethour);

        //Pop up
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        //Code
        Anhxa();
        calendar = Calendar.getInstance();
        final Intent intent = new Intent(AlramSethourActivity.this, AlramReceiver.class);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                String string_hour = String.valueOf(hour);
                String string_minute = String.valueOf(minute);

                if(minute < 10){
                    string_minute = "0" + String.valueOf(minute);
                }
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(AlramSethourActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
                txtShowhour.setText("Time table" + " "+ string_hour + ":" + string_minute);
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtShowhour.setText("Pause");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}
