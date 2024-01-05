package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessment;
import com.example.c196.entities.Course;
import com.example.c196.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        FloatingActionButton fbaAddAssessment = findViewById(R.id.floatingActionButton4);
        fbaAddAssessment.setOnClickListener(view -> {
            Intent intent=new Intent(AssessmentList.this, AddAssessment.class);
            intent.putExtra("parentActivity", "AssessmentList");
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<Assessment> allAssessments = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentListRV);
        final AssessmentListAdapter assessmentListAdapter= new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentListAdapter.setAssessments(allAssessments);
    }

    @Override
    protected void onResume(){

        super.onResume();
        List<Assessment> allAssessments = repository.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentListRV);
        final AssessmentListAdapter assessmentListAdapter= new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentListAdapter.setAssessments(allAssessments);
    }
}