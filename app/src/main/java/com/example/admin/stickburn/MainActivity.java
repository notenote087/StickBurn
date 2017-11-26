package com.example.admin.stickburn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Button B_confirm ;
    EditText w , h , age , name ;

    RadioButton male,female ;
    RadioGroup rg ;

    CallFucntion c = new CallFucntion();
    sqlLite sql ;

    double bmi_value = 0;
    String bmi_result = "";
//
    SharedPreferences share;
    SharedPreferences.Editor editor;
    private static final String Pref = "Pref";



    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://notenonthawat.000webhostapp.com/register.php";
    private ProgressDialog prg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
        prg = new ProgressDialog(MainActivity.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);

        sql = new sqlLite(this, "stickburn.db", null, 1);


        B_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                 findID();
                prg.show();
              click();



            }
        });

    }

    private void click(){

        String n = name.getText().toString();
        String hei = h.getText().toString();
        String wei = w.getText().toString();
        String ag_e = age.getText().toString();
        String sex = getSex();

       if(sex.isEmpty()){
            Toast.makeText(MainActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show(); // if อยู่ภายใต้ create ต้องใส่ MainActivity.this
        }
        else if(n.isEmpty()){Toast.makeText(MainActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();  }
       else if(hei.isEmpty()){Toast.makeText(MainActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();  }
       else if(wei.isEmpty()){Toast.makeText(MainActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();  }

       else if(ag_e.isEmpty()){Toast.makeText(MainActivity.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();  }

        else {

                  /*  intent.putExtra("name",n);
                  //  Toast.makeText(MainActivity.this, n, Toast.LENGTH_SHORT).show();
                    intent.putExtra("h",hei);
                    intent.putExtra("w",wei);
                    intent.putExtra("age",ag_e);
                    intent.putExtra("sex",sex);*/
           //       startActivity(intent);
           //
           share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
           editor = share.edit();
           editor.putString("name", n);
           editor.putString("hei", hei);
           editor.putString("wei", wei);
           editor.putString("age", ag_e);
           editor.putString("sex", sex);


           RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
            urlJsonObj, null, new Response.Listener<JSONObject>() */
           StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

               //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(String response) {
                   Log.d(TAG, response.toString());
                   try {

                       prg.hide();
                       h.setText("");
                       w.setText("");
                       JSONObject j = new JSONObject(response.toString());
                       //   JSONObject j = response.getJSONObject("dataUser");
                       String userID = j.getString("userID");
                       // j.getJSONObject("dataUser");

                       String result = j.getString("result");
                      // Toast.makeText(getApplicationContext(), result + " " + userID, Toast.LENGTH_LONG).show();

                       editor.putString("id", userID);
                       editor.commit();

                       Intent intent = new Intent(MainActivity.this, Detail.class);
                       startActivity(intent);

                   } catch (JSONException e) {
                       prg.hide();
                       //  textviewShow.setText(e.getMessage());
                       //      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                       Log.d("Whats wrong?", e.toString());
                       Log.e("JSON Parser", "Error parsing data " + e.toString());
                       // textviewShow.setText(e.toString());

                       //intent
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   VolleyLog.d(TAG, error.toString());

                   prg.hide();
                   alertDetail("ผิดพลาด");
                   h.setText("");
                   w.setText("");
                   name.setText("");
                   age.setText("");


               }
           }) {
               @Override
               protected Map<String, String> getParams() {
                   Map<String, String> params = new HashMap<String, String>();
                   params.put("w", w.getText().toString());
                   params.put("h", h.getText().toString());
                   params.put("username", name.getText().toString());
                   params.put("age", age.getText().toString());
                   return params;


               }
           };
           requestQueue.add(request);
       }

        }


    private String getSex (){



     if (male.isChecked()) {
    //     Toast.makeText(this, "ผู้ชาย", Toast.LENGTH_SHORT).show();    // อยู่นอก create ใส่ this ได้
        /* Intent intent = new Intent(this, Detail.class);
         startActivity(intent);*/
        return "ชาย" ;
     }

     else if (female.isChecked()){
        // Toast.makeText(this, "ผู้หญิง", Toast.LENGTH_SHORT).show();

       /*  Intent intent = new Intent(this, Detail.class);
         startActivity(intent);*/
         return "หญิง" ;
     }
     else {

        // Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
         return "" ;
     }

    }


    private void findID(){
        B_confirm = (Button) findViewById(R.id.button);
        w = (EditText) findViewById(R.id.w);
        h = (EditText) findViewById(R.id.h);
       age = (EditText) findViewById(R.id.age);
      name = (EditText) findViewById(R.id.editText4);

      female = (RadioButton) findViewById(R.id.radioFemale) ;
      male = (RadioButton) findViewById(R.id.radioMale);
      rg = (RadioGroup) findViewById(R.id.radioSex);

    }


    public void alertDetail(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("                      StickBurn");
        builder.setMessage("" + text);
        builder.setNegativeButton("ตกลง", null);
        builder.show();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
