package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Course;
import com.example.c196.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list2);
        FloatingActionButton fbaAddTerm = findViewById(R.id.floatingActionButton2);
        fbaAddTerm.setOnClickListener(view -> {
            Intent intent=new Intent(TermList.this, AddTerm.class);
            intent.putExtra("parentActivity", "TermList");
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termListRV);
        final TermListAdapter termListAdapter= new TermListAdapter(this);
        recyclerView.setAdapter(termListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termListAdapter.setTerms(allTerms);
    }

    @Override
    protected void onResume(){

        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termListRV);
        final TermListAdapter termListAdapter= new TermListAdapter(this);
        recyclerView.setAdapter(termListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termListAdapter.setTerms(allTerms);
    }
}