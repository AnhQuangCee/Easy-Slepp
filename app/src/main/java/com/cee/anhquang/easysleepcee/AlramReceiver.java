package com.cee.anhquang.easysleepcee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by DAQ on 10/14/2017.
 */

public class AlramReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Toi trong Receiver","Xin chao");
        String chuoi_string = intent.getExtras().getString("extra");
        Log.e("Ban Truyen Key", chuoi_string);
        Intent myIntent = new Intent(context, AlarmMusic.class);
        myIntent.putExtra("extra",chuoi_string);
        context.startService(myIntent);
    }
}
