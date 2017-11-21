package com.example.admin.stickburn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button B_confirm ;
    EditText w , h , age , name;
    // sex

    CallFucntion c = new CallFucntion();
    double bmi_value = 0;
    String bmi_result = "";

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
       /* share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        editor= share.edit();
        editor.putString("checkAlert","1");
        editor.commit();
*/
        //checkid = share.getString("id","") ;

        B_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // findViewID();
                prg = new ProgressDialog(MainActivity.this);
                prg.setMessage("รอสักครู่...");
                prg.setCancelable(false);
                prg.show();

                int  wi = Integer.parseInt(w.getText().toString()) ;
                int  hi = Integer.parseInt(h.getText().toString()) ;
                bmi_value =  c.CalBMI(wi,hi);
                bmi_result  = c.ResultBMI(bmi_value);
                confirm_button();
            }
        });

    }

    private void confirm_button (){

            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                         //   Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();


                            share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
                            editor= share.edit();
                            editor.putString("userID",userID);
                            editor.commit();
                        } catch (JSONException e) {
                            prg.hide();
                            //  textviewShow.setText(e.getMessage());
                              Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
                };requestQueue.add(request);
            }


    private void findID(){
        B_confirm = (Button) findViewById(R.id.button);
        w = (EditText) findViewById(R.id.w);
        h = (EditText) findViewById(R.id.h);
       age = (EditText) findViewById(R.id.age);
      name = (EditText) findViewById(R.id.editText4);

    }


    public void alertDetail(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("                      StickBurn");
        builder.setMessage("" + text);
        builder.setNegativeButton("ตกลง", null);
        builder.show();


    }
}
