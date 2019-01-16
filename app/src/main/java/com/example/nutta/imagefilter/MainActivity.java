package com.example.nutta.imagefilter;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nutta.imagefilter.Adapter.ViewPagerAdapter;
import com.example.nutta.imagefilter.Interface.EditImageFragmentListener;
import com.example.nutta.imagefilter.Interface.FilterListFragmentListener;
import com.example.nutta.imagefilter.Interface.PaintFragmentListener;
import com.example.nutta.imagefilter.Utils.BitmapUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

import java.io.IOException;
import java.util.List;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class MainActivity extends AppCompatActivity implements FilterListFragmentListener,EditImageFragmentListener, PaintFragmentListener {

    public static final String pictureName = "xxx.jpg" ;
    public static final int PERMISSION_PICK_IMAGE = 1000;

   /* ImageView img_preview;
    V1
    TabLayout tabLayout;

    ViewPager viewPager;*/
   PhotoEditorView photoEditorView;
   PhotoEditor photoEditor;
   CardView btn_filters_list,btn_Edit,btn_paint;



    ConstraintLayout coordinatorLayout;


    Bitmap OriginalBitmap,filterBitmap,finalbitmap;

    FilterListFragment filterListFragment;
    EditImageFragment editImageFragment;

    int BrightnessFinal = 0;
    float Contrast = 0;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fliter");

 /* V.1
        img_preview = findViewById(R.id.Image_preview);

        tabLayout = findViewById(R.id.tab);

        viewPager = findViewById(R.id.viewpager);*/

        photoEditorView = findViewById(R.id.Image_preview);
        photoEditor = new PhotoEditor.Builder(this,photoEditorView)
                        .setPinchTextScalable(true)
                        .build();

        coordinatorLayout =  findViewById(R.id.coordinator);

        btn_Edit = findViewById(R.id.btn_edit_list);
        btn_filters_list = findViewById(R.id.btn_fliters_list);
        btn_paint = findViewById(R.id.btn_Paint);


        btn_filters_list.setOnClickListener(new View.OnClickListener() { /*v2*/
            @Override
            public void onClick(View v) {
                 FilterListFragment filterListFragment = FilterListFragment.getInstance();
                 filterListFragment.setListener(MainActivity.this);
                 filterListFragment.show(getSupportFragmentManager(),filterListFragment.getTag());
            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditImageFragment editImageFragment = EditImageFragment.getInstance();
                editImageFragment.setListener(MainActivity.this);
                editImageFragment.show(getSupportFragmentManager(),editImageFragment.getTag());
            }
        });

        btn_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoEditor.setBrushDrawingMode(true);

                 PaintFragement paintFragment= PaintFragement.getInstance();
                 paintFragment.setListener(MainActivity.this);
                 paintFragment.show(getSupportFragmentManager(),paintFragment.getTag());
            }
        });

        loadImage();


      /* v1
        setupViewpager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        */



    }

    private void loadImage() {
        OriginalBitmap = BitmapUtils.getBitmapFromAssets(this,pictureName,300,300);
        filterBitmap =  OriginalBitmap.copy(Bitmap.Config.ARGB_8888,true);
        finalbitmap =  OriginalBitmap.copy(Bitmap.Config.ARGB_8888,true);
        /*v.1
        img_preview.setImageBitmap(OriginalBitmap);
         */
        photoEditorView.getSource().setImageBitmap(OriginalBitmap);
    }

    public void setupViewpager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        filterListFragment = new FilterListFragment();
        filterListFragment.setListener(this);

        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filterListFragment,"Filter");
        adapter.addFragment(editImageFragment,"Edit");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onFilterselected(com.zomato.photofilters.imageprocessors.Filter filter) {
        resetControl();
        filterBitmap = OriginalBitmap.copy(Bitmap.Config.ARGB_8888,true);
        /*img_preview.setImageBitmap(filter.processFilter(filterBitmap));*/
        photoEditorView.getSource().setImageBitmap(filter.processFilter(filterBitmap));

        finalbitmap = filterBitmap.copy(Bitmap.Config.ARGB_8888,true);

    }

    private void resetControl() {
        if(editImageFragment != null)
            editImageFragment.resetControl();
        BrightnessFinal = 0;
        Contrast = 0;
    }

    @Override
    public void onBrightnessChanged(int brightness) {
        BrightnessFinal = brightness;
        com.zomato.photofilters.imageprocessors.Filter myFilter = new com.zomato.photofilters.imageprocessors.Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        /*v.1 img_preview.setImageBitmap(myFilter.processFilter(finalbitmap.copy(Bitmap.Config.ARGB_8888,true)));*/
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalbitmap.copy(Bitmap.Config.ARGB_8888,true)));

    }

    @Override
    public void onContrastChanged(Float contrast) {
        Contrast = contrast;
        com.zomato.photofilters.imageprocessors.Filter myFilter = new com.zomato.photofilters.imageprocessors.Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalbitmap.copy(Bitmap.Config.ARGB_8888,true)));

    }
