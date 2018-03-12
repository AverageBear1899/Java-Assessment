
package models;

public class Footwear extends Product {
    
    //attributes
    private int size;
    
    //getter
    public int getSize()
            {
                return size;
            }
    
    //setter
    public void setSize(int sizeIn)
    {
        size = sizeIn;
    }
    
    //constructor
    public Footwear()
            {
                super();
                size = 0;
            }
    
    //overloaded constructor
    public Footwear(int productIdIn, String productNameIn, double priceIn, int stockLevelIn, int sizeIn)
    {
        super(productIdIn, productNameIn, priceIn, stockLevelIn);
        size = sizeIn;
    }
            
    
}
