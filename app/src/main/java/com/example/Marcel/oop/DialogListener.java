package com.example.Marcel.oop;

/**
 * pass data when triggered to another class
 */

public interface DialogListener {
    void apply(String nazwa,String kcal,String data,String ilosc);
    void pass(Jedzenie jedzenie);
}
