package com.example.nutta.imagefilter;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.nutta.imagefilter.Adapter.ListAdapter;
import com.example.nutta.imagefilter.Model.MuralItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.tags.Tag;

public class PageMyMural extends AppCompatActivity {


    private static final String TAG = "FireLog";
    private ListAdapter mAdapter;
    public ArrayList<MuralItem> mMuralItem = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    String SentId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabmymural);
        //loadDB();
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageMyMural.this, MainActivity.class);

                startActivityForResult(intent, 1);
            }

        });

        firebaseFirestore.collection("Item").orderBy("create_date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String name = documentSnapshot.getString("Name");
                        String ID = documentSnapshot.getId();
                        MuralItem muralItem = new MuralItem(ID, name);
                        mMuralItem.add(muralItem);
                    }


                    final ListView LV = findViewById(R.id.MyMuralItem);
                    LV.setAdapter(mAdapter);

                    LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //String CurrentGroupname = parent.getItemAtPosition(position).toString();
                            String CurrentgroupID = parent.getItemAtPosition(position).toString();
                            Toast.makeText(PageMyMural.this, CurrentgroupID, Toast.LENGTH_LONG).show();


                            Intent groupIntent = new Intent(view.getContext(), ShowActivity.class);
                            groupIntent.putExtra("CurrentgroupID",CurrentgroupID);
                            startActivity(groupIntent);

                        }
                    });

                } else {
                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });

        mAdapter = new ListAdapter(
                this,
                R.layout.listitem,
                mMuralItem

        );

    }

}







