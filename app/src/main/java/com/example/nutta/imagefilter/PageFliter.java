package com.example.nutta.imagefilter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nutta.imagefilter.Adapter.ListAdapter;
import com.example.nutta.imagefilter.Model.MuralItem;

import java.util.ArrayList;

public class PageFliter extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState,  PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.tabfliter);

    }
}
