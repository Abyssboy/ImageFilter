package com.example.nutta.imagefilter.Interface;

public interface BWFragmentListener {
   // void onDepthChanged(int Depth);
    void onColorChanged(int contrast);
    //void BWChanged(int finalDepth,finalcontrast)
    void EditStarted();
    void onEditCompleted();
}
