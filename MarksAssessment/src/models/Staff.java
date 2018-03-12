
package models;


public class Staff extends User {
    
    //displays a greeting to the staff member using the first name and last name
    public String displayStaffGreeting()
    
    {
        String greeting = "<html>Welcome " + this.getFirstName() + " " + this.getLastName() + "<br>"
                + "You are logged in as staff</html>";
        return greeting;
    }
    private double salary;
    private String position;
    
    
    //getter
    public double getSalary()
    {
        return salary;
    }
    
    public String getPosition()
    {
        return position;
    }
    
    //setter
    public void setSalary(double salaryIn)
    {
        salary = salaryIn;
        
    }
    
    public void setPosition(String positionIn)
    {
        position = positionIn;
    }
    
    //constructor
    public Staff()
    {
        super();
        salary = 00;
        position = "";
    }
    
    //overloaded constructor
    public Staff (String usernameIn, String passwordIn, String firstNameIn, String lastNameIn, double salaryIn, String positionIn)
    {
        super(usernameIn, passwordIn, firstNameIn, lastNameIn);
        salary = salaryIn;
        position = positionIn;
    }
    
}
