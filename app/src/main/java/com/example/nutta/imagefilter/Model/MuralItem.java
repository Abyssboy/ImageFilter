package com.example.nutta.imagefilter.Model;

public class MuralItem {
    public final String ID;
    public final String Name;
    public final String User_CREATE;

    public MuralItem(String id, String name,String User_Create) {
        ID = id;
        Name = name;
        User_CREATE = User_Create;
    }

    public String toString(){
        return ID;
    }
}
