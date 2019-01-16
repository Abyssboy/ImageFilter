package com.example.nutta.imagefilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class Detail_Activity extends AppCompatActivity {
    private PhotoEditorView photoEditorView;
   private Bitmap Img_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);

        Toolbar toolbar = findViewById(R.id.toolBar_Detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

        PhotoEditorView photoEditorView;


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            /*byte[] bytesImg  =bundle.getByteArray("image");
            Bitmap bmp = (Bitmap) BitmapFactory.decodeByteArray(bytesImg,0,bytesImg.length);
            Img_preview = bmp;*/

        }

        photoEditorView = findViewById(R.id.Image_detail_preview);

        photoEditorView.getSource().setImageBitmap(Img_preview);


    }
}
