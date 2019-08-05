package com.example.studicated;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity implements ReminderDialog.ReminderDialogListener {

    private ArrayList<Reminder> remindersList;
    private RemindersAdapter remindersAdapter;
    private ListView remindersViewList;

    @Override
    public void applyTextsFromReminder(String title, String hour, String date, String text) {
        Log.d("Apply Texts:", title + " " + hour + " " + date + " " + text);
        Reminder newReminder = new Reminder(title,hour,date,text);
        remindersList.add(newReminder);
        remindersAdapter.notifyDataSetChanged();
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        remindersList = new ArrayList<Reminder>();
        Button addReminder = (Button) findViewById(R.id.addReminder);
        remindersViewList= (ListView) findViewById(R.id.remindersList);
        remindersAdapter = new RemindersAdapter(getApplicationContext(), remindersList);
        remindersViewList.setAdapter(remindersAdapter);


        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "Add reminder button was clicked");
                ReminderDialog dialog = new ReminderDialog();
                dialog.show(getSupportFragmentManager(), "Reminder Dialog");
            }
        });

        remindersViewList.setLongClickable(true);
        remindersViewList.setClickable(true);
        remindersViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.i("Button", "View a reminder was clicked");
                ViewReminderDialog dialog = new ViewReminderDialog();
                Bundle args= new Bundle();
                args.putString("title",remindersList.get(position).getTitle());
                args.putString("hour",remindersList.get(position).getHour());
                args.putString("date",remindersList.get(position).getDate());
                args.putString("text",remindersList.get(position).getText());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "View Reminder Dialog");

            }
        });
        remindersViewList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(ReminderActivity.this).create();
                alertDialog.setTitle("Delete Reminder");
                alertDialog.setMessage("Are you sure you want to delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                remindersList.remove(position);
                                remindersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                return true;
            }
        });

    }
}
