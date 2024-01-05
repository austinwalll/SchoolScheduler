package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Course;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddCourse extends AppCompatActivity {
    Repository repository;
    String courseName;
    int term;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhoneNum;
    String instructorEmail;

    EditText editCourseName;
    EditText editTermNum;
    TextView editStartDate;
    TextView editEndDate;
    Spinner editStatus;
    EditText editInstructorName;
    EditText editInstructorPhoneNum;
    EditText editInstructorEmail;
    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        }
    };
    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        }
    };
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        String parentActivity = getIntent().getStringExtra("parentActivity");

        if ("CourseList".equals(parentActivity)) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        if ("DetailedTermView".equals(parentActivity)) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        editCourseName = findViewById(R.id.courseTitleET);
        editTermNum = findViewById(R.id.termNumET);
        editStartDate = findViewById(R.id.startDateTV);
        editEndDate = findViewById(R.id.endDateTV);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar today = Calendar.getInstance();
        String defaultDate = sdf.format(today.getTime());
        editStartDate.setText(defaultDate);
        editEndDate.setText(defaultDate);
        editStatus = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStatus.setAdapter(adapter);

        editStatus = findViewById(R.id.spinnerStatus);
        editInstructorName = findViewById(R.id.instructorNameET);
        editInstructorPhoneNum = findViewById(R.id.instructorPhoneNumET);
        editInstructorEmail = findViewById(R.id.instructorEmailET);
        int termID = getIntent().getExtras().getInt("termID", 0);
        if (termID != 0) editTermNum.setText(String.valueOf(termID));

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        AddCourse.this,
                        startDateListener,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        AddCourse.this,
                        endDateListener,
                        myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        Button addCourseBTN = findViewById(R.id.addCourseBTN);
        addCourseBTN.setOnClickListener(view -> {
            repository = new Repository(getApplication());
            courseName = editCourseName.getText().toString();
            String strTerm = editTermNum.getText().toString();
            term = Integer.parseInt(strTerm);
            startDate = editStartDate.getText().toString();
            endDate = editEndDate.getText().toString();
            status = editStatus.getSelectedItem().toString();
            instructorName = editInstructorName.getText().toString();
            instructorPhoneNum = editInstructorPhoneNum.getText().toString();
            instructorEmail = editInstructorEmail.getText().toString();
            Course course = new Course(courseName, startDate, endDate, status, instructorName, instructorPhoneNum, instructorEmail, term);
            repository.insert(course);

            finish();
        });


    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
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