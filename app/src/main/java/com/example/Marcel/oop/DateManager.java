package com.example.Marcel.oop;

import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
    private String data;
    TextView editText;
public DateManager(String data,TextView editText){
    this.data=data;
    this.editText=editText;
}
    public String getDate() {
        return data;
    }

    public void setDate(String data) {
        this.data = data;
    }
    public void ustawDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        editText.setText(dateFormat.format(date));
    }
    public void setPreviousDate(){

        String inputDate = data;
        SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, -1);
            inputDate = format.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            inputDate ="";
        }
        editText.setText(inputDate);
    }
    public void setNextDate(){

        String inputDate = data;
        SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, +1);
            inputDate = format.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            inputDate ="";
        }
        editText.setText(inputDate);
    }
}
