package com.troysapp2.troy.testapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class RingtonePlayingService extends Service {


    MediaPlayer mediaSong;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);



        //fetch extra string values
        String state = intent.getExtras().getString("extra");

        Log.e("RingTone state extrais ", state);


        //this converts the extra strings from the intent to startId values 0 or 1
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }

        //if else statements regarding startId

        //if there is no music playing and user pressed Alarm On
        //music should begin
        if(!this.isRunning && startId==1){
            Log.e("there is no music","and you want start");
            //create an instance of the mediaplayer
            mediaSong = MediaPlayer.create(this, com.troysapp2.troy.testapp.R.raw.bruhsoundeffect2);

            //starts ringtone
            mediaSong.start();

            this.isRunning = true;
            this.startId = 0;

            //notifications
            //set up the notification service
            NotificationManager notifyManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            //set up an intent that goes to the main Acitivty
            Intent mainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

            //set up pending intent
            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this,0,mainActivity,0);


            //make notification parameters
            Notification notificationPopup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off")
                    .setSmallIcon(com.troysapp2.troy.testapp.R.mipmap.ic_launcher)
                    .setContentText("Click me!")
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .build();

            //set up notification call command
            notifyManager.notify(0,notificationPopup);

        }

        //if there is music playing and the user pressed Alarm Off
        //music should stop
        else if(this.isRunning && startId==0){
            Log.e("there is music","and you want end");

            //stops ringtone
            mediaSong.stop();
            mediaSong.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        //bug-proofing

        //if there is no music playing and the user pressed Alarm Off
        //do nothing
        else if(!this.isRunning && startId==0){
            Log.e("there is no music", "and you want end");

            this.isRunning = false;
            this.startId = 0;
        }

        //if there is music playing and the user pressed Alarm On
        //do nothing
        else if(this.isRunning && startId==1){
            Log.e("there is music","and you want start");

            this.isRunning = true;
            this.startId = 1;
        }

        //final bug-proofing
        else {
            Log.e("else","somehow you reached this");

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Log.e("on destroy called","ta da");

        super.onDestroy();
        this.isRunning = false;
        this.startId = 0;

    }


    }


