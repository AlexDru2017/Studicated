package com.example.studicated;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Course> item;
    LayoutInflater inflter;
    TextView name;
    FragmentManager fm;

    public CustomAdapter(Context applicationContext, ArrayList<Course> item , FragmentManager fragMan) {
        this.context = context;
        this.item = item;
        fm=fragMan;
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
        name = (TextView) view.findViewById(R.id.courseName);
        TextView credit = (TextView) view.findViewById(R.id.credit);
        TextView grade = (TextView) view.findViewById(R.id.grade);
        ImageView editImage = (ImageView) view.findViewById(R.id.edit);
        editImage.setTag(i);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Image View position: ", v.getTag().toString());
                int pos= Integer.parseInt(v.getTag().toString());
                Log.d("current item: ", item.get(pos).toString());
                NewCourseDialog newCourse = new NewCourseDialog(item.get(pos).getName());
                newCourse.show(fm, "New Course Dialog");

            }
        });
        name.setText(item.get(i).getName());
        credit.setText(item.get(i).getCredit());
        grade.setText(item.get(i).getGrade());
        return view;
    }
}