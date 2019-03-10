package com.example.nutta.imagefilter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.opencensus.tags.Tag;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class Detail_Activity extends AppCompatActivity {
    private PhotoEditorView photoEditorView;
   private Bitmap Img_preview;
   FirebaseFirestore firebaseFirestore;
   EditText TextName,TextDetail,TextYearFrom,TextYearTo,TextWide,TextHeight;
    String name  = null ;
    String Detail = null;
    String muralpos  =   null;
    String muraltype  =   null;
    String YearFrom = null;
    String YearTo = null;
    String Wide =  null;
    String Latitude_data = null;
    String Longtitude_data = null;
    String Height = null;
    Date create_date = null;
    Date update_date = null;

    Button mButton;
    TextView mLocation;
    TextView LT;
    TextView LONGT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);

         Spinner mPos = findViewById(R.id.Muralpos);
        Spinner mType = findViewById(R.id.Muraltype);


        Toolbar toolbar = findViewById(R.id.toolBar_Detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");



        PhotoEditorView photoEditorView;
        Geocoder geocoder;
        List<Address> addresses;


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




         /*
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
           byte[] bytesImg  =bundle.getByteArray("image");
            Bitmap bmp = (Bitmap) BitmapFactory.decodeByteArray(bytesImg,0,bytesImg.length);
            Img_preview = bmp;

        }*/

       /* photoEditorView = findViewById(R.id.Image_detail_preview);

        photoEditorView.getSource().setImageBitmap(Img_preview);*/

        firebaseFirestore = FirebaseFirestore.getInstance();

        TextName  = findViewById(R.id.Edit_Name);
        TextDetail = findViewById(R.id.Edit_Detail);
        TextWide  = findViewById(R.id.Edit_Width);
        TextHeight  = findViewById(R.id.Edit_Width);
        TextYearFrom = findViewById(R.id.Edit_YEAR_FROM);
        TextYearTo = findViewById(R.id.Edit_YEAR_TO);



        double Latitude = 13.7540787;
        double Longtitude =100.6257144;

        mButton = findViewById(R.id.ChangeLocation);
        mLocation = findViewById(R.id.ShowLocation);
        LT = findViewById(R.id.Latitude);
        LONGT = findViewById(R.id.Longtitude);



        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Latitude,Longtitude,1);

            String name = addresses.get(0).getCountryCode();


            mLocation.setText(name);
            String finalLa = Double.toString(Latitude);
            String finalLong = Double.toString(Longtitude);
            LT.setText(finalLa);
            LONGT.setText(finalLong);


        } catch (IOException e) {
            e.printStackTrace();
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_finish){
            SaveIntoDB();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void SaveIntoDB() {

        if(TextUtils.isEmpty(TextName.getText())){
                TextName.setError("กรุณากรอกข้อมูล ชื่อ");
        }else {
            name = TextName.getText().toString();
            Detail = TextDetail.getText().toString();
            YearFrom = TextYearFrom.getText().toString();
            YearTo = TextYearTo.getText().toString();
            Wide =  TextWide.getText().toString();
            Height = TextHeight.getText().toString();

            Latitude_data = LT.getText().toString();
            Longtitude_data = LONGT.getText().toString();





            Timestamp timestamp =  new Timestamp(System.currentTimeMillis());
            Log.i("TAG","test"+timestamp);

            create_date = timestamp;
            update_date = timestamp;

        Map<String,Object> DataMap = new HashMap<>();

        DataMap.put("Name",name);
        DataMap.put("Detail",Detail);
        DataMap.put("Position",muralpos);
        DataMap.put("Type",muraltype);

        DataMap.put("YearFrom",YearFrom);
        DataMap.put("YearTo",YearTo);
        DataMap.put("Wide",Wide);
        DataMap.put("Height",Height);
        DataMap.put("Latitude",Latitude_data);
        DataMap.put("Longtitude",Longtitude_data);
        DataMap.put("create_date",create_date);
        DataMap.put("update_date",update_date);

        firebaseFirestore.collection("Item").add(DataMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
              //  Toast.makeText(Detail_Activity.this,"Okay",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Detail_Activity.this,Main_Menu.class);
        /*ByteArrayOutputStream bs = new ByteArrayOutputStream();
        finalbitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
        intent.putExtra("byteArray", bs.toByteArray());*/
                startActivity(intent);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error  = e.getMessage();
                Toast.makeText(Detail_Activity.this,error,Toast.LENGTH_LONG).show();
            }
        });

    }
    }

}
