package com.example.studicated;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/alarm");
        Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
        r.play();

        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(2500);
    }

}
