package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DBManager {
    //allows us to load the orders for a customer
    public HashMap<String, Customer> loadOrders(HashMap<String, Customer> customersWithoutOrders)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = 
                    DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders");           

            while(rs.next())
            {
                int orderId = rs.getInt("OrderId");
                Date orderDate = rs.getDate("OrderDate");
                String username = rs.getString("Username");
                double orderTotal = rs.getDouble("OrderTotal");
                String status = rs.getString("Status");
                
                Order loadedOrder = new Order(orderId, orderDate, orderTotal, status);
                if(customersWithoutOrders.containsKey(username))
                {
                    Customer customersWithOrder = customersWithoutOrders.get(username);
                    customersWithOrder.getOrders().put(orderId, loadedOrder); 
                }
            }
            conn.close();
        }
        catch(Exception ex)
        {
           String message = ex.getMessage();
        }
        finally
        {
           return customersWithoutOrders; 
        }
    }
    //allows us to load the orderlines for a customer
    public HashMap<String, Customer> loadOrderLines(HashMap<String, Customer> customersWithoutOrderLines)
        {
       try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = 
                    DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OrderLines");           

            while(rs.next())
            {
                int orderLineId = rs.getInt("OrderLineId");
                int productId = rs.getInt("ProductId");
                int quantity = rs.getInt("Quantity");
                double lineTotal = rs.getDouble("LineTotal");
                int orderId = rs.getInt("OrderId");

                for(Map.Entry<String, Customer> customerEntry : customersWithoutOrderLines.entrySet())
                {
                    Customer customer = customerEntry.getValue();
                    if(customer.getOrders().containsKey(orderId))
                    {
                        Order order = customer.getOrders().get(orderId);
                        
                        HashMap<Integer, Product> loadProduct = loadProducts();
                        Product orderedProduct = loadProduct.get(productId);
                        
                        OrderLine loadedOrderLine = new OrderLine(orderLineId, orderedProduct, quantity, lineTotal);
                        
                        order.getOrderLines().put(orderLineId, loadedOrderLine);         
                    }
                }          
            }
            conn.close();
        }
        catch(Exception ex)
        {
           String message = ex.getMessage();
        }
        finally
        {
           return customersWithoutOrderLines; 
        } 
    }
    
    //updates the product availability after a product has been purchased
    public void updateProductAvailability(Product product)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("UPDATE Products SET StockLevel = " + product.getStockLevel() +
                             " WHERE ProductId= '" + product.getProductId() + "'");
            conn.close();
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
        }
    }
    
    //sets the status of the order to complete
    public void completeOrder(int orderId)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("UPDATE Orders SET Status= 'Complete', OrderDate= '" +
                             new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                             "' WHERE OrderId= '" + orderId + "'");
            conn.close();
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
        }
    }
    
    //allows us to delete an orderline for a customer
    public void deleteOrderLine(int orderId, int productId)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("DELETE FROM OrderLines WHERE ProductId = '" + 
                    productId + "' AND OrderId = '" + orderId + "'");
            conn.close();
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
        }
    }
    //allows us to add an orderline for a customer
    public void addOrderLine(OrderLine oLine, int orderId)
    {
        try {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("INSERT INTO OrderLines (OrderLineId, ProductId, Quantity, LineTotal, OrderId) " +
            "VALUES ('" + oLine.getOrderLineId() + "','" + oLine.getProduct().getProductId() + "','" + oLine.getQuantity() + "','" + oLine.getLineTotal() + "'," + orderId + ")");
            
            conn.close();
            updateOrderTotal(orderId, oLine.getLineTotal());
        } catch(Exception ex) {
            String message = ex.getMessage();
        }
    }
    
    //updates the order total using which is set using the linetotal
    public void updateOrderTotal(int orderId, double lineTotal)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("UPDATE Orders SET OrderTotal = OrderTotal + " + lineTotal + " WHERE OrderId = '" + orderId + "'");
            
            conn.close();
        } catch(Exception ex) {
            String message = ex.getMessage();
        }
    }
        
    //allows us to add an order to the database for a customer
    public int addOrder(String username, Order o)
    {
        int orderId = 0;    
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
           
            stmt.executeUpdate("INSERT INTO Orders (OrderDate, Username, OrderTotal, " +
                    "Status) VALUES ('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o.getOrderDateIn()) +
                    "','" + username + "','" + o.getOrderTotal()+ "','" + o.getStatusIn() + "')");
           
            ResultSet rs = stmt.getGeneratedKeys();
           
            if(rs.next())
            {orderId = rs.getInt(1);}        
            conn.close();
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
        }  
        return orderId;
    }
    
    //creates a hashmap of all of our customers registered with the database
    public HashMap<String, Customer> Customers()
    {
        HashMap<String, Customer> customers = new HashMap<>();
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = 
                    DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers");

            
            while(rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String addressLine1 = rs.getString("AddressLine1");
                String addressLine2 = rs.getString("AddressLine2");
                String town = rs.getString("Town");
                String postCode = rs.getString("PostCode");


                Customer customer = new Customer(username, password, firstName, lastName, addressLine1, addressLine2, town, postCode);

                customers.put(username, customer);
            }
            conn.close();
        }  

        catch(Exception ex) {
           String message = ex.getMessage();
        }
        finally {  
           customers = loadOrders(customers);
           customers = loadOrderLines(customers);
           return customers; 
        }
    }
    
    //creates a hashmap of all of our staff registered with the database
    public HashMap<String, Staff> Staffs()
    {
        HashMap<String, Staff> staffs = new HashMap<>();
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = 
                    DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Staff");
            
            if(!rs.next())
            {
                conn.close();
                return null;
            }
            else
            {
                while(rs.next())
                {
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String firstName = rs.getString("FirstName");
                    String lastname = rs.getString("LastName");
                    String position = rs.getString("Position");
                    double salary = rs.getDouble("Salary");
                    
                
                    Staff staff = new Staff(username, password, firstName, lastname, salary, position);
                
                    staffs.put(username, staff);
                }
                
            }
            
        }
        catch(Exception ex)
        {
           String message = ex.getMessage();
        }
        finally
        {
           return staffs; 
        }
    }
    
    //allows us to register and store the details of a new customer
    public boolean registerCustomer(Customer newCustomer)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers WHERE Username = '" + newCustomer.getUsername()+ "'");
            if(rs.next())
            {
                conn.close();
                return false;
            }
            else
            {
                stmt.executeUpdate("INSERT INTO Customers (username,  password,  firstName,  lastName, "
                        + "          addressLine1,  addressLine2,  town,  postcode) " +
                    "VALUES ('" + newCustomer.getUsername() + "','" + newCustomer.getPassword() + "','" + newCustomer.getFirstName() + "','" +
                            newCustomer.getLastName()+ "','" + newCustomer.getAddressLine1()+ "','" + newCustomer.getAddressLine2()+ "','" + 
                            newCustomer.getTown()+ "','" + newCustomer.getPostcode() + "')");
                conn.close();
                return true;
            }
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
            return false;
        }
    }
    
    //allows a customer to log in to the system with the provision of a valid username + password
    public Customer logIn(String usern, String pass)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers WHERE Username = '" + usern  + "' AND Password = '" + pass + "'");
            
            if(!rs.next())
            {
                conn.close();
                return null;
            }
            else
            {
                String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String firstName = rs.getString("FirstName");
                    String lastname = rs.getString("LastName");
                    String addressLine1 = rs.getString("AddressLine1");
                    String addressLine2 = rs.getString("AddressLine2");
                    String town = rs.getString("Town");
                    String postcode = rs.getString("Postcode");
                
                conn.close();
                Customer customer = new Customer(username, password, firstName, lastname, addressLine1, addressLine2, town, postcode);
                
                HashMap<String, Customer> customers = new HashMap<String, Customer>();
                customers.put(username, customer);
                
                customers = loadOrders(customers);
                customers = loadOrderLines(customers);
           
                return customer;
            }
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
            return null;
        }
    }
    
     //allows a stafff member to log in to the system with the provision of a valid username + password
    public Staff StafflogIn(String usern, String pass)
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Staff WHERE Username = '" + usern  + "' AND Password = '" + pass + "'");
            
            if(!rs.next())
            {
                conn.close();
                return null;
            }
            else
            {
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String firstName = rs.getString("FirstName");
                    String lastname = rs.getString("LastName");
                    String position = rs.getString("Position");
                    double salary = rs.getDouble("Salary");
                
                conn.close();
                Staff staff = new Staff(username, password, firstName, lastname, salary, position);
                return staff;
            }
        }
        catch(Exception ex)
        {
            String message = ex.getMessage();
            return null;
        }
    }
    
    //allows us to add a new product to the database
    public void addProduct(Product newProduct) {
        String measurement = "";
        String size = "NULL";
        
        if(newProduct.getClass().getName().equals("models.Clothing")) {
            Clothing newClothing = (Clothing)newProduct;
            measurement = newClothing.getMeasurement();
        } else {
            Footwear newFootwear = (Footwear)newProduct;
            size = String.valueOf(newFootwear.getSize());
        }
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("INSERT INTO Products (ProductName, Price, StockLevel, Measurement, Size) " +
            "VALUES ('" + newProduct.getProductName() + "','" + newProduct.getPrice() + "','" + newProduct.getStockLevel() + "','" + measurement + "'," + size + ")");
            
            conn.close();
        } catch(Exception ex) {
            String message = ex.getMessage();
        }
    }
    
    //creates a hashmap that allows us to load all of the products in the database
    public HashMap<Integer, Product> loadProducts() {
        HashMap<Integer, Product> products = new HashMap();
        
       try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
            
            if(!rs.next()) {
                conn.close();
                return null;
            } else {
                while(rs.next()) {
                    int productId = rs.getInt("ProductId");
                    String productName = rs.getString("ProductName");
                    double price = rs.getDouble("Price");
                    int stock = rs.getInt("StockLevel");
                    
                    String measurement = rs.getString("Measurement");
                    
                    if(measurement != null && !measurement.isEmpty()) {
                        Clothing clothing = new Clothing(productId, productName, price, stock, measurement);
                        products.put(productId, clothing);
                    } else {
                        int size = rs.getInt("Size");
                        Footwear footwear = new Footwear(productId, productName, price, stock, size);
                        products.put(productId, footwear);
                    }
                }
                conn.close();
                return products;
       }
    } catch(Exception ex) {
        String message = ex.getMessage();
        return null;
    }
    }
    
    //allows us to update information regarding a product
    public void updateProduct(Product updateProduct) {
        String measurement = "";
        String size = "NULL";
        if(updateProduct.getClass().getName().equals("models.Clothing")) {
            Clothing clothing = (Clothing)updateProduct;
            measurement = clothing.getMeasurement();
        } else {
            Footwear footwear = (Footwear)updateProduct;
            size = String.valueOf(footwear.getSize());
        }
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("UPDATE Products SET ProductName ='" + updateProduct.getProductName() + "', "
            + "Price = '" + updateProduct.getPrice() + "', StockLevel= '" + updateProduct.getStockLevel() + "', "
            + "Measurement= '" + measurement + "', size=" + size + " WHERE ProductId = '" + updateProduct.getProductId() + "'");
        } catch(Exception ex) {
            String message = ex.getMessage();
        }
        
    }
    //allows us to delete a product from the database
        public void deleteProduct(Product product) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("DELETE FROM Products WHERE ProductId ='" + product.getProductId() + "'");
            conn.close();
        } catch (Exception ex) {
            String message = ex.getMessage();
        }
    }
    
        //allows us to update customer details in the database
    public void updateCustomer(Customer customer) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("UPDATE Customers SET Password= '" + customer.getPassword() + "', " +
             "FirstName= '" + customer.getFirstName() + "', LastName= '" + customer.getLastName() + "', " +
             "AddressLine1= '" + customer.getAddressLine1() + "', AddressLine2= '" + customer.getAddressLine2() + "', " +
             "Town= '" + customer.getTown() + "', Postcode= '" + customer.getPostcode() + "' "
             + "WHERE Username= '" + customer.getUsername() + "'");
            
            conn.close();
        } catch(Exception ex) {
            String message = ex.getMessage();
        }
    }
    //allows us to delete a customer from the database
    public void deleteCustomer(Customer customer) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Simon\\Desktop\\MarksAssessment\\db\\ShopDB.accdb");
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("DELETE FROM Customers WHERE Username ='" + customer.getUsername() + "'");
            conn.close();
        } catch (Exception ex) {
            String message = ex.getMessage();
        }
    }
}

