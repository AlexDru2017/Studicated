package com.example.studicated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GPAActivity extends AppCompatActivity implements NewCourseDialog.NewCourseDialogListener {
    private int semesterNumber;
    ListView simpleList;
    ArrayList<Course> courseList;
    CustomAdapter customAdapter;
    TextView gpaText;
    TextView semesterText;
    double gpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        semesterNumber=0;
        gpaText = (TextView) findViewById(R.id.gpaText);
        gpaText.setText(null);
        semesterText=(TextView) findViewById(R.id.semesterText);
        semesterText.setText(Integer.toString(semesterNumber));
        final Button nextButton = (Button) findViewById(R.id.nextSem);
        Button prevButton = (Button) findViewById(R.id.prevSem);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSemester();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSemester();
            }
        });

        courseList=new ArrayList<Course>();
        Button addCourse = (Button) findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "Add course button was clicked");
                NewCourseDialog newCourse = new NewCourseDialog();
                newCourse.show(getSupportFragmentManager(),"New Course Dialog");
            }
        });
        simpleList = (ListView) findViewById(R.id.ListView);

        customAdapter = new CustomAdapter(getApplicationContext(), courseList);
        simpleList.setAdapter(customAdapter);

    }

    @Override
    public void applyTexts(String name, String credit, String grade) {
        Log.d("Applying texts",name+" "+credit+" "+grade);
        Course newCourse=new Course(name,credit,grade);
        courseList.add(newCourse);
        customAdapter.notifyDataSetChanged();
        calculateGPA();
    }
    private void calculateGPA(){
        double grades=0;
        double credits=0;

        for(Course g: courseList){
            grades+=Integer.parseInt(g.getGrade())*Double.parseDouble(g.getCredit());
            credits+=Double.parseDouble(g.getCredit());

        }
        gpa=grades/credits;
        gpaText.setText(String.format("%.2f", gpa));

    }
    private void nextSemester(){
        semesterNumber++;
        if(semesterNumber>10){
            Toast.makeText(GPAActivity.this, "Semester cannot be greater than 10",
                    Toast.LENGTH_LONG).show();
            semesterNumber=10;
        }
        semesterText.setText(Integer.toString(semesterNumber));

    }
    private void previousSemester(){
        semesterNumber--;
        if(semesterNumber<1){
            Toast.makeText(GPAActivity.this, "Semester cannot be lower than one",
                    Toast.LENGTH_LONG).show();
            semesterNumber=1;

        }
        semesterText.setText(Integer.toString(semesterNumber));
    }
}
