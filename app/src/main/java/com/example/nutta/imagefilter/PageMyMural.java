package com.example.nutta.imagefilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.opencensus.tags.Tag;

public class PageMyMural extends AppCompatActivity {

    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;
    private static final String TAG = "FireLog";
    private ListAdapter mAdapter;
    public ArrayList<MuralItem> mMuralItem = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String uid;
    String Username;
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
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });*/
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();
                 uid = profile.getUid();
            };
        }
                firebaseFirestore.collection("Item").whereEqualTo("UID",uid).orderBy("create_date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    private void SelectImage() {

        final CharSequence[] item = {"ถ่ายภาพ", "แกลลอรี่", "ยกเลิก"};
        AlertDialog.Builder Builder =  new AlertDialog.Builder(PageMyMural.this);
        Builder.setTitle("เลือกภาพ");
        Builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(item[i].equals("ถ่ายภาพ")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1);


                }else if(item[i].equals("แกลลอรี่")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),2);

                }else if(item[i].equals("Cancle")){
                    dialog.dismiss();
                }

            }
        });
        Builder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==1){
            captureimage(this);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("selectedImageUri",currentImagePath.toString());
            startActivity(intent);
            finish();
          /*  Bundle bundle = data.getExtras();
            final Bitmap bitmap = (Bitmap) bundle.get("data");
            mImageView.setImageBitmap(bitmap);*/
        }else if(resultCode== Activity.RESULT_OK && requestCode==2){

            Uri selectedImageUri = data.getData();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("selectedImageUri",selectedImageUri.toString());
            startActivity(intent);
            finish();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void captureimage(PageMyMural view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager()) != null){
            File imageFile =null;

            try {

                imageFile = getImageFile();
            } catch (IOException e){
                e.printStackTrace();
            }
            if(imageFile != null){
                Uri imageURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageURI);
                startActivityForResult(cameraIntent,IMAGE_REQUEST);

            }
        }
    }

    private File getImageFile() throws IOException {
        String  timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String  imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File imageFile =File.createTempFile(imageName,".jpg",storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return  imageFile;
    }

}







