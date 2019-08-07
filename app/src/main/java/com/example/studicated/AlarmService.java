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

import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {

    private PendingIntent pendingIntent;
    private PendingIntent pendingIntentService;
    private AlarmManager alarmManager;
    private Intent notificationIntent;
    private Intent alarmIntent;
    private Notification notification;
    private long time;
    private Timer myTimer;
    private TimerTask myTask;

    @Override
    public void onCreate() {
        super.onCreate();

//        myTask = new TimerTask() {
//            public void run() {
//                Log.d("AlarmService", "TimerTask_onCreate");
//                stopSelf();
//            }
//        };
//        myTimer = new Timer();
//        myTimer.schedule(myTask, time + 1000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String mode = intent.getStringExtra("mode");
        if (mode.matches("on")) {
            String input = intent.getStringExtra("time");
            notificationIntent = new Intent(this, MainActivity.class);
            alarmIntent = new Intent(getApplicationContext(), Alarm.class);
            pendingIntentService = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            time = System.currentTimeMillis() + Integer.parseInt(input) * 1000 * 15;
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            notification = new NotificationCompat
                    .Builder(this, App.CHANNEL_ID)
                    .setContentTitle("Alarm Service")
                    .setContentText("Service")
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(pendingIntentService)
                    .build();
            startForeground(1, notification);
        } else if (mode.matches("off")) {
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("AlarmService", "onDestroy");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
