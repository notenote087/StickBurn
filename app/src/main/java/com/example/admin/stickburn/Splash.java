package com.example.admin.stickburn;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               /* share = getSharedPreferences(Pref, Context.MODE_PRIVATE);

                editor.putString("checkAlert","1");
                editor.commit();

                checkid = share.getString("id","") ;
                checkUser = share.getString("name","");
                checkPrivilege = share.getString("Privilege","");*/
                //Toast.makeText(getApplicationContext(),checkPrivilege,Toast.LENGTH_SHORT).show();
               // if(!checkUser.isEmpty() && !checkid.isEmpty() && !checkPrivilege.isEmpty()){
                    //     Toast.makeText(getApplicationContext(),checkPrivilege,Toast.LENGTH_SHORT).show();
                //    switch (checkPrivilege){

                   /*     case "0" :
                            Intent adminpage = new Intent(Splash.this,Admin_Main.class);

                            startActivity(adminpage);
                            finish();
                            break;
                        case "1":
                            Intent userpage = new Intent(Splash.this,User_Main.class);
                            startActivity(userpage);
                            finish();
                            break;
                        case "2":
                            Intent userpage2 = new Intent(Splash.this,User_Main.class);
                            startActivity(userpage2);
                            finish();
                            break;

                    }

                }else{
                    Intent LogginIntent = new Intent(Splash.this,Login.class);
                    startActivity(LogginIntent);
                    finish();
                }*/

                Intent main = new Intent(Splash.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        },SPLASH_TIME_OUT);



    }
}
