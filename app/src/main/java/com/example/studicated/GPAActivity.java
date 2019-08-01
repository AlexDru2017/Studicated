package com.example.studicated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GPAActivity extends AppCompatActivity implements NewCourseDialog.NewCourseDialogListener,EditCourseDialog.EditCourseDialogListener {
    private int semesterNumber;
    ArrayList<Semester> semestersList;
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

        loadSemesters(); //loads all the semesters' grades
        semesterNumber = 1; //maybe start from the last saved semester?

        gpaText = (TextView) findViewById(R.id.gpaText);
        semesterText = (TextView) findViewById(R.id.semesterText);
        Button nextButton = (Button) findViewById(R.id.nextSem);
        Button prevButton = (Button) findViewById(R.id.prevSem);
        simpleList = (ListView) findViewById(R.id.ListView);
        Button addCourse = (Button) findViewById(R.id.addCourse);
        ImageView editImage= (ImageView) findViewById(R.id.edit);

        gpaText.setText(null); //CURRENTLY NULL, IF THERE ARE GRADES SAVED CALCULATE THE GPA!
        semesterText.setText(Integer.toString(semesterNumber));





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


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "Add course button was clicked");
                NewCourseDialog newCourse = new NewCourseDialog();
                newCourse.show(getSupportFragmentManager(), "New Course Dialog");
            }
        });


        customAdapter = new CustomAdapter(getApplicationContext(), courseList , getSupportFragmentManager());
        simpleList.setAdapter(customAdapter);


    }

    @Override
    public void applyTexts(String name, String credit, String grade) {
        Log.d("Applying texts", name + " " + credit + " " + grade);
        Course newCourse = new Course(name, credit, grade);
        courseList.add(newCourse);
        customAdapter.notifyDataSetChanged();
        calculateGPA();
    }
    @Override
    public void applyTextsFromEdit(String name, String credit, String grade, int pos) {
        Log.d("Applying texts", name + " " + credit + " " + grade);
        courseList.get(pos).setCredit(credit);
        courseList.get(pos).setGrade(grade);
        courseList.get(pos).setName(name);
        customAdapter.notifyDataSetChanged();
        calculateGPA();
    }

    private void calculateGPA() {
        double grades = 0;
        double credits = 0;
        for (Semester s : semestersList) {
            for (Course g : s.getCourses()) {

                grades += Integer.parseInt(g.getGrade()) * Double.parseDouble(g.getCredit());
                credits += Double.parseDouble(g.getCredit());

            }
        }
        gpa = grades / credits;
        gpaText.setText(String.format("%.2f", gpa));

    }

    private void nextSemester() {
        semesterNumber++;
        if (semesterNumber > 9) {
            Toast.makeText(GPAActivity.this, "Semester cannot be greater than 9",
                    Toast.LENGTH_LONG).show();
            semesterNumber = 9;
        }
        updateListView();

    }

    private void previousSemester() {
        semesterNumber--;
        if (semesterNumber < 1) {
            Toast.makeText(GPAActivity.this, "Semester cannot be lower than one",
                    Toast.LENGTH_LONG).show();
            semesterNumber = 1;
        }
        updateListView();

    }

    private void loadSemesters() {
        semestersList = new ArrayList<Semester>();
        for (int i = 0; i < 10; i++) {
            semestersList.add(new Semester(i + 1));
        }
        courseList = semestersList.get(1).getCourses();

    }

    private void updateListView(){
        courseList = semestersList.get(semesterNumber).getCourses();
        customAdapter.swapSemesters(courseList);
        customAdapter.notifyDataSetChanged();
        semesterText.setText(Integer.toString(semesterNumber));
    }
}
