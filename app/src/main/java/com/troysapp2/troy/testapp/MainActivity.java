package com.troysapp2.troy.testapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    //variables yo
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView updateText;
    Context context;
    boolean greaterThan12;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.troysapp2.troy.testapp.R.layout.activity_main);
        this.context = this;

        //time picker initialization
        alarmTimePicker = (TimePicker) findViewById(com.troysapp2.troy.testapp.R.id.timePicker1);

        //alarm manager initilization
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize text update box
        updateText = (TextView) findViewById(com.troysapp2.troy.testapp.R.id.update_text);

        //create an instance of calendar
        final Calendar calendar = Calendar.getInstance();

        //initialize buttons to start and stop alarm
        Button startAlarm = (Button) findViewById(com.troysapp2.troy.testapp.R.id.button);

        //create an intent to the alarm receiver
        final Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);


        //create onClick listener to start and stop the alarms
        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set calendar to the instance with the hour and minutes that we picked on the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                //get string values of hour and minute
                int hour = alarmTimePicker.getCurrentHour();
                int minute = alarmTimePicker.getCurrentMinute();
                //convert int values to string
                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                //if statement to make sure display is correct
                if (hour > 12) {
                    hourString = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    minuteString = "0" + String.valueOf(minute);
                }

                setAlarmText("Alarm set to: " + hourString + ":" +  minuteString);

                //put in extra string into myIntent
                //tells the clock that you pressed the alarmOn button
                //extra string is passed into receiver
                myIntent.putExtra("extra", "alarm on");

                //create a pending intent that delays the intent until specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //now to set alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }



        });

        Button endAlarm = (Button) findViewById(com.troysapp2.troy.testapp.R.id.button2);

        endAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //method that changes the updateText text box
                setAlarmText("Alarm Off");

                //put extra string into myIntent
                //tells the clock that you pressed the alarmOff button
                //extra string is passed into receiver
                myIntent.putExtra("extra","alarm off");

                //stop the ringtone
                sendBroadcast(myIntent);

                if(pendingIntent != null)
                    alarmManager.cancel(pendingIntent);
            }
        });

    }

    public void setAlarmText(String input){
        updateText.setText(input);
    }


}
