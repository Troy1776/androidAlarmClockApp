package com.troysapp2.troy.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver", "Yay");

        //fetch the extra string from the intent
        String getString = intent.getExtras().getString("extra");
        Log.e("what is the key?",getString);

        //create an intent to the ringtone service
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        //pass extra string from MainActivity to the RingtonePlayingService
        serviceIntent.putExtra("extra", getString);

        //start the ringtone service
        context.startService(serviceIntent);
    }
}
