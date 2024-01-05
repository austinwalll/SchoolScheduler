package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessment;
import com.example.c196.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailedCourseView extends AppCompatActivity {
    String courseName;
    int term;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhoneNum;
    String instructorEmail;
    int courseID;

    EditText editCourseName;
    EditText editTermNum;
    TextView editStartDate;
    TextView editEndDate;
    Spinner editStatus;
    EditText editInstructorName;
    EditText editInstructorPhoneNum;
    EditText editInstructorEmail;
    EditText editNotes;
    private Repository repository;
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
        setContentView(R.layout.activity_class_details);

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

        FloatingActionButton fbaAddAssessment = findViewById(R.id.floatingActionButton5);
        fbaAddAssessment.setOnClickListener(view -> {
            Intent intent = new Intent(DetailedCourseView.this, AddAssessment.class);
            intent.putExtra("parentActivity", "DetailedCourseView");
            courseID = getIntent().getIntExtra("courseID", 0);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        int courseID;
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            if (extras == null) {
                courseID = Integer.parseInt(null);
            } else {
                courseID = extras.getInt("courseID");
            }
        } else {
            courseID = (Integer) savedInstanceState.getSerializable("courseID");
        }
        List<Assessment> associatedAssessments = repository.getAssociatedAssessments(courseID);
        RecyclerView recyclerView = findViewById(R.id.courseDetailsRV);
        final DetailedCourseAdapter detailedCourseAdapter= new DetailedCourseAdapter(this);
        recyclerView.setAdapter(detailedCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedCourseAdapter.setAssociatedAssessment(associatedAssessments);

        editCourseName = findViewById(R.id.courseTitleET);
        editTermNum = findViewById(R.id.termNumET);
        editStartDate = findViewById(R.id.startDateTV);
        editEndDate = findViewById(R.id.endDateTV);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStatus = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStatus.setAdapter(adapter);

        editInstructorName = findViewById(R.id.instructorNameET);
        editInstructorPhoneNum = findViewById(R.id.instructorPhoneNumET);
        editInstructorEmail = findViewById(R.id.instructorEmailET);
        editNotes = findViewById(R.id.Notes);

        courseName = getIntent().getStringExtra("courseName");
        term = getIntent().getIntExtra("termID", 0);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhoneNum = getIntent().getStringExtra("instructorPhoneNumber");
        instructorEmail = getIntent().getStringExtra("instructorEmail");

        editCourseName.setText(courseName);
        editTermNum.setText(String.valueOf(term));
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        int position = adapter.getPosition(status);
        editStatus.setSelection(position);
        editInstructorName.setText(instructorName);
        editInstructorPhoneNum.setText(instructorPhoneNum);
        editInstructorEmail.setText(instructorEmail);

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
                        DetailedCourseView.this,
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
                        DetailedCourseView.this,
                        endDateListener,
                        myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Repository repository = new Repository(getApplication());
        editCourseName = findViewById(R.id.courseTitleET);
        editTermNum = findViewById(R.id.termNumET);
        editStartDate = findViewById(R.id.startDateTV);
        editEndDate = findViewById(R.id.endDateTV);
        editStatus = findViewById(R.id.spinnerStatus);

        editInstructorName = findViewById(R.id.instructorNameET);
        editInstructorPhoneNum = findViewById(R.id.instructorPhoneNumET);
        editInstructorEmail = findViewById(R.id.instructorEmailET);
        editNotes = findViewById(R.id.Notes);

        courseName = editCourseName.getText().toString();
        String strTerm = editTermNum.getText().toString();
        term = Integer.parseInt(strTerm);
        startDate = editStartDate.getText().toString();
        endDate = editEndDate.getText().toString();
        status = editStatus.getSelectedItem().toString();
        instructorName = editInstructorName.getText().toString();
        instructorPhoneNum = editInstructorPhoneNum.getText().toString();
        instructorEmail = editInstructorEmail.getText().toString();

        courseID = getIntent().getIntExtra("courseID", 0);

        Course course = new Course(courseID, courseName, startDate, endDate, status, instructorName, instructorPhoneNum, instructorEmail, term);

        if (item.getItemId() == R.id.saveCourseMenu){
            repository.update(course);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Course has been updated");
            builder.setMessage("You have successfully updated " + courseName);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (item.getItemId() == R.id.deleteCourseMenu) {
            repository.delete(course);

            finish();
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.shareCourseMenu) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
            shareIntent.putExtra(Intent.EXTRA_TITLE, courseName +" Notes");
            shareIntent.setType("text/plain");

            Intent sendIntent = Intent.createChooser(shareIntent, null);
            startActivity(sendIntent);
            return true;
        }
        if (item.getItemId() == R.id.notifyCourseMenu){
            String startDateFromScreen = editStartDate.getText().toString();
            String endDateFromScreen = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;
            try {
                myStartDate = sdf.parse(startDateFromScreen);
                myEndDate = sdf.parse(endDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                Long startTrigger = myStartDate.getTime();
                Long endTrigger = myEndDate.getTime();
                Intent startIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
                startIntent.putExtra("key", courseName + " starts today! Good luck!");
                Intent endIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
                endIntent.putExtra("key", courseName + " ends today! Make sure to turn everything in!");
                PendingIntent startSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++HomeScreen.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                PendingIntent endSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++HomeScreen.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);}
            catch (Exception e){

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification set");
            builder.setMessage("You have successfully set notifications for " + courseName);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return true;
    }
    @Override
    protected void onResume(){

        super.onResume();
        Bundle extras = getIntent().getExtras();
        int courseID = extras.getInt("courseID");
        List<Assessment> associatedAssessments = repository.getAssociatedAssessments(courseID);
        RecyclerView recyclerView = findViewById(R.id.courseDetailsRV);
        final DetailedCourseAdapter detailedCourseAdapter= new DetailedCourseAdapter(this);
        recyclerView.setAdapter(detailedCourseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedCourseAdapter.setAssociatedAssessment(associatedAssessments);
    }
}