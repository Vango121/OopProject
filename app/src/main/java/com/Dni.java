package com;

import com.example.Marcel.oop.Jedzenie;

import java.util.ArrayList;

public class Dni {
    ArrayList<Jedzenie> spis;
    String data;
public Dni(String data,ArrayList<Jedzenie> spis){
    this.data=data;
    this.spis=spis;

}
    public ArrayList<Jedzenie> getSpis() {
        return spis;
    }

    public void setSpis(ArrayList<Jedzenie> spis) {
        this.spis = spis;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
