package com.example.studicated;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class AlarmActivity extends AppCompatActivity {

    private EditText min;
    private Switch alarmSwitch;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();

    }

    private void init() {
        min = findViewById(R.id.alarmEditText);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    intent = new Intent(getApplicationContext(), AlarmService.class);
                    intent.putExtra("time", min.getText().toString());
                    startService(intent);
                    Toast.makeText(getApplicationContext(), "Alarm set in" + min.getText().toString() + " minutes", Toast.LENGTH_LONG).show();
                } else {
//                    pendingIntent.cancel();
//                    alarmManager.cancel(pendingIntent);
                    stopService(intent);
                }
            }
        });
    }
}
