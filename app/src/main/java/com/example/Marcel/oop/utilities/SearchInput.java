package com.example.Marcel.oop.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * class that handle searching in array
 */
public class SearchInput implements TextWatcher{
    ListView listView;
    EditText search;
    ArrayAdapter adapter;
    ArrayList<String> a;
    ArrayList<String>temp;

    /**
     * constructor
     * @param adapter ArrayAdapter
     * @param listView listview
     * @param search EditText in which we write
     * @param a ArrayList that have results
     */
    public SearchInput(ArrayAdapter adapter, ListView listView, EditText search, ArrayList<String>a){
        this.adapter=adapter;
        this.listView=listView;
        this.search=search;
        this.a=a;
        temp=new ArrayList<>();
        temp.addAll(a);
        search.addTextChangedListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * method trigger when text is changed in editext
     * @param charSequence text from edittext
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String val = search.getText().toString();

        Log.i("temp",temp.size()+"");
        a.clear();
        for (int j = 0; j <temp.size(); j++) {

            if(temp.get(j).contains(val)){
                a.add(temp.get(j));

            }
        }
        adapter.notifyDataSetChanged();
        if(val.length()==0){

            a.clear();
            a.addAll(temp);

            Log.i("po wyzerowaniu",temp.size()+"");

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
