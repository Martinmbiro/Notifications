package com.ahrefs.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1_id_here";
    public static final String CHANNEL_2_ID = "channel2_id_here";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"Chat Notification", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Message Notifications");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Download Notification",NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("Download Complete Notifications");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }
    }
}
