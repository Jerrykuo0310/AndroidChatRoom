package com.example.assignment1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotifyUtils {
    static public void createNotification(Context context, String title, String content) {
        NotificationManager s_notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = null;
        Intent intent = new Intent();

        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources resources = context.getResources();
        int smallIcon = resources.getIdentifier("ic_launcher", "mipmap", context.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String id = context.getPackageName();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            CharSequence name = context.getString(R.string.app_name);
            NotificationChannel mChannel =new NotificationChannel(id, name, importance);
            mChannel.enableLights(true);
            mChannel.setDescription("notice");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            s_notificationManager.createNotificationChannel(mChannel);

            Notification.Builder builder = new Notification.Builder(context, id);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setOngoing(false)
                    .setSmallIcon(smallIcon)
                    .setWhen(System.currentTimeMillis());
            notification =  builder.build();
        }else if (Build.VERSION.SDK_INT >= 23) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(smallIcon)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setWhen(System.currentTimeMillis());
            notification = builder.build();
        } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setOngoing(false)
                    .setSmallIcon(smallIcon)
                    .setWhen(System.currentTimeMillis());
            notification =  builder.build();
        }
        if(notification != null){
            s_notificationManager.notify(0, notification);
        }
    }
}
