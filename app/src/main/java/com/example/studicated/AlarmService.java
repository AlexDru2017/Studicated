package com.example.studicated;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends Service {

    private PendingIntent pendingIntent;
    private PendingIntent pendingIntentService;
    private AlarmManager alarmManager;
    private Intent notificationIntent;
    private Intent alarmIntent;
    private Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("time");
        notificationIntent = new Intent(this, MainActivity.class);
        alarmIntent = new Intent(getApplicationContext(), Alarm.class);
        pendingIntentService = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Integer.parseInt(input) * 1000 * 15, pendingIntent);
        notification = new NotificationCompat.Builder(this, App.CHANNEL_ID).
                setContentTitle("Alarm Service").setContentText("Service").
                setSmallIcon(R.drawable.ic_android).setContentIntent(pendingIntentService).build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
        Log.d("Service", "onDestroy");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
