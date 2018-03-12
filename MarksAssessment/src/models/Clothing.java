
package models;


public class Clothing extends Product {
    
    //Attributes
    
    private String measurement;
    
    
    //getter
    
    public String getMeasurement()
    {
        return measurement;
    }
    
    //setter 
    public void setMeasurement(String measurementIn)
    {
        measurement = measurementIn;
    }
    
    //constructor
    public Clothing()
            {
                super();
                measurement =" ";
            }
    
    //overloaded constructor
    public Clothing(int productIdIn, String productNameIn, double priceIn, int stockLevelIn, String measurementIn)
    {
       super(productIdIn, productNameIn, priceIn, stockLevelIn);
       measurement = measurementIn;
    }
    
    
}
