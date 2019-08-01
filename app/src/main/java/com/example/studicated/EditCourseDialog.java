package com.example.studicated;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditCourseDialog extends AppCompatDialogFragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EditCourseDialog.EditCourseDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement EditCourseDialog");
        }
    }

    private EditText courseNameText;
    private EditText courseCreditsText;
    private EditText courseGradeText;
    private int position;
    private EditCourseDialog.EditCourseDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_course_dialog, null);

        courseNameText = view.findViewById(R.id.course_name);
        courseGradeText = view.findViewById(R.id.course_grade);
        courseCreditsText = view.findViewById(R.id.course_credit);

        builder.setView(view).setTitle("Edit course")
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
                        listener.applyTextsFromEdit(courseName,courseCredits,courseGrade,position);
                    }
                });

        if(getArguments()!=null){
            Bundle bundle = getArguments();
            courseNameText.setText((String) bundle.get("name"));
            courseGradeText.setText((String) bundle.get("grade"));
            courseCreditsText.setText((String) bundle.get("credits"));
            position=bundle.getInt("position");

        }
        return builder.create();


    }


    public interface EditCourseDialogListener {
        void applyTextsFromEdit(String name, String credit, String grade,int pos);
    }
}
