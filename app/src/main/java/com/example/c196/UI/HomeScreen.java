package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.c196.R;

public class HomeScreen extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button terms=findViewById(R.id.button);
//        Button courses=findViewById(R.id.button3);
//        Button assessments=findViewById(R.id.button4);
        terms.setOnClickListener(view -> {
            Intent intent=new Intent(HomeScreen.this, TermList.class);
            intent.putExtra("test", "Information sent");
            startActivity(intent);
        });
//        courses.setOnClickListener(view -> {
//            Intent intent=new Intent(HomeScreen.this, CourseList.class);
//            intent.putExtra("test", "Information sent");
//            startActivity(intent);
//        });
//        assessments.setOnClickListener(view -> {
//            Intent intent=new Intent(HomeScreen.this, AssessmentList.class);
//            intent.putExtra("test", "Information sent");
//            startActivity(intent);
//        });

    }
}