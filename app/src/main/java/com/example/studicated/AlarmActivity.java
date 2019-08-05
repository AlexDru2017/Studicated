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
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class AlarmActivity extends AppCompatActivity {

    private EditText min;
    private Switch alarmSwitch;
    private Intent intent;
    private BroadcastReceiver mBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();
        registerReceiver(mBroadcastReceiver, new IntentFilter("broadCastName"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void init() {
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
