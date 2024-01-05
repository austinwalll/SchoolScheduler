package com.example.c196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM COURSES WHERE term=:term ORDER BY courseID ASC")
    List<Course> getAssociatedCourses(int term);
}
