
package models;

import java.util.HashMap;
import java.util.Map;


public class Customer extends User {
    
    //used to display a greeting to the customer using their first and last name from the database
    public String displayGreeting()
    {
        String greeting = "<html>Welcome " + this.getFirstName() + " " + this.getLastName() + "<br>"
                + "Enjoy shopping!</html>";
        return greeting;
    }
    //attributes
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String postcode;
    private HashMap<Integer, Order> orders;
    private boolean isRegistered;
    
    //finds the latest order that the customer has placed
    public Order findLatestOrder()
    {
        Order lastOrder = new Order();
        if(orders.isEmpty())
        {
            addOrder(lastOrder);
        }
        else
        {
            lastOrder = orders.entrySet().iterator().next().getValue();
            for(Map.Entry<Integer, Order> orderEntry : orders.entrySet())
            {
                if(orderEntry.getValue().getOrderDateIn().after(lastOrder.getOrderDateIn()))
                {
                    lastOrder = orderEntry.getValue();
                }
            }
            
            if(lastOrder.getStatusIn().equals("Complete"))
            {
                addOrder(lastOrder);
            }
        }
        return lastOrder;
    }
    
    //adds the order to the databse under this username
    public void addOrder(Order o)
    {
        orders.put(o.getOrderIdIn(), o);
        DBManager db = new DBManager();
        int orderId = db.addOrder(this.getUsername(), o);
        
        Order orderCopy = orders.get(o.getOrderIdIn());
        orders.remove(o.getOrderIdIn());
        orders.put(orderId, orderCopy);
        orders.get(orderId).setOrderId(orderId);
        
        
    }
    
    //getters
    public boolean getIsRegistered()
    {
        return isRegistered;
    }
    public String getAddressLine1()
    {
        return addressLine1;
    }
    
    public String getAddressLine2()
    {
        return addressLine2;
    }
    public String getTown()
    {
        return town;
    }
    public String getPostcode()
    {
        return postcode;
    }
    
    public HashMap<Integer, Order> getOrders()
    {
        return orders;
    }
    
    //setter
    public void setIsRegistered(boolean registered)
    {
        isRegistered = registered;
    }
    
   public void setOrders(HashMap<Integer, Order> ordersIn)
    {
        orders = ordersIn;
    }
   
    public void setAddressLine1(String addressLine1In)
    {
        addressLine1 = addressLine1In;
    }
    
    public void setAddressLine2 (String addressLine2In)
    {
        addressLine2 = addressLine2In;
    }
    
    public void setTown (String townIn)
    {
        town = townIn;
    }
    
    public void setPostcode(String postcodeIn)
    {
        postcode = postcodeIn;
    }
    
    //constructors
    public Customer()
    {
        super();
        addressLine1 = "";
        addressLine2 = "";
        town = "";
        postcode = "";
        orders = new HashMap<>();
        isRegistered = true;
            
    }
    //overloaded constructor
    public Customer(String usernameIn, String passwordIn, String firstNameIn, String lastNameIn, String addressLine1In, String addressLine2In, String townIn, String postcodeIn)
    {
        super(usernameIn, passwordIn, firstNameIn, lastNameIn);
        addressLine1 = addressLine1In;
        addressLine2 = addressLine2In;
        town = townIn;
        postcode = postcodeIn;
        orders = new HashMap<>();
        isRegistered = true;
    }
}
