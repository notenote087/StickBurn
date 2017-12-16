package com.example.admin.stickburn;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class FragmentDatail extends Fragment implements TextToSpeech.OnInitListener {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://notenonthawat.000webhostapp.com/randomfood.php";
    //private static final String URL = "http://notestickburn.epizy.com/stickburn/randomfood.php";
    DatabaseReference mDB ;
    sqlLite sql_class ;
    String cal_share ;
    String food ;
    int cal_json ;

    TextView name,age,sex,hh,ww , bmi ,bmr , cal_day;
    Button confirm , foodinsert ;

    double callory_day = 0 ;
    double bmr_result =0;

    String n2 ,s2 ;
    int a2;
    double w2,h2;
    String id , cal_getString;

    SharedPreferences share;
    SharedPreferences.Editor editor;
    PendingIntent alarmintent ;
    AlarmManager alarmManager ;


    private ProgressDialog prg ;
    TextToSpeech sp ;
    String speak = "";
    int result_sp ;

    ImageView edit_ ;

    public FragmentDatail(){

    }

    public void FragmentDatail(String n){
            n2 = n;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View rootView =  inflater.inflate(R.layout.activity_fragment_datail, container, false);


       /* tts = new TextToSpeech(MainActivity,this, this, "com.google.android.tts");

        tts.setLanguage(new Locale("th")   ;
*/


        CallFucntion c = new CallFucntion();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true); // ทำให้ แอพ ทำงานแบบ Offine ได้ด้วย

        prg = new ProgressDialog(getActivity());
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);

        share = this.getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);


        id = share.getString("id","no") ;
        String  h = share.getString("hei","0");
        String w = share.getString("wei","0");
        String a = share.getString("age","0");
        String s = share.getString("sex","0");
        String n = share.getString("name","0");

        cal_share = share.getString("callory_day","0") ;

        name = (TextView) rootView.findViewById(R.id.name_f);
        name.setText("ชื่อ : " + n);
        age = (TextView) rootView.findViewById(R.id.age_f);
        age.setText("อายุ : "+a);

        sex = (TextView) rootView.findViewById(R.id.sex_f);
        sex.setText("เพศ : "+s);

        ww = (TextView) rootView.findViewById(R.id.w_f);
        ww.setText("น้ำหนัก : "+ w);

        hh = (TextView) rootView.findViewById(R.id.h_f);
        hh.setText("ส่วนสูง : " +h);

        int w_bmi = Integer.parseInt(w);
        int h_bmi = Integer.parseInt(h);
     //   Toast.makeText(getActivity(), Integer.toString(w_bmi) + " " + Integer.toString(h_bmi)   , Toast.LENGTH_SHORT).show();
       double resultBMI =  c.CalBMI(h_bmi,w_bmi);




    //    Toast.makeText(getActivity(), Double.toString(resultBMI), Toast.LENGTH_SHORT).show();
       String bmi_text = c.ResultBMI(resultBMI);

        bmi = (TextView) rootView.findViewById(R.id.bmi_f);
        bmi.setText(bmi_text);

        int a_bmr = Integer.parseInt(a);
        bmr_result =  c.BMR(h_bmi , w_bmi ,s ,a_bmr) ;
        bmr = (TextView) rootView.findViewById(R.id.bmr_f);
        bmr.setText("ปริมาณแคลลอรี่ที่เหมาะสม: \n"+bmr_result + " กิโลแคลลอรี่");


        cal_day = (TextView) rootView.findViewById(R.id.cal_day);

        callory_day = Double.parseDouble(cal_share);

        cal_day.setText("คุณได้รับไปแล้ว: \n"+callory_day + " กิโลแคลลอรี่");
       // cal_day.setText("คุณได้รับไปแล้ว: "+cal_share);
        confirm = (Button) rootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                prg.show();
                json_request();
                setAlarm(20,1);

            }
        });

        foodinsert = (Button) rootView.findViewById(R.id.foodinsert);
        foodinsert.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                  DialogInput();

                    setAlarm(59,23);


              }
         } );


        sp = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {

                    result_sp = sp.setLanguage(new Locale("th"));  // Not support Locale.THAI
              //      Toast.makeText(getActivity(), "No Error", Toast.LENGTH_SHORT).show();

                }

            }
        });

      /*  speak = "ยินดีต้อนรับ";// editText.getText().toString();
        speak(speak);*/




        ImageView edit_ = (ImageView) rootView.findViewById(R.id.imageView4);
        edit_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     Toast.makeText(v.getContext(), // <- Line changed
                        "The favorite list would appear on clicking this icon",
                        Toast.LENGTH_LONG).show();*/
                Intent startEdit = new Intent(getActivity(),Edit.class);
                startActivity(startEdit);

            }
        });
        alertWelcome(n);
        return rootView;


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarm(int m, int h){
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(); // requires API level 24
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(android.icu.util.Calendar.HOUR_OF_DAY,h);

        calendar.set(android.icu.util.Calendar.MINUTE,m);
        Intent intent = new Intent(getActivity(),myReceiver.class);
        alarmintent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        // alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(),alarmintent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime()+ 2 *1000,alarmintent);

//        Toast.makeText(getActivity(), h+"+"+m, Toast.LENGTH_SHORT).show();

    }
    public void speak(String text){
        if (text == null || text == "")  Toast.makeText(getActivity(),"Nulll", Toast.LENGTH_SHORT).show();
        //else sp.speak(text,TextToSpeech.QUEUE_FLUSH,null);QUEUE_ADD
        else sp.speak(text,TextToSpeech.QUEUE_ADD,null);
    }

    public void speak2(String text){
        if (text == null || text == "")  Toast.makeText(getActivity(),"Nulll", Toast.LENGTH_SHORT).show();
            else sp.speak(text,TextToSpeech.QUEUE_FLUSH,null);
       // else sp.speak(text,TextToSpeech.QUEUE_ADD,null);
    }

    @Override
    public void onPause() {
        super.onPause();
        Double cc = callory_day;
      /*  if (cc !=null) {
            Toast.makeText(getActivity(), Double.toString(callory_day), Toast.LENGTH_SHORT).show();
        }*/

        editor = share.edit();
       editor.putString("callory_day", Double.toString(callory_day));

       editor.commit();



        if(sp !=null){
            sp.stop();
            sp.shutdown();

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        editor = share.edit();

        editor.putString("callory_day", Double.toString(callory_day));


       editor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        //cal_share = share.getString("callory_day","0") ;

    }


    public void alertWelcome(String n){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(" StickBurn");
        builder.setMessage("ยินดีต้อนรับคุณ " + n);
        builder.setNegativeButton("ตกลง", null);

        builder.show();
        speak2("ยินดีต้อนรับ");

    }

    public void alert2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(" StickBurn");
        builder.setMessage("คุณกินเยอะเกินไปแล้วนะ !!");
        builder.setNegativeButton("ตกลง", null);

        builder.show();
        speak("คุณกินเยอะเกินไปแล้วนะ");

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

                DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH/mm/ss");
                String date = df.format(Calendar.getInstance().getTime());

                speak("อาหารที่สุ่มได้คือ "+food);
                if (callory_day > bmr_result){
                    alert2();
                }




                cal_day.setText("คุณได้รับไปแล้ว: "+ callory_day);

                mDB = FirebaseDatabase.getInstance().getReference(id); // อ้างอิง ถ้าไม่มใีส่อะไรจะอ้างอิงไปที่ Root Node ถ้าใส่ ที่ Referrenc คือที่ๆจะอ้างอิง
                final save_history test = new save_history(food ,  cal_getString,date);
       /* User user2 = new User("Nonthawat2" , "Nonthawat@mail2");
        User user3 = new User("Nonthawat3" , "Nonthawat@mail3");*/

                DateFormat df2 = new SimpleDateFormat("HHmmss");
                mDB.child(id + "_"+df2.format(Calendar.getInstance().getTime())).setValue(test);

                Intent intent1 = new Intent(getActivity(),Splash.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0,intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getActivity().getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Stickburn")
                                .setContentText("อย่าลืมกิน "+ food +" ละ")
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());

            } });


        builder.show();


    }




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


    private void DialogInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("StickBurn :: เพิ่มชื่ออาหาร");


        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);



        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String  food_text = input.getText().toString();

             //   Toast.makeText(getActivity(), m_Text, Toast.LENGTH_SHORT).show();
                cal_getString  = "0" ;

                DateFormat df = new SimpleDateFormat("yyyy/MM/dd  HH/mm/ss");
                String date = df.format(Calendar.getInstance().getTime());

                mDB = FirebaseDatabase.getInstance().getReference(id); // อ้างอิง ถ้าไม่มใีส่อะไรจะอ้างอิงไปที่ Root Node ถ้าใส่ ที่ Referrenc คือที่ๆจะอ้างอิง
                final save_history test = new save_history(food_text ,  cal_getString,date);


                DateFormat df2 = new SimpleDateFormat("HHmmss");
                mDB.child(id + "_"+df2.format(Calendar.getInstance().getTime())).setValue(test);


            }
        });
        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

            }

// TTS
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            // Do something here
        }
    }
}
