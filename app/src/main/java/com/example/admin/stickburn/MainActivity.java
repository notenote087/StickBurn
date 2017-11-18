package com.example.admin.stickburn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button B_confirm ;
    EditText w , h , age;
    // sex

    int wi , hi ;


/*
public void aleatDetail(String glass,String bottle){
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Main.this);
        builder.setTitle("                      รู้หรือไม่?");
        builder.setMessage("\nขวด 1 ขวด ลดคาร์บอนไดออกไซด์ได้ " +bottle+ " กรัมCO2\n" +
                "\nแก้ว 1 ใบ  ลดคาร์บอนไดออกไซด์ได้ "+ glass +" กรัมCO2");
        builder.setNegativeButton("ตกลง", null);
        builder.show();


    }

* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double bmi_value = 0;
        String bmi_result = "";

        CallFucntion c = new CallFucntion();

        findID();
        wi = Integer.parseInt(String.valueOf(w));
        hi = Integer.parseInt(String.valueOf(h)) ;
       bmi_value =  c.CalBMI(wi,hi);
       bmi_result  = c.ResultBMI(bmi_value);

    }

    public void findID(){
        B_confirm = (Button) findViewById(R.id.button);
        w = (EditText) findViewById(R.id.w);
        h = (EditText) findViewById(R.id.h);
        age = (EditText) findViewById(R.id.age);

    }
}
