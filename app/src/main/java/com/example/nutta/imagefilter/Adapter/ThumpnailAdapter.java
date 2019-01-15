package com.example.nutta.imagefilter.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutta.imagefilter.Interface.FilterListFragmentListener;
import com.example.nutta.imagefilter.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

public class ThumpnailAdapter extends RecyclerView.Adapter<ThumpnailAdapter.MyviewHolder> {

    private List<ThumbnailItem> thumbnailItems;
    private FilterListFragmentListener listener;
    private Context context;
    private int selectedIndex = 0;

    public  ThumpnailAdapter(List<ThumbnailItem> thumpnailItems , FilterListFragmentListener listener,Context context){
         this.thumbnailItems = thumpnailItems;
         this.listener =listener;
         this.context = context;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.thumbnail,viewGroup,false);
        return new MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, final int i) {
            final ThumbnailItem thumbnailItem = thumbnailItems.get(i);
            myviewHolder.thumpnail.setImageBitmap(thumbnailItem.image);
            myviewHolder.thumpnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFilterselected(thumbnailItem.filter);
                    selectedIndex = i;
                    notifyDataSetChanged();
                }
            });
            myviewHolder.filter_name.setText(thumbnailItem.filterName);
            if(selectedIndex == i){
                myviewHolder.filter_name.setTextColor(ContextCompat.getColor(context,R.color.selected_filter));
            }
            else{
                myviewHolder.filter_name.setTextColor(ContextCompat.getColor(context,R.color.normal_filter));

            }

    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        ImageView thumpnail;
        TextView filter_name;
        public MyviewHolder(View itemView){
            super(itemView);
            thumpnail = itemView.findViewById(R.id.thumbnail);
            filter_name = itemView.findViewById(R.id.filter_name);
        }
    }
}
