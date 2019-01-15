package com.example.nutta.imagefilter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.nutta.imagefilter.Interface.EditImageFragmentListener;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class EditImageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {  V.1*/
public class EditImageFragment extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListener listener;
    SeekBar Seekbar_brightness,Seekbar_Contrast;

    static EditImageFragment instance;/*v2*/

    public static EditImageFragment getInstance(){
        if(instance == null){
            instance = new EditImageFragment();
        }
        return instance;
    }
    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public EditImageFragment() {
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
        View itemView = inflater.inflate(R.layout.fragment_edit_image, container, false);
        Seekbar_brightness =  itemView.findViewById(R.id.seekbar_brightness);
        Seekbar_Contrast = itemView.findViewById(R.id.seekbar_Contrast);

        /*Seekbar_brightness.setMax(200);
        Seekbar_brightness.setProgress(100);

        Seekbar_Contrast.setMax(200);
        Seekbar_brightness.setProgress(100);*/
        Seekbar_brightness.setMax(200);
        Seekbar_brightness.setProgress(100);

        Seekbar_Contrast.setMax(20);
        Seekbar_Contrast.setProgress(0);

        Seekbar_Contrast.setOnSeekBarChangeListener( this);
        Seekbar_brightness.setOnSeekBarChangeListener(this);
        return itemView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (listener != null){
            if (seekBar.getId()==R.id.seekbar_brightness){
                listener.onBrightnessChanged(progress-100);

            }
            else if(seekBar.getId()==R.id.seekbar_Contrast){
                progress += 10;
                float value = .10f*progress;
                listener.onContrastChanged(value);
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
