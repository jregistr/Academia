/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw5;

import java.util.Random;

/**
 *
 * @author Jeffrey R
 */
public class Passenger {
    private String name;
    private double fare;
    
    public Passenger(String n){
        name = n;
        generateFare ();
    }
    
    private void generateFare (){
       // double 
        Random rng = new Random();
        float randFloat = 100 + rng.nextFloat() * 400;
        fare = randFloat;
    }
    
    public String getName(){
        return name;
    }
    
    public double getFare(){
        return fare;
    }
    
    @Override
    public String toString(){
        return name + " " + fare;
    }
}