/*
    @Override
    public void onContrastChanged(float contrast) {
        Contrast = contrast;
        com.zomato.photofilters.imageprocessors.Filter myFilter = new com.zomato.photofilters.imageprocessors.Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
       /*v.1 img_preview.setImageBitmap(myFilter.processFilter(finalbitmap.copy(Bitmap.Config.ARGB_8888,true)));
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalbitmap.copy(Bitmap.Config.ARGB_8888,true)));
    }
    */


    @Override
    public void EditStarted() {

    }

    @Override
    public void onEditCompleted() {

        Bitmap bitmap = filterBitmap.copy(Bitmap.Config.ARGB_8888,true);

        com.zomato.photofilters.imageprocessors.Filter myFilter = new com.zomato.photofilters.imageprocessors.Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(BrightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(Contrast));

        finalbitmap  = myFilter.processFilter(bitmap);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open){
            openImageFromGallary();
            return true;
        }
        if (id == R.id.action_save){
            SaveImageToGallary();
            return true;
        }
        if(id == R.id.action_nextpage){
            NextPage();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void NextPage() {
        Intent intent = new Intent(this,Detail_Activity.class);
        startActivity(intent);
    }

    private void SaveImageToGallary() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                       /* if(report.areAllPermissionsGranted()){
                            try {
                                final String path = BitmapUtils.insertImage(getContentResolver(),
                                        finalbitmap,
                                          System.currentTimeMillis()+"_ImageFilter.jpg"
                                        ,null);
                                if(!TextUtils.isEmpty(path))
                                {
                                    Snackbar snackbar = Snackbar.make(
                                            coordinatorLayout,
                                            "Complete",
                                            Snackbar.LENGTH_LONG)
                                            .setAction("Open", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    openImage(path);
                                                }
                                            });
                                            snackbar.show();



                                }
                                else{
                                    Snackbar snackbar = Snackbar.make(
                                            coordinatorLayout,
                                            "Unable to save",
                                            Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }*/
                        if(report.areAllPermissionsGranted()){
                                photoEditor.saveAsBitmap(new OnSaveBitmap() {
                                    @Override
                                    public void onBitmapReady(Bitmap saveBitmap) {
                                        try {
                                            photoEditorView.getSource().setImageBitmap(saveBitmap);
                                            final String path = BitmapUtils.insertImage(getContentResolver(),
                                                    saveBitmap,
                                                    System.currentTimeMillis()+"_ImageFilter"
                                                    ,null);
                                            if(!TextUtils.isEmpty(path))
                                            {
                                                Snackbar snackbar = Snackbar.make(
                                                        coordinatorLayout,
                                                        "Complete",
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Open", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                openImage(path);
                                                            }
                                                        });
                                                snackbar.show();



                                            }
                                            else{
                                                Snackbar snackbar = Snackbar.make(
                                                        coordinatorLayout,
                                                        "Unable to save",
                                                        Snackbar.LENGTH_LONG);
                                                snackbar.show();

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Permisson Denied",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                    }
                })
        .check();
    }

    private void openImage(String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path),"image/*");
        startActivity(intent);

    }

    private void openImageFromGallary() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
              .withListener(new MultiplePermissionsListener() {
                  @Override
                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                      if (report.areAllPermissionsGranted()){
                          Intent intent = new Intent(Intent.ACTION_PICK);
                          intent.setType("image/*");
                          startActivityForResult(intent,PERMISSION_PICK_IMAGE);
                      }
                      else{
                          Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                      }
                  }

                  @Override
                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                  }
              })
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == RESULT_OK && requestCode == PERMISSION_PICK_IMAGE){
                 Bitmap bitmap =  BitmapUtils.getBitmapFromGallery(this,data.getData(),800,800);

                 OriginalBitmap.recycle();
                 finalbitmap.recycle();
                 filterBitmap.recycle();

                 OriginalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                 finalbitmap = OriginalBitmap.copy(Bitmap.Config.ARGB_8888,true);
                 filterBitmap = OriginalBitmap.copy(Bitmap.Config.ARGB_8888,true);

                 /*V.1 img_preview.setImageBitmap(OriginalBitmap);*/
                photoEditorView.getSource().setImageBitmap(OriginalBitmap);

                 bitmap.recycle();

                 filterListFragment.displayThumbnail(OriginalBitmap);




            }
        }

    @Override
    public void onPaintSizeChangeListener(float size) {
        photoEditor.setBrushSize(size);
    }

    @Override
    public void onPaintOpacityChangeListener(int opacity) {
        photoEditor.setOpacity(opacity);
    }

    @Override
    public void onPaintColorChangeListener(int color) {
        int edit_color = color;
        photoEditor.setBrushColor(edit_color);
    }

    @Override
    public void onPaintStateChangeListener(Boolean isErase) {
        if (isErase){
                photoEditor.brushEraser();
        }
        else{
            photoEditor.setBrushDrawingMode(true);
        }
    }
}
