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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabmymural);
        //loadDB();

      firebaseFirestore.collection("Item").orderBy("create_date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               mMuralItem.clear();
                for(DocumentSnapshot  snapshot : queryDocumentSnapshots){
                    String name = snapshot.getString("Name");
                    String ID = snapshot.getId();
                    MuralItem muralItem= new MuralItem(ID,name);
                    mMuralItem.add(muralItem);
                    Log.d(TAG, "onEvent: "+name+" "+ID);

                }
           }
       });
        mAdapter = new ListAdapter(
                this,
                R.layout.listitem,
                mMuralItem

        );
        ListView LV = findViewById(R.id.MyMuralItem);
        LV.setAdapter(mAdapter);




      /*  LV.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MuralItem muralItem =mMuralItem.get(position);
                String ItemID = muralItem.ID ;

                Intent intent =new Intent(PageMyMural.this,ShowActivity.class);

                intent.putExtra("Position",ItemID);
                startActivityForResult(intent,2);
            }
        });*/

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageMyMural.this, MainActivity.class);

                startActivityForResult(intent, 1);
            }

        });

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (requestCode == RESULT_OK) {
                loadDB();
                mAdapter.notifyDataSetChanged();
            }
        }

    }*/

    /*private void loadDB() {

        firebaseFirestore.collection("Item").orderBy("create_date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                mMuralItem.clear();
                if (e == null) {

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String name = doc.getDocument().getString("name");
                            String ID = doc.getDocument().getId();
                            Log.d(TAG, "onEvent: " + name + " " + ID);


                            MuralItem muralItem = new MuralItem(ID, name);

                            mMuralItem.add(muralItem);

                        }
                    }


                }
            }
        });*/


    }








