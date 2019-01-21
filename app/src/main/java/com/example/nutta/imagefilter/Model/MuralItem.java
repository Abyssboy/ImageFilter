package com.example.nutta.imagefilter.Model;

public class MuralItem {
    public final String ID;
    public final String Name;

    public MuralItem(String id, String name) {
        ID = id;
        Name = name;
    }

    public String toString(){
        return Name;
    }
}
