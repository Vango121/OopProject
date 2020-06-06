package com.example.Marcel.oop.model;

/**
 * Main custom object class
 */

public class Produkt {



    private int id;
    private int kcal;
    private String Nazwa;
    private String data_przydatnosci;


    /**
     * empty constructor
     */
    public Produkt(){
        super();
    }

    /**
     * Constructor
     * @param id id in database
     * @param Nazwa
     * @param kcal
     * @param data_przydatnosci
     */
    public Produkt(int id,String Nazwa,int kcal,String data_przydatnosci){
        this.id=id;
        this.kcal=kcal;
        this.Nazwa=Nazwa;
        this.data_przydatnosci=data_przydatnosci;

    }
    public Produkt(String Nazwa,int kcal,String data_przydatnosci){

        this.kcal=kcal;
        this.Nazwa=Nazwa;
        this.data_przydatnosci=data_przydatnosci;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getData_przydatnosci() {
        return data_przydatnosci;
    }

    public void setData_przydatnosci(String data_przydatnosci) {
        this.data_przydatnosci = data_przydatnosci;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }
}
