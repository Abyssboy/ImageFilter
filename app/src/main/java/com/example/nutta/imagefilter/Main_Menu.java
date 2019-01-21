package com.example.nutta.imagefilter;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;





public class Main_Menu extends AppCompatActivity {
    LocalActivityManager mLocalActivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);


        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                .setIndicator("My Mural")
                .setContent(new Intent(this, PageMyMural.class));
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3")
                .setIndicator("All Mural")
                .setContent(new Intent(this, PageAllMural.class));

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("tab4")
                .setIndicator("Filter")
                .setContent(new Intent(this, PageFliter.class));


        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(!isFinishing());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }
}



