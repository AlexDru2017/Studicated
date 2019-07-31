package com.example.studicated;

import java.util.ArrayList;

public class Semester {
    int semesterNum;
    ArrayList<Course> courses;

    public Semester(int semesterNum) {
        this.semesterNum = semesterNum;
        courses=new ArrayList<Course>();
    }

    public Semester(int semesterNum, ArrayList<Course> courses) {
        this.semesterNum = semesterNum;
        this.courses = courses;
    }

    public int getSemesterNum() {
        return semesterNum;
    }

    public void setSemesterNum(int semesterNum) {
        this.semesterNum = semesterNum;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
