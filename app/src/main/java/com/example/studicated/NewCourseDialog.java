package com.example.studicated;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewCourseDialog extends AppCompatDialogFragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NewCourseDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement NewCourseDialogListener");
        }
    }

    private EditText courseNameText;
    private EditText courseCreditsText;
    private EditText courseGradeText;
    private NewCourseDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_course_dialog, null);
        builder.setView(view).setTitle("Add new course")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String courseName = courseNameText.getText().toString();
                        String courseCredits = courseCreditsText.getText().toString();
                        String courseGrade = courseGradeText.getText().toString();
                        listener.applyTexts(courseName,courseCredits,courseGrade);
                    }
                });
        courseNameText = view.findViewById(R.id.course_name);
        courseGradeText = view.findViewById(R.id.course_grade);
        courseCreditsText = view.findViewById(R.id.course_credit);
        return builder.create();


    }

    public interface NewCourseDialogListener {
        void applyTexts(String name, String credit, String grade);
    }
}
