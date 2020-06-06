package com.example.Marcel.oop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity model
 */
public class MainActivityModel {
    List<Produkt> allProducts;
    ArrayList<String> productsName;

    /**
     * Default constructor
     */
    public MainActivityModel() {
        productsName=new ArrayList<>();
    }

    /**
     * product display names for listview
     * @return arraylist with product display names
     */
    public ArrayList<String> getProductsName() {
        return productsName;
    }

    /**
     * Add item to ProductsName array
     * @param value
     */
    public void addProductsName(String value){
        this.productsName.add(value);
    }

    /**
     * Setter for ProductsName
     * @param productsName new arraylist
     */
    public void setProductsName(ArrayList<String> productsName) {
        this.productsName = productsName;
    }

    /**
     * Method to delete item from productsname array
     * @param id id to delete
     */
    public void deleteProductsName(int id){
        this.productsName.remove(id);
    }

    /**
     * Getter
     * @return List of produkt
     */
    public List<Produkt> getAllProducts() {
        return allProducts;
    }

    /**
     * Setter
     * @param allProducts new list
     */
    public void setAllProducts(List<Produkt> allProducts) {
        this.allProducts = allProducts;
    }

    /**
     * Add item
     * @param value value to add
     */
    public void addAllProducts(Jedzenie value){
        this.allProducts.add(value);
    }

    /**
     * remove item from list
     * @param id id to remove
     */
    public void deleteAllProducts(int id){
        this.allProducts.remove(id);
    }
}
