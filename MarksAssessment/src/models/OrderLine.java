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
public class OrderLine {
    
    //attributes
    private int orderLineId;
    private int quantity;
    private double lineTotal;
    private Product product;

    //getters
    public Product getProduct()
    {
        return product;
    }
    
    public int getOrderLineId()
    {
        return orderLineId;
    }
    
    public int getQuantity()
    {
        return quantity;
    }
    
    public double getLineTotal()
    {
        return lineTotal;
    }
    
    //setters
    
    public void setOrderLineId(int orderLineIn)
    {
        orderLineId = orderLineIn;
    }
    
    public void setQuantity (int quantityIn)
    {
        quantity = quantityIn;
    }
    
    public void setLineTotal (int lineTotalIn)
    {
        lineTotal = lineTotalIn;
    }
    public void setProduct(Product productIn)
    {
        product = productIn;
    }
    //constructor
    
    public OrderLine(int OlId, Product productIn, int quantityIn, double total)
    {
        orderLineId = OlId;
        product = productIn;
        quantity = quantityIn;
        lineTotal = total;
    }
    
    public OrderLine(Order o, Product productIn, int quantityIn)
    {
        orderLineId = o.generateUniqueOrderLineId();
        quantity = quantityIn;
        product = productIn;
        lineTotal = product.getPrice() * quantity;
                    
    }
    
    
    
    //overloaded constructor
    public OrderLine(int orderLineIn, int quanitityIn, int lineTotalIn)
    {
        orderLineId = orderLineIn;
        quantity = quanitityIn;
        lineTotal = lineTotalIn;
    }


}
