package com.example.Marcel.oop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Marcel.oop.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , DialogListener {


    EditText edtProductName, edtProductKcal, edtProductAmount, edtProductDate;
    Button btnAdd, btnDelete;
    ListView listView;

    int clicked_number; // pressed id from listview
    List<Produkt> allProducts;
    ArrayList<String> productsName;


    MySqliteHandler databaseHandler;

    ArrayAdapter adapter;


    int newkcal;
    int newilosc;
    String newNazwa;
    String newData;

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



        btnAdd.setOnClickListener(MainActivity.this);
        btnDelete.setOnClickListener(MainActivity.this);




        databaseHandler = new MySqliteHandler(MainActivity.this);
        allProducts = databaseHandler.getAllProducts();
        productsName = new ArrayList<>();


        addItems();






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                Toast.makeText(MainActivity.this, allProducts.get(i).getNazwa(), Toast.LENGTH_SHORT).show();
                clicked_number=i;
            }
        });

    }
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
    public void openDialog(){
        UpdateDialog updateDialog = new UpdateDialog();

        updateDialog.show(getSupportFragmentManager(),"update");
    }
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


                if (allProducts.size() > 0) {
                    productsName.remove(0);
                    databaseHandler.deleteProduct(allProducts.get(0));
                    allProducts.remove(0);
                } else {
                    return;
                }



                break;


        }

        adapter.notifyDataSetChanged();

    }

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
}
