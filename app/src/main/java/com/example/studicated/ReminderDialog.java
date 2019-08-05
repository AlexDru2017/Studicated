package com.example.studicated;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class ReminderDialog extends AppCompatDialogFragment {
    private EditText chooseTime;
    private EditText chooseDate;
    private EditText chooseTitle;
    private EditText chooseText;
    private ReminderDialog.ReminderDialogListener listener;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ReminderDialog.ReminderDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement ReminderDialog");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reminder_dialog, null);
        chooseTitle = view.findViewById(R.id.reminderDialogTitle);
        chooseText = view.findViewById(R.id.reminderDialogText);
        chooseDate = view.findViewById(R.id.reminderDialogDate);

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year, month, day);
                mDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDatePickerDialog.show();

            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String mDate = dayOfMonth + "/" + month + "/" + year;
                chooseDate.setText(mDate);
            }
        };


        chooseTime = view.findViewById(R.id.reminderDialogHour);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, 0, 0, true);
                timePickerDialog.show();

            }
        });

        builder.setView(view).setTitle("Add new reminder")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reminderTitle = chooseTitle.getText().toString();
                        String reminderDate = chooseDate.getText().toString();
                        String reminderHour = chooseTime.getText().toString();
                        String reminderText = chooseText.getText().toString();
                        Log.d("onClick in Dialog:", reminderTitle + " " + reminderHour + " " + reminderDate + " " + reminderText);
                        listener.applyTextsFromReminder(reminderTitle, reminderHour, reminderDate, reminderText);
                    }
                });


        return builder.create();
    }


    public interface ReminderDialogListener {
        void applyTextsFromReminder(String title, String hour, String date, String text);
    }
}

