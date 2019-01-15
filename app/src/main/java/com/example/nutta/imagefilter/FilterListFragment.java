package com.example.nutta.imagefilter;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutta.imagefilter.Adapter.ThumpnailAdapter;
import com.example.nutta.imagefilter.Interface.FilterListFragmentListener;
import com.example.nutta.imagefilter.Utils.BitmapUtils;
import com.example.nutta.imagefilter.Utils.SpacesItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class FilterListFragment extends Fragment implements FilterListFragmentListener {*/
public class FilterListFragment extends BottomSheetDialogFragment implements FilterListFragmentListener {

    RecyclerView recyclerView;
    ThumpnailAdapter adapter;
    List<ThumbnailItem>  thumbnailItems;

    FilterListFragmentListener listener; /*v2*/

    static FilterListFragment instance;/*v2*/



    public static FilterListFragment getInstance(){     /*v2*/
        if(instance == null )
            instance = new FilterListFragment();
        return instance;
    }

    public void setListener(FilterListFragmentListener listener) {
        this.listener = listener;
    }

    public FilterListFragment() {
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
       View itemView =  inflater.inflate(R.layout.fragment_filter_list, container, false);

       thumbnailItems = new ArrayList<>();
       adapter = new ThumpnailAdapter(thumbnailItems,this,getActivity());
       recyclerView = itemView.findViewById(R.id.RecyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       int space  =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
       recyclerView.addItemDecoration(new SpacesItemDecoration(space));
       recyclerView.setAdapter(adapter);

       displayThumbnail(null);

       return  itemView;
    }

    public void displayThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                    Bitmap thumpImg;
                    if (bitmap == null) {
                        thumpImg = BitmapUtils.getBitmapFromAssets(getActivity(), MainActivity.pictureName, 100, 100);

                    } else
                        thumpImg = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

                    if (thumpImg == null)
                        return;
                    ThumbnailsManager.clearThumbs();
                    thumbnailItems.clear();

                    ThumbnailItem thumbnailItem = new ThumbnailItem();
                    thumbnailItem.image = thumpImg;
                    thumbnailItem.filterName = "Normal";
                    ThumbnailsManager.addThumb(thumbnailItem);

                    List<Filter> filters = FilterPack.getFilterPack(getActivity());

                    for (Filter filter : filters) {

                        ThumbnailItem TI = new ThumbnailItem();
                        TI.image = thumpImg;
                        TI.filter = filter;
                        TI.filterName = filter.getName();
                        ThumbnailsManager.addThumb(TI);

                    }
                    thumbnailItems.addAll(ThumbnailsManager.processThumbs(getActivity()));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });


                }
            };
                new Thread(r).start();

            }

    @Override
    public void onFilterselected(Filter filter) {
        if (listener !=null){
             listener.onFilterselected(filter);
        }
    }
}

