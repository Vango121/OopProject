package com.example.Marcel.oop;

/**
 * Created by Vango on 29.03.2020.
 */

public class Jedzenie extends Produkt {
    private int ilosc;

    public Jedzenie(){
super();
    }
    public Jedzenie(int id,String Nazwa,int kcal,String data_przydatnosci,int ilosc) {
        super(id,Nazwa,kcal,data_przydatnosci);
        this.ilosc=ilosc;
    }
    public Jedzenie(String Nazwa,int kcal,String data_przydatnosci,int ilosc) {
        super(Nazwa, kcal, data_przydatnosci);
        this.ilosc=ilosc;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }
}
