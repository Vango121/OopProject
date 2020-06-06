package com.example.Marcel.oop.view;

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

import com.example.Marcel.oop.R;
import com.example.Marcel.oop.model.Produkt;
import com.example.Marcel.oop.presenter.MainPresenter;
import com.example.Marcel.oop.utilities.DateInputMask;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * main class
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener , MainPresenter.MainInterface {
    EditText edtProductName, edtProductKcal, edtProductAmount, edtProductDate;
    Button btnAdd, btnDelete;
    ListView listView;
    List<Produkt> allProducts;
    ArrayList<String> productsName;
TextInputLayout textInputLayout;
    MainPresenter mainPresenter;
    ArrayAdapter adapter;
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
        mainPresenter=new MainPresenter(MainActivity.this,this);
        mainPresenter.addItems();
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
     * Method to open item udate dialog
     */
    public void openDialog(){
        UpdateDialog updateDialog = new UpdateDialog(mainPresenter);
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
                mainPresenter.buttonAdd(edtProductName.getText().toString(),edtProductKcal.getText().toString(),edtProductAmount.getText().toString(),edtProductDate.getText().toString());
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
     * Overriden method from interface in presenter add onitem click to listview with products
     * @param productsName Array with items that show in listview
     */
    @Override
    public void createListView(ArrayList<String> productsName) {
        this.productsName=productsName;
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, this.productsName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(delete_active){
                    mainPresenter.buttonDelete_item();
                    delete_active=false;
                }
                else{
                    openDialog();
                    mainPresenter.setclickedNumber(i);
                }
            }
        });
    }
    /**
     * Overriden method from interface in presenter update listview with new product list
     * @param productsName Array with items that show in listview
     */
    @Override
    public void updateListView(ArrayList<String> productsName){
        this.productsName=productsName;
        adapter.notifyDataSetChanged();
    }
}
