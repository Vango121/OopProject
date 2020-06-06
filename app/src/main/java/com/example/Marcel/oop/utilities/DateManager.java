package com.example.Marcel.oop.utilities;

import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class to manage data
 */
public class DateManager {
    private String data;
    TextView editText;
public DateManager(String data,TextView editText){
    this.data=data;
    this.editText=editText;
}

    /**
     * @deprecated not used
     * @return
     */
    public String getDate() {
        return data;
    }

    /**
     * Simple setter
     * @param data
     */
    public void setDate(String data) {
        this.data = data;
    }

    /**
     * get date from calendar and set in to TextView given in constructor
     */
    public void ustawDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
        editText.setText(dateFormat.format(date));
    }

    /**
     * set date from the day before
     */
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

    /**
     * set next day date
     */
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
