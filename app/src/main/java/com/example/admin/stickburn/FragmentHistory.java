package com.example.admin.stickburn;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHistory extends Fragment {
    DatabaseReference mDB;
    DatabaseReference dref;

    TextView  t;
    ListView l;


    ArrayList<String> myArrList=new ArrayList<>();


    SharedPreferences share ;

    public FragmentHistory(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.activity_fragment_history, container, false);
        l = (ListView) rootView.findViewById(R.id.lv);
        t = (TextView) rootView.findViewById(R.id.result);
        share = this.getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);


       String id = share.getString("id","no") ;
      mDB = FirebaseDatabase.getInstance().getReference().child(id) ;//.child("leixoes");
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  // ข้อมูลจะยุนี้ dataSnapshot
                String data = "";
                for (DataSnapshot childSnap : dataSnapshot.getChildren()) { // ตราบใดที่ getChilderen ได้คือยังมีข้อมูล
                    save_history user = childSnap.getValue(save_history.class);
                    data += user.date_ + " " + "\n" + user.food + " " + user.cal +" กิโลแคลอรี่ "+ "\n"+"\n";
                }
               // t.setText(data);

                myArrList.add(data);
                ArrayAdapter itemsAdapter = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_list_item_1,
                        myArrList);

                l.setAdapter(itemsAdapter);

            }   // เมื่อมีการแอด หรือเปลี่ยนแปลงข้อมูงจะทำที่นี้

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return  rootView;
    }


}
