package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        FloatingActionButton fbaAddCourse = findViewById(R.id.floatingActionButton);
        fbaAddCourse.setOnClickListener(view -> {
            Intent intent=new Intent(CourseList.this, AddCourse.class);
            intent.putExtra("parentActivity", "CourseList");
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseListRV);
        final CourseListAdapter courseListAdapter= new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseListAdapter.setCourses(allCourses);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.update){
            //Toast.makeText(CourseList.this,"Put in sample data", Toast.LENGTH_LONG).show();


        }
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onResume(){

        super.onResume();
        List<Course> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseListRV);
        final CourseListAdapter courseListAdapter= new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseListAdapter.setCourses(allCourses);
    }

}