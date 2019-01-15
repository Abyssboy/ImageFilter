package com.example.nutta.imagefilter.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class NonswipeableViewPager extends ViewPager {
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public NonswipeableViewPager(@NonNull Context context) {
        super(context);
        setMyScoller();
    }

    private void setMyScoller() {
        try {
            Class<?> Viewpager = ViewPager.class;
            Field Scroller =  Viewpager.getDeclaredField("mScroller");
            Scroller.setAccessible(true);
            Scroller.set(this,new MyScroller(getContext()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public NonswipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMyScoller();
    }

    private class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super (context,new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,int duation) {
            super.startScroll(startX, startY, dx, dy,400);
        }
    }
}
