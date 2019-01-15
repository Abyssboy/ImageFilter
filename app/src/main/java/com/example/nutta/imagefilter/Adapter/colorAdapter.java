package com.example.nutta.imagefilter.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutta.imagefilter.R;

import java.util.List;

public class colorAdapter extends RecyclerView.Adapter<colorAdapter.ColorViewholder> {
    Context context ;
    List<Integer> colorList;
    ColorAdapterListener listener;

    public colorAdapter(Context context,List<Integer> colorList, ColorAdapterListener listener){
        this.context = context;
        this.colorList = colorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ColorViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.color_item,parent,false);
        return new ColorViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewholder colorViewholder, int i) {
            colorViewholder.color_section.setCardBackgroundColor(colorList.get(i));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewholder  extends  RecyclerView.ViewHolder{

        CardView color_section;




        public ColorViewholder(@NonNull View itemView) {
            super(itemView);
            color_section =  itemView.findViewById(R.id.Color_section);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        listener.onColorSelected(colorList.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface ColorAdapterListener{
        void onColorSelected(int color);
    }
}
