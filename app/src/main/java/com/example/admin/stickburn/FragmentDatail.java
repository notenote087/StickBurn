package com.example.admin.stickburn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class FragmentDatail extends Fragment {
    DatabaseReference mDB ;
    String food ;
    int cal_json ;

    TextView name,age,sex,hh,ww , bmi ,bmr , cal_day;
    Button confirm ;

    double callory_day = 0 ;
    double bmr_result =0;

    String n2 ,s2 ;
    int a2;
    double w2,h2;
    String id , cal_getString;
    SharedPreferences share ;
    private ProgressDialog prg ;

    public FragmentDatail(){

    }

    public void FragmentDatail(String n){
            n2 = n;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View rootView =  inflater.inflate(R.layout.activity_fragment_datail, container, false);

        CallFucntion c = new CallFucntion();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // ทำให้ แอพ ทำงานแบบ Offine ได้ด้วย

        prg = new ProgressDialog(getActivity());
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);
        //   Bundle bundle = getArguments();
     /*  String name = bundle.getString("name");
        int age = this.getArguments().getInt("age");

        String sex = this.getArguments().getString("sex");
        Double w = this.getArguments().getDouble("w");
        Double h = this.getArguments().getDouble("h");

        showText(name,sex,w,h,age);

*/
   /*     Toast.makeText(getActivity(), n2, Toast.LENGTH_SHORT).show();
     if (bundle == null) {
        // Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
     }*/
        share = this.getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);


        id = share.getString("id","no") ;
        String  h = share.getString("hei","0");
        String w = share.getString("wei","0");
        String a = share.getString("age","0");
        String s = share.getString("sex","0");
        String n = share.getString("name","0");


        name = (TextView) rootView.findViewById(R.id.name_f);
        name.setText("ชื่อ :" + n);
        age = (TextView) rootView.findViewById(R.id.age_f);
        age.setText("อายุ :"+a);

        sex = (TextView) rootView.findViewById(R.id.sex_f);
        sex.setText("เพศ :"+s);

        ww = (TextView) rootView.findViewById(R.id.w_f);
        ww.setText("น้ำหนัก :"+ w);

        hh = (TextView) rootView.findViewById(R.id.h_f);
        hh.setText("ส่วนสูง :" +h);

        int w_bmi = Integer.parseInt(w);
        int h_bmi = Integer.parseInt(h);
       double resultBMI =  c.CalBMI(h_bmi,w_bmi);
       String bmi_text = c.ResultBMI(resultBMI);

        bmi = (TextView) rootView.findViewById(R.id.bmi_f);
        bmi.setText(bmi_text);

        int a_bmr = Integer.parseInt(a);
        bmr_result =  c.BMR(h_bmi , w_bmi ,s ,a_bmr) ;
        bmr = (TextView) rootView.findViewById(R.id.bmr_f);
        bmr.setText("ปริมาณแคลลอรี่ที่เหมาะสม: "+bmr_result );


        cal_day = (TextView) rootView.findViewById(R.id.cal_day);
        cal_day.setText("คุณได้รับไปแล้ว: "+callory_day);

        confirm = (Button) rootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              prg.show();
                json_request();

            }
        });


        return rootView;


    }
    public void alertDetail(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(" StickBurn");
        builder.setMessage("อาหารที่คุณสุ่มได้ " + text);
       // builder.setNegativeButton("ตกลง", null);
        builder.setPositiveButton("สุ่มใหม่", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             json_request();
            } });

        builder.setNegativeButton("ยืนยัน", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callory_day += cal_json;
                cal_day.setText("คุณได้รับไปแล้ว: "+callory_day);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd  'at' HH/mm/ss");
                String date = df.format(Calendar.getInstance().getTime());




                mDB = FirebaseDatabase.getInstance().getReference(id); // อ้างอิง ถ้าไม่มใีส่อะไรจะอ้างอิงไปที่ Root Node ถ้าใส่ ที่ Referrenc คือที่ๆจะอ้างอิง
                final save_history test = new save_history(food ,  cal_getString,date);
       /* User user2 = new User("Nonthawat2" , "Nonthawat@mail2");
        User user3 = new User("Nonthawat3" , "Nonthawat@mail3");*/

                DateFormat df2 = new SimpleDateFormat("HHmmss");
                mDB.child(id + "_"+df2.format(Calendar.getInstance().getTime())).setValue(test);


            } });


        builder.show();


    }



    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://notenonthawat.000webhostapp.com/randomfood.php";


    private void json_request(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
            urlJsonObj, null, new Response.Listener<JSONObject>() */
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {

                    prg.hide();

                    JSONObject j = new JSONObject(response.toString());
                    //   JSONObject j = response.getJSONObject("dataUser");
                 //   String userID = j.getString("userID");
                    // j.getJSONObject("dataUser");

                  //  String result = j.getString("result");

                    food = j.getString("food");

                    cal_json = j.getInt("cal") ;
                    cal_getString = j.getString("cal");

                    alertDetail(food);
                } catch (JSONException e) {
                    prg.hide();
                    //  textviewShow.setText(e.getMessage());
                    //      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("Whats wrong?", e.toString());
                    Log.e("JSON Parser", "Error parsing data " + e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, error.toString());

                prg.hide();
                alertDetail("ผิดพลาด");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("cal_day",Double.toString(callory_day));
                params.put("bmr", Double.toString(bmr_result));
                return params;


            }
        };
        requestQueue.add(request);
    }

}
