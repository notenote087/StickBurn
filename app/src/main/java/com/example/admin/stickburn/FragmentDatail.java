package com.example.admin.stickburn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class FragmentDatail extends Fragment {

    TextView name,age,sex,hh,ww;
    String n2 ,s2 ;
    int a2;
    double w2,h2;

    SharedPreferences share ;
    public FragmentDatail(){

    }

    public void FragmentDatail(String n){
            n2 = n;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View rootView =  inflater.inflate(R.layout.activity_fragment_datail, container, false);

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


        String id = share.getString("id","no") ;
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
        return rootView;


    }
    public void alertDetail(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("                      StickBurn");
        builder.setMessage("" + text);
        builder.setNegativeButton("ตกลง", null);
        builder.show();


    }



    private String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://notenonthawat.000webhostapp.com/register.php";
    private ProgressDialog prg ;

}
