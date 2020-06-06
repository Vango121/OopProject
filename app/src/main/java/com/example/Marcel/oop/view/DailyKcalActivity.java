package com.example.Marcel.oop.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.example.Marcel.oop.R;
import com.example.Marcel.oop.model.Dni;
import com.example.Marcel.oop.model.Jedzenie;
import com.example.Marcel.oop.presenter.DailyKcalPresenter;
import com.example.Marcel.oop.utilities.DateManager;
import com.example.Marcel.oop.utilities.ListViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity class
 */
public class DailyKcalActivity extends AppCompatActivity  {

ListView listView;
TextView Data;
ImageButton buttonNext, buttonPrevious;
ArrayAdapter adapter;
DateManager dateManager;
ArrayList<String> productsName= new ArrayList<>();
ArrayList<Dni> listOfDni = new ArrayList<>();
AnimateHorizontalProgressBar progressBar;
String recievedDataValue="";
DailyKcalPresenter dailyKcalPresenter;
int max_kcal=2100;

    /**
     * This method change item list in list view and change kcal proggress bar.
     */
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
        suma_kcal+=(listOfDni.get(g).getSpis().get(i).getKcal()*(listOfDni.get(g).getSpis().get(i).getIlosc())/100);

    }}
    progressBar.setMax(max_kcal);
    progressBar.setProgress(suma_kcal);

    adapter.notifyDataSetChanged();

}

    /**
     * just like changeitems without adapter notification
     */
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
            suma_kcal+=(listOfDni.get(g).getSpis().get(i).getKcal()*(listOfDni.get(g).getSpis().get(i).getIlosc())/100);

        }
        progressBar.setMax(max_kcal);
        progressBar.setProgress(suma_kcal);}

        adapter = new ArrayAdapter(DailyKcalActivity.this, android.R.layout.simple_list_item_1, productsName);
        listView.setAdapter(adapter);
    }

    /**
     * check if 'jedzenie' object is in Array
     * @param jedzenie custom food object
     * @param list list of food objects
     * @return -1 or item index if found
     */
    public int checkIfExist(Jedzenie jedzenie, ArrayList<Jedzenie>list){
        for (int i = 0; i <list.size(); i++) {
            if(list.get(i).getNazwa().equals(jedzenie.getNazwa())){
                return i;
            }
        }

    return -1;
    }

    /**
     * check if picked day already exist in ArrayList of 'Dni'
     * @param list list of 'Dni'
     * @param a String with data to check if exist in list
     * @return index or -1
     */
    public int getIndex(ArrayList<Dni>list,String a){
    for (int i = 0; i <list.size(); i++) {
        if(list.get(i).getData().equals(a)){
            Log.i("lista",list.get(i).getData());
            Log.i("recievedfor",a+" "+i );
            return i;
        }
    }
    return -1;
}

    /**
     * Method to put Data passed from previous activity in Array and save in Shared preferences
     */
    public void putData(){
        ArrayList<Jedzenie> jedzenies = new ArrayList<>();
        if(getIntent().hasExtra("Jedzenie")&& ListViewDialog.done&&getIntent().hasExtra("Data")){
            Intent intent=getIntent();
            Jedzenie get=intent.getParcelableExtra("Jedzenie");
            recievedDataValue=intent.getExtras().getString("Data");

            int ind = getIndex(listOfDni,recievedDataValue);
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


    /**
     * Get json data from shared preference and pass it as Array
     */
    public void loadData() {
        SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefss", Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("Array", "");
        Log.i("jsonrec",text);
        Gson gson=new Gson();
        if(!text.equals("")){
        Type type = new TypeToken<ArrayList<Dni>>(){}.getType();

        listOfDni=gson.fromJson(text,type);}
    }

    /**
     * Android lifecycle method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_kcal);
        Data=findViewById(R.id.textViewData);
        buttonNext=findViewById(R.id.imageButtonNext);
        buttonPrevious=findViewById(R.id.imageButtonPrevious);
        listView=findViewById(R.id.listViewKcal);
        max_kcal=Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("edit_text_preference_kcal", "2100"));
        loadData();
         progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar1);
        //ustawDate();
        dateManager=new DateManager(Data.getText().toString(),Data);
        dateManager.ustawDate();
        putData();
        addItems();
    }

    /**
     * Android lifecycle method. Method changeItems is used here
     */
    @Override
    protected void onResume() {
        max_kcal=Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("edit_text_preference_kcal", "2100"));
        progressBar.setMax(max_kcal);
        Log.i("max kcal",max_kcal+"");
       changeItems();
        super.onResume();
    }

    /**
     * Android lifecycle method save data from all days in sharedpreferences
     */
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

    /**
     * Method that change data in TextView and handle button clicks
     * @param view
     */
    public void ChangeData(View view){
    switch (view.getId()){
        case R.id.imageButtonNext:
            dateManager.setDate(Data.getText().toString());
            dateManager.setNextDate();
            changeItems();
            break;
        case R.id.imageButtonPrevious:
            //setPreviousDate();
            dateManager.setDate(Data.getText().toString());
            dateManager.setPreviousDate();
            changeItems();
            break;
    }
    }

    /**
     * Method that handle button click, and open new activity
     * @see AddActivity
     * Also send data from textView
     * @param view
     */
    public void Dodaj(View view){
        Intent intent = new Intent(DailyKcalActivity.this,AddActivity.class);
        SharedPreferences sharedPreferences = DailyKcalActivity.this.getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", Data.getText().toString());

        editor.apply();

        DailyKcalActivity.this.startActivity(intent);
        finish();
    }
    //menu

    /**
     * Menu method
     * @param menu
     * @return method return only true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudailykcal,menu);

        return true;
    }

    /**
     * Method which handle menu items in this case only settings
     * @param item menu item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Ustawienia:
                Intent intent = new Intent(DailyKcalActivity.this,SettingsActivity.class);
                DailyKcalActivity.this.startActivity(intent);

                return true;
        }
        return true;
    }


}
