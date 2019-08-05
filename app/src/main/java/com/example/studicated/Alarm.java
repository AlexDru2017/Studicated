package com.example.studicated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;


public class Alarm extends BroadcastReceiver {

    private MediaPlayer mMediaPlayer;
    private Context context;
    private Intent i;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
//        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/alarm");
        mMediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mMediaPlayer.setOnCompletionListener(new HandleOnCompletion());
        mMediaPlayer.start();
//        Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
//        r.play();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(2500);
    }


    private class HandleOnCompletion implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            i = new Intent("broadCastName");
            i.putExtra("message", "1");
            mp.stop();
            context.sendBroadcast(i);
            Log.d("Alarm", "onCompletion");
        }
    }


}
