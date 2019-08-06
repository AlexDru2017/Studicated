package com.example.studicated;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class GPAActivity extends AppCompatActivity implements NewCourseDialog.NewCourseDialogListener, EditCourseDialog.EditCourseDialogListener {
    public static final String SHARED_PREFS = "mySharedPrefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int semesterNumber;
    private ArrayList<Semester> semestersList;
    private ListView simpleList;
    private ArrayList<Course> courseList;
    private CustomAdapter customAdapter;
    private TextView gpaText;
    private TextView semesterText;
    private double gpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        loadSemesters(); //loads all the semesters' grades
        semesterNumber = 1; //maybe start from the last saved semester?
        gpaText = findViewById(R.id.gpaText);
        semesterText = findViewById(R.id.semesterText);
        Button nextButton = findViewById(R.id.nextSem);
        Button prevButton = findViewById(R.id.prevSem);
        simpleList = findViewById(R.id.remindersList);
        Button addCourse = findViewById(R.id.addReminder);
        ImageView editImage = findViewById(R.id.edit);

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
        simpleList.setLongClickable(true);
        simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(GPAActivity.this).create();
                alertDialog.setTitle("Delete course");
                alertDialog.setMessage("Are you sure you want to delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                courseList.remove(position);
                                calculateGPA();
                                Log.d("position", Integer.toString(position + 1));
                                updatePositionInSP(position + 1);
                                customAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                return true;
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


        customAdapter = new CustomAdapter(getApplicationContext(), courseList, getSupportFragmentManager());
        simpleList.setAdapter(customAdapter);
        loadData();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    private void updatePositionInSP(int position) {
        Map<String, String> savedData = (Map<String, String>) sharedPreferences.getAll();
        for (Map.Entry<String, String> entry : savedData.entrySet()) {
            String[] key = entry.getKey().split(",");
            if (position <= Integer.parseInt(key[1]) && semesterNumber == Integer.parseInt(key[0])) {
                sharedPreferences.edit().remove(Integer.toString(semesterNumber) + ',' + Integer.parseInt(key[1])).commit();
            }
        }

        for (int i = position; i <= courseList.size(); i++) {
            saveDataToSP(courseList.get(i - 1), i);
        }
    }

    @Override
    public void applyTexts(String name, String credit, String grade) {

        Log.d("Applying texts", name + " " + credit + " " + grade);
        if (isNameGood(name)) {
            if (isCreditGood(credit)) {
                if (isGradeGood(grade)) {
                    Course newCourse = new Course(name, credit, grade);
                    courseList.add(newCourse);
                    customAdapter.notifyDataSetChanged();
                    saveDataToSP(newCourse, courseList.size());
                    calculateGPA();
                } else {
                    Toast.makeText(this, "Grade need to be between 0-100 ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Missing Field/s", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Missing Field/s", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void applyTextsFromEdit(String name, String credit, String grade, int pos) {
        Log.d("Applying texts", name + " " + credit + " " + grade);
        if (isNameGood(name)) {
            if (isCreditGood(credit)) {
                if (isGradeGood(grade)) {
                    courseList.get(pos).setCredit(credit);
                    courseList.get(pos).setGrade(grade);
                    courseList.get(pos).setName(name);
                    Course saveCourse = new Course(name, credit, grade);
                    customAdapter.notifyDataSetChanged();
                    saveDataToSP(saveCourse, pos + 1);
                    calculateGPA();
                } else {
                    Toast.makeText(this, "Grade need to be between 0-100 ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Missing Field/s", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Missing Field/s", Toast.LENGTH_LONG).show();
        }
    }


    private boolean isNameGood(String name) {
        return (!name.matches(""));
    }

    private boolean isCreditGood(String credit) {
        return (!credit.matches(""));
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
        if (semesterNumber > 10) {
            Toast.makeText(GPAActivity.this, "Semester cannot be greater than 10",
                    Toast.LENGTH_LONG).show();
            semesterNumber = 10;
        }
        updateListView();

    }

    private void previousSemester() {
        semesterNumber--;
        if (semesterNumber < 1) {
            Toast.makeText(GPAActivity.this, "Semester cannot be lower than 1",
                    Toast.LENGTH_LONG).show();
            semesterNumber = 1;
        }
        updateListView();

    }

    /**
     * @param courseGrade
     * @return
     */
    private boolean isGradeGood(String courseGrade) {
        if (!courseGrade.matches("")) {
            return (Double.parseDouble(courseGrade) <= 100);
        }
        return false;
    }

    private void loadSemesters() {
        semestersList = new ArrayList<Semester>();
        for (int i = 0; i <= 10; i++) {
            semestersList.add(new Semester(i + 1));
        }
        courseList = semestersList.get(1).getCourses();

    }

    private void updateListView() {
        courseList = semestersList.get(semesterNumber).getCourses();
        customAdapter.swapSemesters(courseList);
        customAdapter.notifyDataSetChanged();
        semesterText.setText(Integer.toString(semesterNumber));
    }


    public void saveDataToSP(Course newCourse, int position) {
        editor = sharedPreferences.edit();
        editor.putString(Integer.toString(semesterNumber) + ',' + position, newCourse.getName() + ',' + newCourse.getCredit() + ',' + newCourse.getGrade());
        if (editor.commit())
            Log.d("Saved course:", newCourse.getName());
    }


    public void loadData() {
        editor = sharedPreferences.edit();
        Map<String, String> savedData = (Map<String, String>) sharedPreferences.getAll();
        if (savedData.size() > 0) {
            SortedSet<String> keys = new TreeSet<>(savedData.keySet());
            for (String key : keys) {
                String[] keySplitted = key.split(",");
                String[] values = savedData.get(key).split(",");
                Semester semester = semestersList.get(Integer.parseInt(keySplitted[0]));
                ArrayList<Course> courseListFromSP = semester.getCourses();
                courseListFromSP.add(Integer.parseInt(keySplitted[1]) - 1, new Course(values[0], values[1], values[2]));
            }
            calculateGPA();
            customAdapter.notifyDataSetChanged();
        }
    }

}
