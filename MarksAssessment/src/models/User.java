/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


public class User {
   
    
    //Attributes
    private String username;
    private String password;
    private String firstName;
    private String lastname;
    
    
    //getters
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastname;
    }
    
    //setters
    public void setUsername (String usernameIn)
    {
        username = usernameIn;
    }
    
    public void setPassword (String passwordIn)
    {
        password = passwordIn;
    }
    
    public void setFirstName(String firstNameIn)
    {
        firstName = firstNameIn;
    }
    
    public void setLastName(String lastNameIn)
    {
        lastname = lastNameIn;
    }
    
    
    //constructor
    public User()
            {
                username = " ";
                password = " ";
                firstName = " ";
                lastname = " ";
            }
    
    //Overloaded Consturctor
    public User(String usernameIn, String passwordIn, String firstNameIn, String lastNameIn)
    {
        username = usernameIn;
        password = passwordIn;
        firstName = firstNameIn;
        lastname = lastNameIn;
    }
}
