package com.example.Marcel.oop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * class which handle all SqlLite operations
 */
public class MySqliteHandler extends SQLiteOpenHelper {




    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "product.db";

    // Product Table Name
    private static final String TABLE_PRODUCT = "product";

    // Product Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "productName";
    private static final String COLUMN_PRODUCT_KCAL = "productKcal";
    private static final String COLUMN_PRODUCT_DATE = "productDATE";
    private static final String COLUMN_PRODUCT_AMOUNT = "productCOUNT";


    String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_PRODUCT_NAME + " TEXT, "+ COLUMN_PRODUCT_KCAL + " INTEGER, " +
            COLUMN_PRODUCT_DATE + " TEXT, "+COLUMN_PRODUCT_AMOUNT+" INTEGER" + ")";

    /**
     * constructor
     * @param context current context
     */
    public MySqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PRODUCT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);

        onCreate(db);

    }


    // All Database Operations: create, read, update, delete

    // create

    /**
     * Create new item in database
     * @param produkt custom object to put in database
     */
    public void addProduct(Produkt produkt) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, produkt.getNazwa());
        values.put(COLUMN_PRODUCT_KCAL, produkt.getKcal());
        values.put(COLUMN_PRODUCT_DATE,produkt.getData_przydatnosci());
        values.put(COLUMN_PRODUCT_AMOUNT, ((Jedzenie) produkt).getIlosc());

        database.insert(TABLE_PRODUCT, null, values);


        database.close();

    }




    // Getting a single product - read

    /**
     * Getting a single product - read
     * @param id item id
     * @return jedzenie
     */
    public Produkt getProduct(int id) {

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_PRODUCT, new String[]{COLUMN_ID,
                        COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_KCAL, COLUMN_PRODUCT_DATE, COLUMN_PRODUCT_AMOUNT},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);


        if (cursor != null)  {
            cursor.moveToFirst();
        }

        Jedzenie jedzenie = new Jedzenie(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),cursor.getString(3),cursor.getInt(4));
        return jedzenie;


    }





    /**
     * Getting all Products Objects
     * @return array of all produkt
     */
    public List<Produkt> getAllProducts() {

        List<Produkt> produktList = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);


        if (cursor.moveToFirst()) {

            do {

                Jedzenie produkt = new Jedzenie();
                produkt.setId(Integer.parseInt(cursor.getString(0)));
                produkt.setNazwa(cursor.getString(1));
                produkt.setKcal((cursor.getInt(2)));
                produkt.setData_przydatnosci(cursor.getString(3));
                ((Jedzenie) produkt).setIlosc(cursor.getInt(4));


                produktList.add(produkt);

            } while (cursor.moveToNext());


        }

        return produktList;


    }





    // Updating a single product

    /**
     * Updating a single product
     * @param produkt object with data to update
     * @return int with information about success
     */

    public int updateProduct(Produkt produkt) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, produkt.getNazwa());
        values.put(COLUMN_PRODUCT_KCAL, produkt.getKcal());
        values.put(COLUMN_PRODUCT_DATE,produkt.getData_przydatnosci());
        values.put(COLUMN_PRODUCT_AMOUNT, ((Jedzenie) produkt).getIlosc());
        Log.i("ilosc", String.valueOf(((Jedzenie) produkt).getIlosc()));
        return database.update(TABLE_PRODUCT, values, COLUMN_ID + " = ? ",
                new String[] {String.valueOf(produkt.getId())});


    }





    /**
     * Delete a single product
     * @param produkt
     */
    public void deleteProduct(Produkt produkt) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_PRODUCT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(produkt.getId())});
        database.close();


    }

    /**
     * Get number of items
     * @return
     */
    public int getProductsCount() {
        String productCountQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(productCountQuery, null);
        cursor.close();

        return cursor.getCount();
    }










}
