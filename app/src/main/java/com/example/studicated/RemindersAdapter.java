package com.example.studicated;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class RemindersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Reminder> item;
    private LayoutInflater inflter;
    private FragmentManager fm;
    private TextView title;
    private TextView date;



    public RemindersAdapter(Context applicationContext, ArrayList<Reminder> item) {
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
        view = inflter.inflate(R.layout.reminders_item, null);
        title = view.findViewById(R.id.reminderDialogTitle);
        date =  view.findViewById(R.id.reminder_date);


        title.setText(item.get(i).getTitle());
        date.setText(item.get(i).getDate());

        return view;
    }
}

