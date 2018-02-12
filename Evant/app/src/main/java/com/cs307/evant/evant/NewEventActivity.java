package com.cs307.evant.evant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity {

    boolean priv = false;
    String title = "";
    String address = "";
    String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Calendar calendar = Calendar.getInstance();
        final Calendar clock = Calendar.getInstance();
        final EditText dText = (EditText) findViewById(R.id.dateText);
        final EditText tText = (EditText) findViewById(R.id.timeText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText addrText = (EditText) findViewById(R.id.locationText);
        final EditText descText = (EditText) findViewById(R.id.descriptionText);
        final Switch prSwitch = (Switch) findViewById(R.id.privSwitch);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleText.getText().toString();
                address = addrText.getText().toString();
                description = descText.getText().toString();
                priv = prSwitch.isChecked();
                if (title.length() > 0 && address.length() > 0 && description.length() > 0 && dText.getText().length() > 0 && tText.getText().length() > 0) {
                    Snackbar.make(view, "Event added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dText, calendar);
            }
        };

        dText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewEventActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int min) {
                clock.set(Calendar.HOUR, hour);
                clock.set(Calendar.MINUTE, min);
                updateLabelTime(tText, clock);
            }
        };

        tText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewEventActivity.this, time, clock
                        .get(clock.HOUR), clock.get(clock.MINUTE),
                        false).show();
            }
        });
    }

    private void updateLabel(EditText dText, Calendar calendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dText.setText(sdf.format(calendar.getTime()));
    }

    private void updateLabelTime(EditText tText, Calendar clock) {
        String myFormat = "hh:mm aa"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tText.setText(sdf.format(clock.getTime()));
    }
}
