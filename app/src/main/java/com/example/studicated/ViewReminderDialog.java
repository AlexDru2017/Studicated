package com.example.studicated;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ViewReminderDialog extends AppCompatDialogFragment {
    private EditText time;
    private EditText date;
    private EditText title;
    private EditText text;





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reminder_dialog, null);


        title=  view.findViewById(R.id.reminderDialogTitle) ;
        text =  view.findViewById(R.id.reminderDialogText);
        date =  view.findViewById(R.id.reminderDialogDate);
        time = view.findViewById(R.id.reminderDialogHour);

        title.setEnabled(false);
      //  title.setFocusable(false);

        text.setEnabled(false);
       // text.setFocusable(false);

        date.setEnabled(false);
      //  date.setFocusable(false);

        time.setEnabled(false);
       // time.setFocusable(false);


        builder.setView(view).setTitle("View reminder")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });



        if(getArguments()!=null){
            Bundle bundle = getArguments();
            title.setText((String) bundle.get("title"));
            text.setText((String) bundle.get("text"));
            date.setText((String) bundle.get("date"));
            time.setText((String) bundle.get("hour"));

        }

        return builder.create();
    }
}