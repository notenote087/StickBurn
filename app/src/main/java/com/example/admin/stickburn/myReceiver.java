package com.example.admin.stickburn;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 30/11/2560.
 */

public class myReceiver extends BroadcastReceiver {
    SharedPreferences share ;
    SharedPreferences.Editor editor;



    @Override
    public void onReceive(Context context, Intent intent) {

          Intent intent1 = new Intent(context,Detail.class);
           PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1, PendingIntent.FLAG_CANCEL_CURRENT);
           NotificationCompat.Builder builder =
                   new NotificationCompat.Builder(context.getApplicationContext())
                           .setSmallIcon(R.mipmap.ic_launcher)
                           .setContentTitle("StickBurn")
                           .setContentText("สวัสดีวันใหม่ คิดอะไรไม่ออกให้นึกถึงเรา")
                           .setAutoCancel(true)
                           .setContentIntent(pendingIntent);

           NotificationManager notificationManager =
                   (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
           notificationManager.notify(1, builder.build());



           editor = share.edit();
           editor.putString("callory_day", "0");
           editor.commit();

    }
}
