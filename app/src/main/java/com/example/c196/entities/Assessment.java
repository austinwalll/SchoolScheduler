package com.example.c196.entities;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName= "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentTitle;
    private boolean assessmentType;
    private int course;
    private String dueDate;

    public Assessment(String assessmentTitle, boolean assessmentType, int course, String dueDate) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.course = course;
        this.dueDate = dueDate;
    }

    @Ignore
    public Assessment(int assessmentID, String assessmentTitle, boolean assessmentType, int course, String dueDate) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.course = course;
        this.dueDate = dueDate;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public boolean isAssessmentType() {
        return assessmentType;
    }

    public int getCourse() {
        return course;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentType(boolean assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
