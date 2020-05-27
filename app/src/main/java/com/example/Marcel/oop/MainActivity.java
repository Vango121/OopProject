package com.example.Marcel.oop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * main class
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener , DialogListener {


    EditText edtProductName, edtProductKcal, edtProductAmount, edtProductDate;
    Button btnAdd, btnDelete;
    ListView listView;
    int clicked_number; // pressed id from listview
    List<Produkt> allProducts;
    ArrayList<String> productsName;
TextInputLayout textInputLayout;

    MySqliteHandler databaseHandler;

    ArrayAdapter adapter;


    int newkcal;
    int newilosc;
    String newNazwa;
    String newData;

    boolean delete_active=false;

    /**
     * Android lifecycle method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtProductName = (EditText) findViewById(R.id.edtProductName);
        edtProductKcal = (EditText) findViewById(R.id.edtProductKcal);
        edtProductAmount = (EditText) findViewById(R.id.edtProductCount);
        edtProductDate = (EditText) findViewById(R.id.edtProductDate);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        listView = (ListView) findViewById(R.id.listView);
        textInputLayout = findViewById(R.id.textField);
        btnAdd.setOnClickListener(MainActivity.this);
        btnDelete.setOnClickListener(MainActivity.this);
        databaseHandler = new MySqliteHandler(MainActivity.this);
        allProducts = databaseHandler.getAllProducts();
        productsName = new ArrayList<>();
        addItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(delete_active){
                    productsName.remove(i);
                    databaseHandler.deleteProduct(allProducts.get(i));
                    allProducts.remove(i);
                    adapter.notifyDataSetChanged();
                    delete_active=false;
                }
                else{
                    openDialog();
                    clicked_number=i;
                }
            }
        });
        DateInputMask e=new DateInputMask(edtProductDate, textInputLayout);
    }


    //menu

    /**
     * load menu xml file
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * on menu item click
     * @param item item clicked
     * @return only true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.kalkulator:
                Intent intent = new Intent(MainActivity.this,DailyKcalActivity.class);
                MainActivity.this.startActivity(intent);
                return true;
        }
        return true;
    }

    /**
     * add item to database(Litesql) and list of current items
     */
    public void addItems(){
        productsName.clear();
        if (allProducts.size() > 0) {

            for (int i = 0; i < allProducts.size(); i++) {
                Jedzenie produkt = (Jedzenie)allProducts.get(i);
                productsName.add(produkt.getNazwa() + " Ilosc: " +produkt.getIlosc()+ " Data przydatnosci: "+ produkt.getData_przydatnosci()+" Kcal: "+produkt.getKcal());
            }
        }
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, productsName);
        listView.setAdapter(adapter);
    }

    /**
     * Method which open custom dialog
     * @see UpdateDialog
     * and update clicked item values
     */
    public void openDialog(){
        UpdateDialog updateDialog = new UpdateDialog();

        updateDialog.show(getSupportFragmentManager(),"update");
    }

    /**
     * Handle add button and delete button
     * @param view
     */
    @Override
    public void onClick(View view) {



        switch (view.getId()) {

            case R.id.btnAdd:

                if (edtProductName.getText().toString().matches("") || edtProductKcal.getText().toString().matches("")|| edtProductAmount.getText().toString().matches("")|| edtProductDate.getText().toString().matches("")) {
                    return;
                }
                //Produkt produkt = new Produkt(edtProductName.getText().toString(), Integer.valueOf(edtProductKcal.getText().toString()));
                Jedzenie  jedzenie = new Jedzenie();
                jedzenie.setNazwa(edtProductName.getText().toString());
                jedzenie.setKcal(Integer.valueOf(edtProductKcal.getText().toString()));
                jedzenie.setIlosc(Integer.valueOf(edtProductAmount.getText().toString()));
                jedzenie.setData_przydatnosci(edtProductDate.getText().toString());

                allProducts.add(jedzenie);
                databaseHandler.addProduct(jedzenie);
                productsName.add(jedzenie.getNazwa() + " Ilosc: " +jedzenie.getIlosc()+ " Data przydatnosci: "+ jedzenie.getData_przydatnosci()+" Kcal: "+jedzenie.getKcal());
                edtProductName.setText("");
                edtProductKcal.setText("");
                edtProductAmount.setText("");
                edtProductDate.setText("");
                break;
            case R.id.btnDelete:
                delete_active=true;
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     *  Method from interface
     * @see DialogListener
     * get data from interface and update item list
     * @param nazwa
     * @param kcal
     * @param data
     * @param ilosc
     */
    @Override
    public void apply(String nazwa, String kcal, String data, String ilosc) {
    newkcal=Integer.parseInt(kcal);
    newilosc = Integer.parseInt(ilosc);
    newNazwa=nazwa;
    newData=data;
        Jedzenie jedzenie = (Jedzenie)allProducts.get(clicked_number);
        jedzenie.setId(clicked_number);
        jedzenie.setData_przydatnosci(newData);
        jedzenie.setKcal(newkcal);
        jedzenie.setIlosc(newilosc);
        jedzenie.setNazwa(newNazwa);
        databaseHandler.updateProduct(jedzenie);
        addItems();
    }

    /**
     * unfortunately empty method from interface
     * @param jedzenie custom object
     */
    @Override
    public void pass(Jedzenie jedzenie) {
    }
}
