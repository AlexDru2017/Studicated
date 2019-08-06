package com.example.studicated;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Course> item;
    LayoutInflater inflter;

    FragmentManager fm;
    TextView name;
    TextView credit;
    TextView grade;
    int pos;

    public CustomAdapter(Context applicationContext, ArrayList<Course> item, FragmentManager fragMan) {
        this.context = context;
        this.item = item;
        fm = fragMan;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void swapSemesters(ArrayList<Course> currentSemester) {
        item = currentSemester;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_item, null);
        name = view.findViewById(R.id.reminderDialogTitle);
        credit = view.findViewById(R.id.reminder_date);
        grade = view.findViewById(R.id.grade);
        ImageView editImage = view.findViewById(R.id.edit);
        editImage.setTag(i);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Image View position: ", v.getTag().toString());
                pos = Integer.parseInt(v.getTag().toString());
                Log.d("current item: ", item.get(pos).toString());
                Bundle args = new Bundle();
                args.putString("name", item.get(pos).getName());
                args.putString("grade", item.get(pos).getGrade());
                args.putString("credits", item.get(pos).getCredit());
                args.putInt("position", pos);
                EditCourseDialog editCourse = new EditCourseDialog();
                editCourse.setArguments(args);
                editCourse.show(fm, "Edit_Dialog");

            }
        });
        name.setText(item.get(i).getName());
        credit.setText(item.get(i).getCredit());
        grade.setText(item.get(i).getGrade());
        return view;
    }


}