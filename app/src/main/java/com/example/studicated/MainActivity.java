package com.example.studicated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gpaButton = (Button) findViewById(R.id.gpaButton);
        Button alarmButton = (Button) findViewById(R.id.alarmButton);

        gpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "GPA button was clicked");
                Intent intent = new Intent(getApplicationContext(), GPAActivity.class);
                startActivity(intent);
            }
    });

    }

}
