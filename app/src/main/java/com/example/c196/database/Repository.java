package com.example.c196.database;

import android.app.Application;


import com.example.c196.dao.AssessmentDao;
import com.example.c196.dao.CourseDao;
import com.example.c196.dao.TermDao;
import com.example.c196.entities.Assessment;
import com.example.c196.entities.Course;
import com.example.c196.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private CourseDao mCourseDAO;
    private TermDao mTermDao;
    private AssessmentDao mAssessmentDao;

    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<Assessment> mAllAssessments;

    private List<Course> mAssociatedCourses;
    private List<Assessment> mAssociatedAssessments;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermDatabaseBuilder db=TermDatabaseBuilder.getDatabase(application);
        mCourseDAO=db.courseDAO();
        mTermDao=db.termDAO();
        mAssessmentDao=db.assessmentDAO();
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(() -> {
            mAllTerms=mTermDao.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAllTerms;
    }

    public List<Course> getAssociatedCourses(int term){
        databaseExecutor.execute(() -> {
            mAssociatedCourses = mCourseDAO.getAssociatedCourses(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAssociatedCourses;
    }

    public List<Course> getAllCourses(){
        databaseExecutor.execute(() -> {
            mAllCourses= mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAllCourses;
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()-> {
            mAllAssessments = mAssessmentDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAllAssessments;
    }
    public List<Assessment> getAssociatedAssessments(int course){
        databaseExecutor.execute(() -> {
            mAssociatedAssessments = mAssessmentDao.getAssociatedAssessments(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return mAssociatedAssessments;
    }


    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(() ->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(() ->{
            mTermDao.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment){
        databaseExecutor.execute(() ->{
            mAssessmentDao.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(() ->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Term term){
        databaseExecutor.execute(() ->{
            mTermDao.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(() ->{
            mAssessmentDao.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
