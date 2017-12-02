package com.example.admin.stickburn;

import android.speech.tts.TextToSpeech;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
       // FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager = getSupportFragmentManager();
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(fragmentManager);
        viewPager.setAdapter(myFragmentAdapter);




        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);



        TextToSpeech sp ;

     /*   int age = getIntent().getExtras().getInt("age");
        String name = getIntent().getExtras().getString("name");
      //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        String sex = getIntent().getExtras().getString("sex");
        Double w = getIntent().getExtras().getDouble("w");
        Double h = getIntent().getExtras().getDouble("h");


        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("sex", getIntent().getExtras().getString("sex"));
        bundle.putDouble("w", getIntent().getExtras().getDouble("w"));
        bundle.putDouble("h",getIntent().getExtras().getDouble("h"));
        bundle.putInt("age" ,getIntent().getExtras().getInt("age"));
// set Fragmentclass Arguments
        FragmentDatail fragDetail = new FragmentDatail();
        fragDetail.FragmentDatail(name);
        fragDetail.setArguments(bundle);*/
    }




    @Override
    protected void onDestroy() {

       finish();
        super.onDestroy();
    }


}
