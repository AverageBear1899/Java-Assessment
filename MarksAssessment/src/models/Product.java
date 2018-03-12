/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author 30251094
 */
public class Product {
 
    //attributes
    
    private int productId;
    private String productName;
    private double price;
    private int stockLevel;
    
    
    //getters
    public String toString(){
        return productName;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public int getStockLevel()
    {
        return stockLevel;
    }
    
    //setters
    
    public void setProductId(int productIdIn)
    {
        productId = productIdIn;
    }
    
    public void setProductName(String productNameIn)
    {
        productName = productNameIn;
    }
    
    public void setPrice(double priceIn)
    {
        price = priceIn;
    }
    
    public void setStockLevel(int stockLevelIn)
    {
        stockLevel = stockLevelIn;
    }
    
    //constructor
    public Product()
    {
        productId = 0;
        productName = " ";
        price = 0;
        stockLevel = 0;
                
    }
    
    //overloaded constructor
    
    public Product(int productIdIn, String productNameIn, double priceIn, int stockLevelIn)
    {
        productId = productIdIn;
        productName = productNameIn;
        price = priceIn;
        stockLevel = stockLevelIn;
        
    }
    
}

