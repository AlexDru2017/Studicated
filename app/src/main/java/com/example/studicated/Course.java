package com.example.studicated;

public class Course {
    public Course(String name,String credit,String grade){
        this.name=name;
        this.credit=credit;
        this.grade=grade;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    String name;
    String credit;
    String grade;

}
