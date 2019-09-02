package com.example.nutta.imagefilter.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nutta.imagefilter.Model.MuralItem;
import com.example.nutta.imagefilter.PageMyMural;
import com.example.nutta.imagefilter.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<MuralItem> {
private Context mContext;
private int mLayoutResID;
private ArrayList<MuralItem> mMutalItem;


    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MuralItem> objects) {
        super(context, resource, objects);

        this.mContext  = context;
        this.mLayoutResID =resource;
        this.mMutalItem = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =LayoutInflater.from(mContext);
        View ItemLayout =inflater.inflate(mLayoutResID,null);

        MuralItem muralItem = mMutalItem.get(position);

        TextView Name =ItemLayout.findViewById(R.id.Name_Mural);
        TextView User =ItemLayout.findViewById(R.id.USER_CREATE);
        Name.setText(muralItem.Name);
        User.setText(muralItem.User_CREATE);


        return ItemLayout;
    }
}
