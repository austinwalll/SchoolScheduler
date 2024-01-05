package com.example.c196.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName= "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;
    private int term;

    public Course(String courseName, String startDate, String endDate, String status, String instructorName, String instructorPhoneNumber, String instructorEmail, int term) {
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
        this.term = term;
    }
    @Ignore
    public Course(int courseID, String courseName, String startDate, String endDate, String status, String instructorName, String instructorPhoneNumber, String instructorEmail, int term){
        this.courseID = courseID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
        this.term = term;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getTerm() {
        return term;
    }

    public void setCourseTitle(int courseID) {
        this.courseID = courseID;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }


}
