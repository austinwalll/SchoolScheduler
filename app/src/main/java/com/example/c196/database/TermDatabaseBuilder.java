package com.example.c196.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.c196.dao.AssessmentDao;
import com.example.c196.dao.CourseDao;
import com.example.c196.dao.TermDao;
import com.example.c196.entities.Course;
import com.example.c196.entities.Term;
import com.example.c196.entities.Assessment;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    public abstract CourseDao courseDAO();
    public abstract TermDao termDAO();
    public abstract AssessmentDao assessmentDAO();
    private static volatile TermDatabaseBuilder INSTANCE;


    static TermDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (TermDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TermDatabaseBuilder.class, "MyTermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
