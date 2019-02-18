package com.example.nutta.imagefilter;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Filter extends AppCompatActivity {
    EditText mEdittext;
    Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);



     /*  mEdittext.findViewById(R.id.CreateDate);
       mEdittext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mCalendar = Calendar.getInstance();
               int year = mCalendar.get(Calendar.YEAR);
               int MONTH = mCalendar.get(Calendar.MONTH);
               int day  =  mCalendar.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog mDatepicker = new DatePickerDialog(Filter.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       mEdittext.setText(dayOfMonth+"/"+month+"/"+year);

                       mCalendar.set(dayOfMonth,month,year);
                   }
               }, year, MONTH, day);
               mDatepicker.show();

           }
       });*/




    }
}
