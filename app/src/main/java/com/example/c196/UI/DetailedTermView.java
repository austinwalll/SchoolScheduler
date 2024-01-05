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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Course;
import com.example.c196.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedTermView extends AppCompatActivity {
    private Repository repository;
    private int termID;
    private String termTitle;
    private String startDate;
    private String endDate;

    EditText editTermTitle;
    TextView editStartDate;
    TextView editEndDate;
    DatePickerDialog.OnDateSetListener startingDate;
    DatePickerDialog.OnDateSetListener endingDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term_view);

        repository = new Repository(getApplication());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termID = extras.getInt("termID", -1);
        }

        editTermTitle = findViewById(R.id.termTitleET);
        editStartDate = findViewById(R.id.startDateTV);
        editEndDate = findViewById(R.id.endDateTV);
        FloatingActionButton fbaAddCourse = findViewById(R.id.floatingActionButton3);
        fbaAddCourse.setOnClickListener(view -> {
            Intent intent = new Intent(DetailedTermView.this, AddCourse.class);
            intent.putExtra("parentActivity", "DetailedTermView");
            termID = getIntent().getIntExtra("termID", 0);
            intent.putExtra("termID", termID);
            startActivity(intent);
        });
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startingDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }
        };
        endingDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelEnd();
            }
        };
        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editStartDate.getText().toString();
                if(info.equals(""))info="02/10/22";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTermView.this, startingDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editEndDate.getText().toString();
                if(info.equals(""))info="02/10/22";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTermView.this, endingDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termTitle = getIntent().getStringExtra("termTitle");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        editTermTitle.setText(termTitle);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        displayAssociatedCourses();

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
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Repository repository = new Repository(getApplication());
        editTermTitle = findViewById(R.id.termTitleET);
        editStartDate = findViewById(R.id.startDateTV);
        editEndDate = findViewById(R.id.endDateTV);

        termTitle = editTermTitle.getText().toString();
        startDate = editStartDate.getText().toString();
        endDate = editEndDate.getText().toString();

        termID = getIntent().getIntExtra("termID", 0);

        Term term = new Term(termID, termTitle, startDate, endDate);

        if (item.getItemId() == R.id.saveTermMenu){
            repository.update(term);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Term has been updated");
            builder.setMessage("You have successfully updated " +termTitle);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (item.getItemId() == R.id.deleteTermMenu) {
            List<Course> associatedCourses = repository.getAssociatedCourses(termID);
            if (associatedCourses.isEmpty()) {
                repository.delete(term);
                finish();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cannot Delete Term");
                builder.setMessage("You cannot delete a term that has associated courses.");
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.notifyTermMenu){
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
                Intent startIntent = new Intent(DetailedTermView.this, MyReceiver.class);
                startIntent.putExtra("key", termTitle + " starts today! Good luck!");
                Intent endIntent = new Intent(DetailedTermView.this, MyReceiver.class);
                endIntent.putExtra("key", termTitle + " ends today!");
                PendingIntent startSender = PendingIntent.getBroadcast(DetailedTermView.this, ++HomeScreen.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                PendingIntent endSender = PendingIntent.getBroadcast(DetailedTermView.this, ++HomeScreen.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);}
            catch (Exception e){

            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification set");
            builder.setMessage("You have successfully set notifications for " + termTitle);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return true;
    }



    private void displayAssociatedCourses() {
        List<Course> associatedCourses = repository.getAssociatedCourses(termID);
        RecyclerView recyclerView = findViewById(R.id.detailedTermRV);
        DetailedTermAdapter detailedTermAdapter = new DetailedTermAdapter(this);
        recyclerView.setAdapter(detailedTermAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedTermAdapter.setAssociatedCourses(associatedCourses);
    }

    @Override
    protected void onResume(){

        super.onResume();
        Bundle extras = getIntent().getExtras();
        int termID = extras.getInt("termID");
        List<Course> associatedCourses = repository.getAssociatedCourses(termID);
        RecyclerView recyclerView = findViewById(R.id.detailedTermRV);
        final DetailedTermAdapter detailedTermAdapter= new DetailedTermAdapter(this);
        recyclerView.setAdapter(detailedTermAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedTermAdapter.setAssociatedCourses(associatedCourses);
    }
}