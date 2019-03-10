package com.example.nutta.imagefilter;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.nutta.imagefilter.Adapter.ListAdapter;
import com.example.nutta.imagefilter.Model.MuralItem;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class Filter extends AppCompatActivity {
    EditText mDateFrom;
    EditText mDateTo;
    EditText mYearFrom;
    EditText mYearTo;
    Calendar mCalendar;
    DatePickerDialog DPD;
    String muralpos  =   null;
    String muraltype  =   null;

    private static final String TAG = "FireLog";
    private ListAdapter mAdapter;
    public ArrayList<MuralItem> mMuralItem = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

            mDateFrom=findViewById(R.id.CreateDateFrom);
            mDateTo=findViewById(R.id.CreateDateTo);
            mYearFrom=findViewById(R.id.YearFrom);
            mYearTo=findViewById(R.id.YearTo);
           Spinner mPos = findViewById(R.id.filter_pos);
           Spinner mType = findViewById(R.id.filter_type);

        final String[] pos  = getResources().getStringArray(R.array.MuralPos);
        ArrayAdapter<String> adapterpos = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, pos);

        mPos.setAdapter(adapterpos);

        mPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                muralpos = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] type  = getResources().getStringArray(R.array.MuralType);
        ArrayAdapter<String> adaptertype = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, type);
        mType.setAdapter(adaptertype);

        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                muraltype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int MONTH = mCalendar.get(Calendar.MONTH);
                int day  =  mCalendar.get(Calendar.DAY_OF_MONTH);

                DPD = new DatePickerDialog(Filter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        mDateFrom.setText(mdayOfMonth+"/"+mmonth+1+"/"+myear);

                    }
                },day , MONTH ,year);
                DPD.show();
            }
        });
        mDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int MONTH = mCalendar.get(Calendar.MONTH);
                int day  =  mCalendar.get(Calendar.DAY_OF_MONTH);

                DPD = new DatePickerDialog(Filter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        mDateTo.setText(mdayOfMonth+"/"+mmonth+1+"/"+myear);

                    }
                },day , MONTH ,year);
                DPD.show();
            }
        });

        mYearFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mCalendar.get(Calendar.YEAR);
            }
        });



      /*  firebaseFirestore.collection("Item").orderBy("create_date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                mMuralItem.clear();
                for(DocumentSnapshot snapshot : queryDocumentSnapshots){
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
        LV.setAdapter(mAdapter);*/


    }
}
