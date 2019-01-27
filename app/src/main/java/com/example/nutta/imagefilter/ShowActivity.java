package com.example.nutta.imagefilter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.Date;

import javax.annotation.Nullable;

public class ShowActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    private static final String TAG = "FireLog";

    TextView TextName, TextDetail, TextPos, TextYear, TextWide, TextHeight, TextETC;
    String name = null;
    String Detail = null;
    String Pos = null;
    String Year = null;
    String Wide = null;
    String Latitude_data = null;
    String Longtitude_data = null;
    String Height = null;
    String ETC = null;
    Date create_date = null;
    Date update_date = null;

    Button mButton;
    TextView mLocation;
    TextView LT;
    TextView LONGT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = findViewById(R.id.toolBar_Show);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextName = findViewById(R.id.Show_name);
        TextDetail = findViewById(R.id.Show_detail);


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Item").document("IbApVfGzb9nK54lXotz7").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot  doc = task.getResult();
                    if (doc.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.get("Name"));
                        String name = doc.get("Name").toString();
                        TextName.setText(name);
                        getSupportActionBar().setTitle(name);
                    }
                }
            }
        });
    }
}