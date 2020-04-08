package com.example.Marcel.oop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class DailyKcalActivity extends AppCompatActivity {

ListView listView;
TextView Data;
ImageButton buttonNext, buttonPrevious;
ArrayAdapter adapter;
ArrayList<String> productsName= new ArrayList<>();
ArrayList<Dni> listOfDni = new ArrayList<>();
AnimateHorizontalProgressBar progressBar;
String recievedDataValue="";
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
            e.printStackTrace();
            inputDate ="";
        }
        Data.setText(inputDate);
    }
public void changeItems(){
    int g=-1;
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
    if(g>=0){
    for (int i = 0; i <listOfDni.get(g).getSpis().size(); i++) {
        productsName.add(listOfDni.get(g).getSpis().get(i).getNazwa()+" x"+listOfDni.get(g).getSpis().get(i).getIlosc()+" kcal:"+listOfDni.get(g).getSpis().get(i).getKcal());
        suma_kcal+=listOfDni.get(g).getSpis().get(i).getKcal()*(listOfDni.get(g).getSpis().get(i).getIlosc()/100);
    }}
    progressBar.setMax(2100);
    Toast.makeText(this, suma_kcal+"", Toast.LENGTH_SHORT).show();
    progressBar.setProgress(suma_kcal);
    adapter.notifyDataSetChanged();

}
    public void addItems(){
    int g=-1;
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
        if(g>=0){
        for (int i = 0; i <listOfDni.get(g).getSpis().size(); i++) {
            productsName.add(listOfDni.get(g).getSpis().get(i).getNazwa()+" x"+listOfDni.get(g).getSpis().get(i).getIlosc()+" kcal:"+listOfDni.get(g).getSpis().get(i).getKcal());
            suma_kcal+=listOfDni.get(g).getSpis().get(i).getKcal()*(listOfDni.get(g).getSpis().get(i).getIlosc()/100);
        }
        progressBar.setMax(2100);
        progressBar.setProgress(suma_kcal);}
        adapter = new ArrayAdapter(DailyKcalActivity.this, android.R.layout.simple_list_item_1, productsName);
        listView.setAdapter(adapter);
    }

    public int checkIfExist(Jedzenie jedzenie,ArrayList<Jedzenie>lista){
        for (int i = 0; i <lista.size(); i++) {
            if(lista.get(i).getNazwa().equals(jedzenie.getNazwa())){
                return i;
            }
        }

    return -1;
    }
public int getIndex(ArrayList<Dni>lista,String a){
    Log.i("value",a);
    for (int i = 0; i <lista.size(); i++) {
        if(lista.get(i).getData().equals(a)){
            Log.i("lista",lista.get(i).getData());
            Log.i("recievedfor",a+" "+i );
            return i;
        }
    }
    return -1;
}
    public void putData(){
        ArrayList<Jedzenie> jedzenies = new ArrayList<>();

        if(getIntent().hasExtra("Jedzenie")&&ListViewDialog.done&&getIntent().hasExtra("Data")){
            Intent intent=getIntent();
            Jedzenie get=intent.getParcelableExtra("Jedzenie");
            recievedDataValue=intent.getExtras().getString("Data");
            Log.i("recieved",recievedDataValue);
            int ind = getIndex(listOfDni,recievedDataValue);
            Log.i("ind",ind+"");
            if(ind>=0){
            int check=checkIfExist(get,listOfDni.get(ind).getSpis());
            if(check<0){
                jedzenies.add(get);
                ListViewDialog.done=false;
                listOfDni.get(ind).getSpis().add(get);
            }
            if(check>=0){
                listOfDni.get(ind).getSpis().get(check).setIlosc(listOfDni.get(ind).getSpis().get(check).getIlosc()+get.getIlosc());
            }
            }
            if(ind<0){
                ArrayList<Jedzenie> temp=new ArrayList<>();
                temp.add(get);
                listOfDni.add(new Dni(recievedDataValue,temp));
            }
        }
        else{
            SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            int id = getIndex(listOfDni,Data.getText().toString());
            if(id<0){
            listOfDni.add(new Dni(Data.getText().toString(),jedzenies));}
        }
    }



    public void loadData() {
        SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefss", Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("Array", "");
        Log.i("jsonrec",text);
        Gson gson=new Gson();
        if(!text.equals("")){
        Type type = new TypeToken<ArrayList<Dni>>(){}.getType();

        listOfDni=gson.fromJson(text,type);}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_kcal);
        Data=findViewById(R.id.textViewData);
        buttonNext=findViewById(R.id.imageButtonNext);
        buttonPrevious=findViewById(R.id.imageButtonPrevious);
        listView=findViewById(R.id.listViewKcal);
        loadData();
         progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar1);
        ustawDate();


putData();
        ArrayList<Jedzenie> jedzeniess = new ArrayList<>();
        jedzeniess.add(new Jedzenie("Kielbasa12",600,"24/03/2000",12));
        jedzeniess.add(new Jedzenie("Kielbas12a1",100,"24/03/2000",123));
        jedzeniess.add(new Jedzenie("Kielbas122a1",100,"24/03/2000",12));
        listOfDni.add(new Dni("06-04-2020",jedzeniess));
        addItems();
    }

    @Override
    protected void onStop() {
    if(ListViewDialog.save){
        SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefss", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson=new Gson();
        String txt;
        txt=gson.toJson(listOfDni);
        Log.i("Json",txt);
        editor.putString("Array", txt);
        editor.apply();
        ListViewDialog.save=false;
    }
        super.onStop();
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
    public void Dodaj(View view){
        Intent intent = new Intent(DailyKcalActivity.this,AddActivity.class);
        SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", Data.getText().toString());

        editor.apply();

        DailyKcalActivity.this.startActivity(intent);
        finish();
    }
}
