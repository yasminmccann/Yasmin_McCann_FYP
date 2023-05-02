package com.example.fyp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Medicine Reminder:Alarm");
        } else {
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Medicine Reminder:Alarm");
        }
        wl.acquire(10 * 60 * 1000L /*10 minutes*/);

        // Play sound
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
        mediaPlayer.start();

        // Show the reminder toast message
        Toast.makeText(context, "Time to take your medicine!", Toast.LENGTH_LONG).show();
    }
}