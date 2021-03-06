package com.example.Marcel.oop.view;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.Marcel.oop.R;
import com.example.Marcel.oop.data.MySqliteHandler;
import com.example.Marcel.oop.model.Jedzenie;
import com.example.Marcel.oop.model.Produkt;
import com.example.Marcel.oop.utilities.ListViewDialog;
import com.example.Marcel.oop.utilities.SearchInput;

import java.util.ArrayList;
import java.util.List;

/**
 * class to find product and add it into selected day
 */
public class AddActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    ListView listView;
    EditText search;
    ArrayList<String>temp;
    ArrayList<String>a;
    List<Produkt> allProducts;
    MySqliteHandler databaseHandler;
    Button ok;
    boolean exist=false;
    Jedzenie jedzenie;//item to pass
    ArrayList<Jedzenie>listToPass = new ArrayList<>();

    /**
     * This metod get data from litesql
     */
    public void getItems(){
        allProducts=new ArrayList<>();
        databaseHandler = new MySqliteHandler(AddActivity.this);
        allProducts = databaseHandler.getAllProducts();
        for (int i = 0; i <allProducts.size(); i++) {
        a.add(allProducts.get(i).getNazwa());
        }
    }

    /**
     *  Android Lifecycle Method
     * @param savedInstanceState bundle in which can be stored data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        listView = findViewById(R.id.ListViewpick);
        search= findViewById(R.id.editTextAdd);
        a = new ArrayList<>();
        getItems();
        getIntent().removeExtra("Jedzenie");
        adapter = new ArrayAdapter(AddActivity.this, android.R.layout.simple_list_item_1, a);
        listView.setAdapter(adapter);
        SearchInput searchInput=new SearchInput(adapter,listView,search,a);
        ListViewDialog listViewDialog = new ListViewDialog(ok,listView,AddActivity.this,allProducts);
        if(exist){
        jedzenie=listViewDialog.getJedzenie();

        listToPass.add(jedzenie);
        exist=false;
        }
    }
    /*
    public void setValue(boolean existt){
       exist=existt;
    }
    */

    /**
     * Method that handle back press, and pass data
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddActivity.this,DailyKcalActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("Jedzenie",listToPass);
        intent.putExtra("Jedzenie",bundle);
        startActivity(intent);
        finish();
    }
}
