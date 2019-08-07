package com.example.studicated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class AlarmActivity extends AppCompatActivity {

    private EditText min;
    private Switch alarmSwitch;
    private Intent intent;
    private BroadcastReceiver mBroadcastReceiver;
    private Boolean isRegistered = false;
    private Boolean isTurnOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();
        registerReceiver(mBroadcastReceiver, new IntentFilter("broadCastName"));
        isRegistered = true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("AlarmService", "onDestroy");
        try {
            if (!isTurnOn) {
                unregisterReceiver(mBroadcastReceiver);
            }
            unregisterReceiver(mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AlarmService", "onPause");
        try {
            if (!isTurnOn) {
                unregisterReceiver(mBroadcastReceiver);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        min = findViewById(R.id.alarmEditText);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = alarmSwitch.isChecked();
                if (isChecked) {
                    intent = new Intent(getApplicationContext(), AlarmService.class);
                    if (!min.getText().toString().matches("")) {
                        intent.putExtra("time", min.getText().toString());
                        intent.putExtra("mode", "on");
                        startService(intent);
                        Toast.makeText(getApplicationContext(), "Alarm set in" + min.getText().toString() + " minutes", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter min's before setting the alarm", Toast.LENGTH_LONG).show();
                        alarmSwitch.setChecked(false);
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(AlarmActivity.this).create();
                    alertDialog.setTitle("Alarm Manager");
                    alertDialog.setMessage("Have you finished studying?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    stopService(intent);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alarmSwitch.setChecked(true);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    Bundle receive = intent.getExtras();
                    String message = receive.getString("message");
                    Log.d("mBroadcastReceiver", message);
                    if (message.matches("1")) {
                        intent = new Intent(getApplicationContext(), AlarmService.class);
                        stopService(intent);
                    }
                    min.setText("");
                    alarmSwitch.setChecked(false);
                }
            }
        };
    }
}
