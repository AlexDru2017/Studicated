package com.example.studicated;

import android.content.Context;
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

    public CustomAdapter(Context applicationContext, ArrayList<Course> item ) {
        this.context = context;
        this.item = item;

        inflter = (LayoutInflater.from(applicationContext));
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
        TextView name = (TextView) view.findViewById(R.id.courseName);
        TextView credit = (TextView) view.findViewById(R.id.credit);
        TextView grade= (TextView) view.findViewById(R.id.grade);
        name.setText(item.get(i).getName());
        credit.setText(item.get(i).getCredit());
        grade.setText(item.get(i).getGrade());
        return view;
    }
}