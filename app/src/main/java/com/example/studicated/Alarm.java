package com.example.studicated;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;


public class Alarm extends BroadcastReceiver {

    private MediaPlayer mMediaPlayer;
    private Context context;
    private Intent i;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        mMediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mMediaPlayer.setOnCompletionListener(new HandleOnCompletion());
        mMediaPlayer.start();
        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(2500);
    }


    private class HandleOnCompletion extends Service implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            i = new Intent("broadCastName");
            i.putExtra("message", "1");
            mp.stop();
            context.sendBroadcast(i);
            Intent intent = new Intent(context.getApplicationContext(), AlarmService.class);
            intent.putExtra("mode", "off");
//            startService(intent);
            Log.d("Alarm", "onCompletion");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }


}
