package com.example.admin.stickburn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends AppCompatActivity {

    Button reset , update ;
    EditText name,w,h ,age ;
    String name_,w_,h_,age_;
    SharedPreferences share;
    SharedPreferences.Editor editor;
    private static final String Pref = "Pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findID();

        share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        editor = share.edit();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!name.getText().toString().isEmpty()){ name_ = name.getText().toString(); editor.putString("name", name_);}
                if(!w.getText().toString().isEmpty()){ w_= w.getText().toString();  editor.putString("wei", w_); }
                if(!h.getText().toString().isEmpty()){ h_= h.getText().toString();  editor.putString("hei", h_); }
                if(!age.getText().toString().isEmpty()){ age_= age.getText().toString(); editor.putString("age", age_); }


          //      editor.putString("name", name_);
              //  editor.putString("hei", h_);
             //   editor.putString("wei", w_);
             //   editor.putString("age", age_);
             //   editor = share.edit();

                //editor.putString("callory_day", Double.toString(callory_day));
                editor.apply();

                Intent intent = new Intent(Edit.this,Splash.class);
                finish();

                Detail d = new Detail();
                d.finish();

              //  d.onDestroy(); // สัส

                startActivity(intent);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double b = 0.0;
                editor.putString("callory_day", Double.toString(b));
                editor.apply();

                Intent intent = new Intent(Edit.this,Splash.class);
                finish();

                Detail d = new Detail();
                d.finish();

            //    d.onDestroy(); // สัส

                startActivity(intent);

            }
        });


    }

    public void findID(){
        reset = (Button) findViewById(R.id.reset);
        update = (Button) findViewById(R.id.update);
        name = (EditText) findViewById(R.id.name_edit);
        w = (EditText) findViewById(R.id.w_edit);
        h = (EditText) findViewById(R.id.h_edit);
        age = (EditText) findViewById(R.id.age_edit);


    }
}
