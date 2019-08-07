package com.example.studicated;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class RingtonePlayingService extends Service {
    private MediaPlayer mp;
    private PendingIntent pendingIntentService;
    private Intent notificationIntent;
    private Intent alarmIntent;
    private Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mp= MediaPlayer.create(this,R.raw.alarm);
        mp.start();
        String text = intent.getExtras().getString("text");
        int requestCode= intent.getExtras().getInt("requestCode");
        NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationIntent = new Intent(this, ReminderActivity.class);
        notificationIntent.putExtra("requestCode",requestCode);
        pendingIntentService = PendingIntent.getActivity(this, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Studicated Reminder")
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setOngoing(false)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL)
                .setContentIntent(pendingIntentService).build();
        notify_manager.notify(requestCode,notification);

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Ringtone playing Stopped", Toast.LENGTH_LONG).show();
        mp.stop();
    }
}
