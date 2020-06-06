package com.example.Marcel.oop.presenter;

import android.content.Context;
import android.util.Log;

import com.example.Marcel.oop.view.UpdateDialog;
import com.example.Marcel.oop.data.MySqliteHandler;
import com.example.Marcel.oop.model.Jedzenie;
import com.example.Marcel.oop.model.MainActivityModel;

import java.util.ArrayList;

/**
 * Presenter class for MainActivity
 */
public class MainPresenter implements UpdateDialog.DialogListener1 {
    private MySqliteHandler databaseHandler;
    private Context context;
    private MainActivityModel mainActivityModel;
    private MainInterface mainInterface;
    private int clicked_number;

    /**
     * Default constructor
     * @param context view context
     * @param mainInterface interface from view
     */
    public MainPresenter(Context context,MainInterface mainInterface) {
        this.context=context;
        databaseHandler = new MySqliteHandler(context);
        mainActivityModel=new MainActivityModel();
        mainActivityModel.setAllProducts(databaseHandler.getAllProducts());
        this.mainInterface=mainInterface;
    }
    /**
     * add item to database(Litesql) and list of current items
     */
    public void addItems(){
        ArrayList<String> productsName1=new ArrayList<>();
        mainActivityModel.getProductsName().clear();
        mainActivityModel.setAllProducts(databaseHandler.getAllProducts());
        if (mainActivityModel.getAllProducts().size() > 0) {

            for (int i = 0; i < mainActivityModel.getAllProducts().size(); i++) {
                Jedzenie produkt = (Jedzenie)mainActivityModel.getAllProducts().get(i);
                mainActivityModel.addProductsName(produkt.getNazwa() + " Ilosc: " +produkt.getIlosc()+ " Data przydatnosci: "+ produkt.getData_przydatnosci()+" Kcal: "+produkt.getKcal());
            }
        }
        Log.i("rozmiar",mainActivityModel.getProductsName().get(0)+"");
        productsName1=mainActivityModel.getProductsName();
        mainInterface.createListView(productsName1);
    }

    /**
     *  Method that pass clicked item id from listview
     * @param id item clicked
     */
    public void setclickedNumber(int id){
        clicked_number=id;
    }

    /**
     * method to handle button delete click
     */
    public void buttonDelete_item(){
        mainActivityModel.deleteProductsName(clicked_number);
        databaseHandler.deleteProduct(mainActivityModel.getAllProducts().get(clicked_number));
        mainActivityModel.deleteAllProducts(clicked_number);
        mainInterface.updateListView(mainActivityModel.getProductsName());
    }

    /**
     * method that handle button add click
     * @param edtProductName Product name
     * @param edtProductKcal Product kcal
     * @param edtProductAmount Product amount
     * @param edtProductDate Product expiration date
     */
    public void buttonAdd(String edtProductName,String edtProductKcal,String edtProductAmount,String edtProductDate){
        if (edtProductName.matches("") || edtProductKcal.matches("")|| edtProductAmount.matches("")|| edtProductDate.matches("")) {
            return;
        }
        Jedzenie  jedzenie = new Jedzenie();
        jedzenie.setNazwa(edtProductName);
        jedzenie.setKcal(Integer.valueOf(edtProductKcal));
        jedzenie.setIlosc(Integer.valueOf(edtProductAmount));
        jedzenie.setData_przydatnosci(edtProductDate);
        mainActivityModel.addAllProducts(jedzenie);
        databaseHandler.addProduct(jedzenie);
        mainActivityModel.addProductsName(jedzenie.getNazwa() + " Ilosc: " +jedzenie.getIlosc()+ " Data przydatnosci: "+ jedzenie.getData_przydatnosci()+" Kcal: "+jedzenie.getKcal());
    }

    /**
     * interface from Update Dialog to update data in database
     * @param nazwa name
     * @param kcal kcal
     * @param data Product expiration date
     * @param ilosc Product amount
     */
    @Override
    public void apply(String nazwa, String kcal, String data, String ilosc) {
        int newkcal=Integer.parseInt(kcal);
        int newilosc = Integer.parseInt(ilosc);
        String newNazwa=nazwa;
        String newData=data;
        Jedzenie jedzenie = (Jedzenie)mainActivityModel.getAllProducts().get(clicked_number);
        jedzenie.setId(clicked_number);
        jedzenie.setData_przydatnosci(newData);
        jedzenie.setKcal(newkcal);
        jedzenie.setIlosc(newilosc);
        jedzenie.setNazwa(newNazwa);
        databaseHandler.updateProduct(jedzenie);
        addItems();
    }

    /**
     * interface for view
     */
    public interface MainInterface{
        void createListView(ArrayList<String> productsName);
        void updateListView(ArrayList<String> productsName);
}
}
