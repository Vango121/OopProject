package com.example.Marcel.oop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * listview with dialog on click class
 */
public class ListViewDialog implements AdapterView.OnItemClickListener {
    ListView listView;
    String recieveValue;
    Button ok;
    List<Produkt> lista;
    private Jedzenie jedzenie;
    private Context context;
    private int id;
    static boolean done;
    static boolean save=false;
    AddActivity addActivity;

    /**
     * Class constructor
     * @param ok Ok button
     * @param listView
     * @param context app context
     * @param lista array of produkt
     */
    public ListViewDialog(Button ok, ListView listView, Context context, List<Produkt> lista){
        this.listView=listView;
        this.ok=ok;
        this.context=context;
        this.lista=lista;
        listView.setOnItemClickListener(this);
    }

    /**
     * get data from sharedpreferences
     * @return String Data
     */
    public String loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("data", "");
        return text;
    }

    /**
     * ListView on item click which trigger custom dialog
     * @param adapterView
     * @param view
     * @param i index of item clicked
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        id=i;
        final Dialog dialog =new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Ustaw");
        final EditText edtInput=dialog.findViewById(R.id.input);
        ok= dialog.findViewById(R.id.buttonOK);
        Button cancel= dialog.findViewById(R.id.buttonAnuluj);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recieveValue=edtInput.getText().toString();
                Toast.makeText(context, recieveValue, Toast.LENGTH_SHORT).show();
                jedzenie=(Jedzenie)lista.get(id);
                jedzenie.setIlosc(Integer.parseInt(recieveValue));
                Intent intent = new Intent(context,DailyKcalActivity.class);
               intent.putExtra("Jedzenie",jedzenie);
               intent.putExtra("Data",loadData());
                context.startActivity(intent);
                done=true;
                save=true;
                dialog.dismiss();
                ((Activity)context).finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public Jedzenie getJedzenie() {
        return jedzenie;
    }

    public int getId(){
        return id;
    }
}
