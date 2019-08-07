package com.example.studicated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm Receiver", "OnCreate Method was started");
        Intent ringtoneServiceIntent = new Intent(context, RingtonePlayingService.class);
        ringtoneServiceIntent.putExtra("text", intent.getExtras().getString("text"));
        ringtoneServiceIntent.putExtra("requestCode", intent.getExtras().getInt("requestCode"));
        try {
            context.startService(ringtoneServiceIntent);
        } catch (IllegalStateException e) {

        }
    }
}
