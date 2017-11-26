package com.example.admin.stickburn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3500;
    private static final String Pref = "Pref";
    SharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        share = getSharedPreferences(Pref, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               String checkUser = share.getString("name","");
                if(checkUser.isEmpty()){
                    Intent main = new Intent(Splash.this,MainActivity.class);
                    startActivity(main);
                    finish();

                }else{


                Intent main = new Intent(Splash.this,Detail.class);
                startActivity(main);
                finish();

                }
            }
        },SPLASH_TIME_OUT);



    }
}
