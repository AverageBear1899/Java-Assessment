/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Order {
    
    
    //Attributes
    
    private int orderId;
    private Date orderDate;
    private double orderTotal;
    private String status;
    private HashMap<Integer, OrderLine> orderLines;
    
    //adds the orderline to the database
    public void addOrderLine(OrderLine oLine)
    {
        orderTotal = orderTotal + oLine.getLineTotal();
        orderLines.put(oLine.getOrderLineId(), oLine);
        DBManager db = new DBManager();
        db.addOrderLine(oLine, orderId);
    }
    
    //generates a unique id for the order line
    public int generateUniqueOrderLineId()
    {
        int orderLineId = 0;
        for(Map.Entry<Integer, OrderLine> orderLineEntry : orderLines.entrySet())
        {
            if(orderLines.containsKey(orderLineId))
            {
                orderLineId++;
            }
        }
        return orderLineId;
    }
    
   
    
    //getters
    
    public int getOrderIdIn()
    {
        return orderId;
    }
    
    public Date getOrderDateIn()
    {
        return orderDate;
    }
    
    public double getOrderTotal()
    {
        return orderTotal;
    }
    
    public String getStatusIn()
    {
        return status;
    }
    public HashMap<Integer, OrderLine> getOrderLines()
    {
        return orderLines;
    }
    
    //setters
    
    public void setOrderId(int orderIdIn)
    {
        orderId = orderIdIn;
    }
    
    public void setOrderDate(Date orderDateIn)
    {
        orderDate = orderDateIn;
    }
    
    public void setOrderTotal(double orderTotalIn)
    {
        orderTotal = orderTotalIn;
    }
    
    public void setStatus (String statusIn)
    {
        status = statusIn;
    }

    public void setOrderLines(HashMap<Integer, OrderLine> oLines)
    { 
        orderLines = oLines;
    }
    
    //constructor
    public Order()
    {
        orderId = 0;
        orderDate = new Date();
        orderTotal = 0;
        status = " ";
        orderLines = new HashMap<>();
                
                
    }
    
    
    
    //overloaded constructor
    public Order(int orderIdIn, Date orderDateIn, double orderTotalIn, String statusIn)
    {
        orderId = orderIdIn;
        orderDate = orderDateIn;
        orderTotal = orderTotalIn;
        status = statusIn;
        orderLines = new HashMap<>();
        
    }

    //removes the orderline from the database
    public void removeOrderLine(int productId) {
            Iterator <Map.Entry<Integer, OrderLine>> iter = orderLines.entrySet().iterator();
                while (iter.hasNext()) {
                     Map.Entry<Integer, OrderLine> entry = iter.next();
                    if(entry.getValue().getProduct().getProductId() == productId) {
                iter.remove();
                orderTotal = orderTotal - entry.getValue().getLineTotal();
                
                DBManager db = new DBManager();
                db.deleteOrderLine(orderId, productId);
                db.updateOrderTotal(orderId, -entry.getValue().getLineTotal());    }
}
    }
}
