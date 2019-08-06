package com.example.studicated;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

public class RingtonePlayingService extends Service {
    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String str = "3000";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mp= MediaPlayer.create(this,R.raw.alarm);
        mp.start();
        Intent new_intent = new Intent(this,ReminderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,new_intent,0);


        



        Notification popup= new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Click here!")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        notificationManager.notify(0,popup);


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {

    }
}
