package com.example.appservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {

    public static final String TAG = CountService.class.getSimpleName();
    public static final String TIME = "TIME";

    private ScheduledExecutorService mScheduledExecutorService;

    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;
    public CountService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = getNotificationBuilder();

        //Configuration builder
        mBuilder.setContentTitle("CountService notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        // For API version 26 and above create notification with channel
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(this);
        } else {
            String MY_CHANNEL_ID = "my_channel_id";
            if (mManager.getNotificationChannel(MY_CHANNEL_ID) == null) {

                NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, "text for user", NotificationManager.IMPORTANCE_LOW);
                mManager.createNotificationChannel(channel);
            }

            return new NotificationCompat.Builder(this, MY_CHANNEL_ID);
        }




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");

        startForeground(123, getNotification("Start notification"));

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                mManager.notify(123,getNotification("time is " + currentTimeMillis));
                Log.d(TAG, "run: " + currentTimeMillis);
                Intent intentToSend = new Intent(SimpleReceiver.SIMPLE_ACTION);
                intentToSend.putExtra(TIME, currentTimeMillis);
                sendBroadcast(intentToSend);

            }
        },1000, 4000, TimeUnit.MILLISECONDS);


        return START_STICKY;
    }

    @NonNull
    private Notification getNotification(String content_text) {
        Intent intent = new Intent(this, TempActivity.class);
        intent.putExtra(TIME,content_text);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return mBuilder.setContentText(content_text)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mScheduledExecutorService.shutdownNow();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}