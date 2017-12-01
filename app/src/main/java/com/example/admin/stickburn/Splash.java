package com.example.admin.stickburn;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

  //  private static int SPLASH_TIME_OUT = 3500;
  private static int SPLASH_TIME_OUT = 5000;
    private static final String Pref = "Pref";
    SharedPreferences share;
    PendingIntent alarmintent ;
    AlarmManager alarmManager ;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAlarm(18,2);
        share = getSharedPreferences(Pref, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void run() {

               String checkUser = share.getString("name","");
                if(checkUser.isEmpty()){
                    Intent main = new Intent(Splash.this,MainActivity.class);
                    startActivity(main);
                    finish();

                }else{

                   // Toast.makeText(Splash.this, "sss", Toast.LENGTH_SHORT).show();

                Intent main = new Intent(Splash.this,Detail.class);
                startActivity(main);
                finish();

                }
            }
        },SPLASH_TIME_OUT);



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(int m, int h){
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(); // requires API level 24
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(android.icu.util.Calendar.HOUR_OF_DAY,h);

        calendar.set(android.icu.util.Calendar.MINUTE,m);
        Intent intent = new Intent(Splash.this,myReceiver.class);
        alarmintent = PendingIntent.getBroadcast(Splash.this,0,intent,0);
        alarmManager = (AlarmManager) Splash.this.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),alarmintent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+ 2 *1000,alarmintent);

       // Toast.makeText(Splash.this, h+"+"+m, Toast.LENGTH_SHORT).show();

    }
}
