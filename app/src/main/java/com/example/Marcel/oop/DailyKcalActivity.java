package com.example.Marcel.oop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.Dni;
import com.daasuu.ahp.AnimateHorizontalProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DailyKcalActivity extends AppCompatActivity {

ListView listView;
TextView Data;
ImageButton buttonNext, buttonPrevious;
ArrayAdapter adapter;
ArrayList<String> productsName= new ArrayList<>();
ArrayList<Dni> listOfDni = new ArrayList<>();
AnimateHorizontalProgressBar progressBar;
private void ustawDate(){
    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
    Data.setText(dateFormat.format(date));
}
private void setPreviousDate(){

        String inputDate = Data.getText().toString();
        SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, -1);
            inputDate = format.format(c.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            inputDate ="";
        }
        Data.setText(inputDate);
    }
    private void setNextDate(){

        String inputDate = Data.getText().toString();
        SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, +1);
            inputDate = format.format(c.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            inputDate ="";
        }
        Data.setText(inputDate);
    }
public void changeItems(){
    int g=0;
    if(productsName.size()>0){
        productsName.clear();
    }
    for (int i = 0; i <listOfDni.size(); i++) {
        String temp = listOfDni.get(i).getData();
        if(temp.equals(Data.getText().toString())){
            g=i;
            break;
        }
    }
    int suma_kcal=0;
    for (int i = 0; i <listOfDni.get(g).getSpis().size(); i++) {
        productsName.add(listOfDni.get(g).getSpis().get(i).getNazwa()+" x"+listOfDni.get(g).getSpis().get(i).getIlosc()+" kcal:"+listOfDni.get(g).getSpis().get(i).getKcal());
        suma_kcal+=listOfDni.get(g).getSpis().get(i).getKcal();
    }
    progressBar.setMax(2100);
    progressBar.setProgress(suma_kcal);
    adapter.notifyDataSetChanged();
}
    public void addItems(){
    int g=0;
    if(productsName.size()>0){
        productsName.clear();
    }
        for (int i = 0; i <listOfDni.size(); i++) {
            String temp = listOfDni.get(i).getData();
            if(temp.equals(Data.getText().toString())){
                g=i;
                break;
            }
        }
        int suma_kcal=0;
        for (int i = 0; i <listOfDni.get(g).getSpis().size(); i++) {
            productsName.add(listOfDni.get(g).getSpis().get(i).getNazwa()+" x"+listOfDni.get(g).getSpis().get(i).getIlosc()+" kcal:"+listOfDni.get(g).getSpis().get(i).getKcal());
            suma_kcal+=listOfDni.get(g).getSpis().get(i).getKcal();
        }
        progressBar.setMax(2100);
        progressBar.setProgress(suma_kcal);
        adapter = new ArrayAdapter(DailyKcalActivity.this, android.R.layout.simple_list_item_1, productsName);
        listView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_kcal);
        Data=findViewById(R.id.textViewData);
        buttonNext=findViewById(R.id.imageButtonNext);
        buttonPrevious=findViewById(R.id.imageButtonPrevious);
        listView=findViewById(R.id.listViewKcal);
         progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar1);
        ustawDate();

        ArrayList<Jedzenie> jedzenies = new ArrayList<>();
        jedzenies.add(new Jedzenie("Kielbasa",100,"24/03/2000",12));
        jedzenies.add(new Jedzenie("Kielbas12a",100,"24/03/2000",123));
        jedzenies.add(new Jedzenie("Kielbas122a",100,"24/03/2000",12));
        listOfDni.add(new Dni(Data.getText().toString(),jedzenies));
        ArrayList<Jedzenie> jedzeniess = new ArrayList<>();
        jedzeniess.add(new Jedzenie("Kielbasa12",600,"24/03/2000",12));
        jedzeniess.add(new Jedzenie("Kielbas12a1",100,"24/03/2000",123));
        jedzeniess.add(new Jedzenie("Kielbas122a1",100,"24/03/2000",12));
        listOfDni.add(new Dni("06-04-2020",jedzeniess));
        addItems();
    }
    public void ChangeData(View view){
    switch (view.getId()){
        case R.id.imageButtonNext:
            setNextDate();
            changeItems();
            break;
        case R.id.imageButtonPrevious:
            setPreviousDate();
            changeItems();
            break;
    }
    }
}
