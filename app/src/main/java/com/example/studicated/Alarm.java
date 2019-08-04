package com.example.studicated;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.EditText;
import android.widget.Switch;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent("broadCastName");
        i.putExtra("message", "1");
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/alarm");
        Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
        r.play();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(2500);
        if (!r.isPlaying()) {
            context.sendBroadcast(i);
        }
    }

}
