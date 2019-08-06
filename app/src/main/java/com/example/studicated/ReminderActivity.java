package com.example.studicated;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReminderActivity extends AppCompatActivity implements ReminderDialog.ReminderDialogListener {

    private static final String FILE_NAME = "reminders.txt";
    private ArrayList<Reminder> remindersList;
    private RemindersAdapter remindersAdapter;
    private ListView remindersViewList;
    private AlarmManager alarm_manager;
    private Context context;
    private Calendar calendar;
    private Intent intent;

    @Override
    public void applyTextsFromReminder(String title, String hour, String date, String text) {
        if (!title.matches("") && !text.matches("") && !date.matches("") && !hour.matches("")) {
            if (!text.contains(";") && !title.contains(";")) {
                Log.d("Apply Texts:", title + " " + hour + " " + date + " " + text);
                Reminder newReminder = new Reminder(title, hour, date, text);
                remindersList.add(newReminder);
                for (int i = 0; i < remindersList.size(); i++) {
                    Log.d("Before Sort " + i, remindersList.get(i).toString());
                }
                Collections.sort(remindersList);
                for (int i = 0; i < remindersList.size(); i++) {
                    if (remindersList.get(i).getTitle().matches(newReminder.getTitle())) {
                        addNewReminder(newReminder, i);
                        break;
                    }
                }
                for (int i = 0; i < remindersList.size(); i++) {
                    Log.d("After Sort " + i, remindersList.get(i).toString());
                }
                saveDataToFile();
                remindersAdapter.notifyDataSetChanged();


            } else {
                Toast.makeText(this, "Please avoid using ;", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Missing fields", Toast.LENGTH_LONG).show();
        }
    }

    private void addNewReminder(Reminder newReminder, int i) {
        String[] date = newReminder.getDate().split("/");
        String[] time = newReminder.getHour().split(":");
        calendar.set(Calendar.YEAR, Integer.parseInt(date[2]));
        calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar.set(Calendar.SECOND, 0);
        Log.d("Alarm Receiver", "i is: "+i);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();

        intent = new Intent(context, AlarmReceiver.class);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        remindersList = new ArrayList<>();
        readDateFromFile();
        Button addReminder = findViewById(R.id.addReminder);
        remindersViewList = findViewById(R.id.remindersList);
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
                Bundle args = new Bundle();
                args.putString("title", remindersList.get(position).getTitle());
                args.putString("hour", remindersList.get(position).getHour());
                args.putString("date", remindersList.get(position).getDate());
                args.putString("text", remindersList.get(position).getText());
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
                                Collections.sort(remindersList);
                                saveDataToFile();
                                remindersAdapter.notifyDataSetChanged();
                                dialog.dismiss();

                                Log.d("Alarm Receiver", "position is: "+position);
                                PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context,position, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarm_manager.cancel(cancelPendingIntent);
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
        remindersAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void saveDataToFile() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            for (Reminder reminder : remindersList) {
                fos.write(reminder.mToString().getBytes());
                Log.d("Saved ", reminder.toString());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readDateFromFile() {
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineArrayString = line.split(";");
                remindersList.add(new Reminder(lineArrayString[0], lineArrayString[1], lineArrayString[2], lineArrayString[3]));
            }
            dataInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
