package com.example.Marcel.oop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyKcalActivity extends AppCompatActivity {

ListView listView;
TextView Data;
ImageButton buttonNext, buttonPrevious;
ArrayAdapter adapter;
ArrayList<String> productsName= new ArrayList<>();

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

    public void addItems(){

productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");
        productsName.add("Kielbasa x1 kcal 500");


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
        ustawDate();
        addItems();
    }
    public void ChangeData(View view){
    switch (view.getId()){
        case R.id.imageButtonNext:
            setNextDate();
            break;
        case R.id.imageButtonPrevious:
            setPreviousDate();
            break;
    }
    }
}
