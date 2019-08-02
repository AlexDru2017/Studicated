package com.example.studicated;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class AlarmService extends Service {

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("time");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        Intent alarmIntent = new Intent(getApplicationContext(), Alarm.class);
        PendingIntent pendingIntentService = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Integer.parseInt(input) * 1000 * 60, pendingIntent);
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID).
                setContentTitle("Alarm Service").setContentText("Service").
                setSmallIcon(R.drawable.ic_android).setContentIntent(pendingIntentService).build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
