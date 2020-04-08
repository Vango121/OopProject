package com.example.Marcel.oop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vango on 29.03.2020.
 */

public class Jedzenie extends Produkt implements Parcelable {
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


    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(getNazwa());
        dest.writeInt(getKcal());
        dest.writeString(getData_przydatnosci());
        dest.writeInt(getIlosc());
    }

    //constructor used for parcel
    public Jedzenie(Parcel parcel){
        setNazwa(parcel.readString());
        setKcal(parcel.readInt());
        setData_przydatnosci(parcel.readString());
        setIlosc(parcel.readInt());
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Jedzenie> CREATOR = new Parcelable.Creator<Jedzenie>(){


        @Override
        public Jedzenie createFromParcel(Parcel parcel) {
            return new Jedzenie(parcel);
        }

        @Override
        public Jedzenie[] newArray(int size) {
            return new Jedzenie[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
