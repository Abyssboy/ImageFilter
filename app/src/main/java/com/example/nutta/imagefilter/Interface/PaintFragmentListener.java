package com.example.nutta.imagefilter.Interface;

public interface PaintFragmentListener {
    void onPaintSizeChangeListener(float size);
    void onPaintOpacityChangeListener(int opacity);
    void onPaintColorChangeListener(int color);
    void onPaintStateChangeListener(Boolean isErase);



}
