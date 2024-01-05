package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddAssessment extends AppCompatActivity {
    Repository repository;

    String assessmentTitle;
    boolean assessmentType;
    int course;
    String dueDate;

    EditText editAssessmentTitle;
    RadioButton editPerformance;
    EditText editCourse;
    TextView editDueDate;

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        String parentActivity = getIntent().getStringExtra("parentActivity");

        if ("AssessmentList".equals(parentActivity)) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        if ("DetailedCourseView".equals(parentActivity)) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Repository repository = new Repository(getApplication());
        editAssessmentTitle = findViewById(R.id.assessmentTitleET);
        editPerformance = findViewById(R.id.performanceRB);
        editCourse = findViewById(R.id.courseET);
        editDueDate = findViewById(R.id.dueDateTV);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar today = Calendar.getInstance();
        String defaultDate = sdf.format(today.getTime());
        editDueDate.setText(defaultDate);
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };
        editDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editDueDate.getText().toString();
                if(info.equals(""))info="02/10/22";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        int courseID = getIntent().getExtras().getInt("courseID", 0);
        if (courseID != 0) editCourse.setText(String.valueOf(courseID));

        Button addAssessmentBtn = findViewById(R.id.addAssessmentBTN);
        addAssessmentBtn.setOnClickListener(view -> {
            assessmentTitle = editAssessmentTitle.getText().toString();
            if (editPerformance.isChecked()){
                assessmentType = true;
            } else {
                assessmentType = false;
            }
            course = Integer.parseInt(String.valueOf(editCourse.getText()));
            dueDate = editDueDate.getText().toString();
            Assessment assessment = new Assessment(assessmentTitle, assessmentType, course, dueDate);
            repository.insert(assessment);

            finish();
        });
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDueDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}