package com.cee.anhquang.easysleepcee;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ServiceConfigurationError;
import java.util.logging.Handler;

/**
 * Created by DAQ on 10/16/2017.
 */

public class AlarmMusic extends Service {

    int id;
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String nhanKey = intent.getExtras().getString("extra");
        Log.e("Music Nhan Key", nhanKey);

        //Notification new
        NotificationManager notify_manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent intent2 = new Intent(this.getApplicationContext(),AlramSethourActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent2,0);

        //Build Notification
        Notification.Builder notifyBuilder = new Notification.Builder(this);
        notifyBuilder.setContentTitle("An alarm is going off");
        notifyBuilder.setContentText("Click me!");
        notifyBuilder.setSmallIcon(R.mipmap.ic_alarm1);
        notifyBuilder.setAutoCancel(true);

        //Remove app Alarm Clock
        notifyBuilder.setContentIntent(pIntent);

        //Show Notification
        notify_manager.notify(1,notifyBuilder.build());
        if(nhanKey.equals("on"))
        {
            id = 1;
        }
        else if(nhanKey.equals("off"))
        {
            id = 0;
        }

        if(id==1)
        {
            mediaPlayer = MediaPlayer.create(AlarmMusic.this,R.raw.ring);
            mediaPlayer.start();
            id=0;
        }
        else if(id==0)
        {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        Log.e("Toi trong Music","Hello");
        return START_NOT_STICKY;
    }
}
