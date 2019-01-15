package com.example.nutta.imagefilter;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.nutta.imagefilter.Adapter.colorAdapter;
import com.example.nutta.imagefilter.Interface.PaintFragmentListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaintFragement extends BottomSheetDialogFragment implements com.example.nutta.imagefilter.Adapter.colorAdapter.ColorAdapterListener {
    SeekBar seekBar_paint_size , SeekBar_paint_Opacity;
    ToggleButton btn_paint_state;
    RecyclerView recyclerView;
    colorAdapter colorAdapter;
    PaintFragmentListener listener;

    static PaintFragement instance;

    public static PaintFragement getInstance(){
        if (instance  == null)
            instance = new PaintFragement();
        return instance;
    }

    public void setListener(PaintFragmentListener listener) {
        this.listener = listener;
    }

    public PaintFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =  inflater.inflate(R.layout.fragment_paint_fragement, container, false);

        seekBar_paint_size = itemView.findViewById(R.id.SeekBar_Paint);
        SeekBar_paint_Opacity = itemView.findViewById(R.id.SeekBar_Opacity);
        btn_paint_state = itemView.findViewById(R.id.btn_paint_state);
        recyclerView = itemView.findViewById(R.id.Recycler_Color);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        colorAdapter =  new colorAdapter(getContext(),getColorList(),this);

        recyclerView.setAdapter(colorAdapter);


        SeekBar_paint_Opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onPaintOpacityChangeListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_paint_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onPaintSizeChangeListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_paint_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onPaintStateChangeListener(isChecked);
            }
        });


        return itemView;

    }
    private List<Integer> getColorList(){
            List<Integer> colorList =  new ArrayList<>();
            colorList.add(Color.parseColor("#FFFFFF"));
            colorList.add(Color.parseColor("#000000"));



        return  colorList;
    }


    @Override
    public void onColorSelected(int color) {

        listener.onPaintColorChangeListener(color);

    }
}
