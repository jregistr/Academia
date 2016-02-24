/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab09;

import java.text.DecimalFormat;

/**
 *
 * @author Jeffrey R
 */
public class Candy {
    private String name;
    private float price;
    private int ounces;
    private DecimalFormat decimalFormat = new DecimalFormat ("0.00");
    
    public Candy(String n, float p, int o){
        name = n;
        price = p;
        ounces = o;
    }
    
    
    @Override
    public String toString(){
        return "Name: " + name + "\nFor The cheap Price: " + decimalFormat.format(price) + "\nOunces: " + ounces;
    }
    
    public String getName(){
        return name;
    }
}
