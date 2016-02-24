/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab11;

/**
 *
 * @author Jeffrey R
 */
public class Player {
    private String firstName, lastName;
    
    public Player (String fn, String ln){
        firstName = fn;
        lastName = ln;
    }
    
    public String getLastName (){
        return lastName;
    }
    
    @Override
    public String toString (){
        String concat = String.format ("[%s, %s]",lastName, firstName);
        return concat;
    }
}
