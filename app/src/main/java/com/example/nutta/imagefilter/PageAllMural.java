package com.example.nutta.imagefilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nutta.imagefilter.Adapter.ListAdapter;
import com.example.nutta.imagefilter.Model.MuralItem;

import java.util.ArrayList;

public class PageAllMural extends AppCompatActivity {
    private ListAdapter mAdapter;
    private ArrayList<MuralItem> mMuralItem = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taballmural);

    }
}
