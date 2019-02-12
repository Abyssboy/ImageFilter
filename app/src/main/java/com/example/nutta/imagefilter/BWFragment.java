package com.example.nutta.imagefilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.nutta.imagefilter.Interface.BWFragmentListener;
import com.example.nutta.imagefilter.Interface.EditImageFragmentListener;

public class BWFragment  extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {



    private BWFragmentListener listener;
    SeekBar Seekbar_depth,Seekbar_color;

    static BWFragment instance;/*v2*/

    public static BWFragment getInstance(){
        if(instance == null){
            instance = new BWFragment();
        }
        return instance;
    }
    public void setListener(BWFragmentListener listener) {
        this.listener = listener;
    }

    public BWFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_black_white, container, false);
        Seekbar_depth=  itemView.findViewById(R.id.seekbar_dept);
        Seekbar_color = itemView.findViewById(R.id.seekbar_bw);

        /*Seekbar_brightness.setMax(200);
        Seekbar_brightness.setProgress(100);

        Seekbar_Contrast.setMax(200);
        Seekbar_brightness.setProgress(100);*/
        Seekbar_depth.setMax(500);
        Seekbar_depth.setProgress(0);

        Seekbar_color.setMax(500);
        Seekbar_color.setProgress(0);

        Seekbar_depth.setOnSeekBarChangeListener( this);
        Seekbar_color.setOnSeekBarChangeListener(this);
        return itemView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (listener != null){
            if (seekBar.getId()==R.id.seekbar_dept){
               // listener.onDepthChanged(progress+100);

            }
            else if(seekBar.getId()==R.id.seekbar_bw){
                progress = - progress ;
                listener.onColorChanged(progress);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(listener!=null){
            listener.EditStarted();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(listener!=null) {
            listener.onEditCompleted();
        }

    }

    public void resetControl (){
        /**Seekbar_brightness.setProgress(100);
         Seekbar_Contrast.setProgress(100);**/
    }
}
