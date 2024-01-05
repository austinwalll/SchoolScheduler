package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessment;
import com.example.c196.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailedAssessmentView extends AppCompatActivity {
    private Repository repository;
    private String assessmentTitle;
    private boolean assessmentType;
    private int course;
    private String dueDate;
    private int assessmentID;

    EditText editAssessmentTitle;
    RadioButton performanceBtn;
    RadioButton objectiveBtn;
    EditText editCourse;
    TextView editDueDate;


    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment_view);
        repository = new Repository(getApplication());

        editAssessmentTitle = findViewById(R.id.assessmentTitleET);
        performanceBtn = findViewById(R.id.performanceRB);
        objectiveBtn = findViewById(R.id.objectiveRB);
        editCourse = findViewById(R.id.courseET);
        editDueDate = findViewById(R.id.dueDateTV);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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
                new DatePickerDialog(DetailedAssessmentView.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        course = getIntent().getIntExtra("course", 0);
        dueDate = getIntent().getStringExtra("dueDate");
        assessmentType = getIntent().getBooleanExtra("assessmentType", false);
        String strCourse = String.valueOf(course);

        if (assessmentType == true){
            performanceBtn.setChecked(true);
        } else {
            objectiveBtn.setChecked(true);
        }
        editAssessmentTitle.setText(assessmentTitle);
        editCourse.setText(strCourse);
        editDueDate.setText(dueDate);

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

    public boolean onOptionsItemSelected(MenuItem item){
        Repository repository = new Repository(getApplication());
        editAssessmentTitle = findViewById(R.id.assessmentTitleET);
        editCourse = findViewById(R.id.courseET);
        editDueDate = findViewById(R.id.dueDateTV);
        performanceBtn = findViewById(R.id.performanceRB);

        assessmentTitle = editAssessmentTitle.getText().toString();
        String strCourse = editCourse.getText().toString();
        if (performanceBtn.isChecked()){
            assessmentType = true;
        } else assessmentType = false;
        course = Integer.parseInt(strCourse);
        dueDate = editDueDate.getText().toString();

        assessmentID = getIntent().getIntExtra("assessmentID", 0);

        Assessment assessment = new Assessment(assessmentID, assessmentTitle, assessmentType, course, dueDate);

        if (item.getItemId() == R.id.saveAssessmentMenu){
            repository.update(assessment);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Assessment has been updated");
            builder.setMessage("You have successfully updated " + assessmentTitle);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();


        } else if (item.getItemId() == R.id.deleteAssessmentMenu) {
            repository.delete(assessment);

            finish();
        }
        if (item.getItemId() == R.id.notifyAssessmentMenu){
            String startDateFromScreen = editDueDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDueDate = null;
            try {
                myDueDate = sdf.parse(startDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                Long trigger = myDueDate.getTime();
                Intent intent = new Intent(DetailedAssessmentView.this, MyReceiver.class);
                intent.putExtra("key", assessmentTitle + " is due today! Good luck!");
                PendingIntent sender = PendingIntent.getBroadcast(DetailedAssessmentView.this, ++HomeScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
            catch (Exception e){
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification set");
            builder.setMessage("You have successfully set notifications for " + assessmentTitle);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }
}