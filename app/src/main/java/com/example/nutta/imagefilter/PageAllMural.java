package com.example.nutta.imagefilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PageAllMural extends AppCompatActivity {

    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;
    private static final String TAG = "FireLog";
    private ListAdapter mAdapter;
    public ArrayList<MuralItem> mMuralItem = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taballmural);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();
                uid = profile.getUid();
            };
        }
        firebaseFirestore.collection("Item").orderBy("create_date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String name = documentSnapshot.getString("Name");
                        String ID = documentSnapshot.getId();
                        String User_Create = documentSnapshot.getString("create_by");

                        MuralItem muralItem = new MuralItem(ID, name,User_Create);
                        mMuralItem.add(muralItem);
                    }


                    final ListView LV = findViewById(R.id.AllMuralItem);
                    LV.setAdapter(mAdapter);

                    LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //String CurrentGroupname = parent.getItemAtPosition(position).toString();
                            String CurrentgroupID = parent.getItemAtPosition(position).toString();


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

