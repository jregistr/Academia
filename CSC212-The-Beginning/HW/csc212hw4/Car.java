/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw4;

/**
 *
 * @author Jeffrey R
 */
public class Car {
    public String model;
    public String make;
    public double mpg;
    public int weight;
    public int year;
    
    public Car (String mod, String mak, double mp, int wei, int y){
       model = mod;
       make = mak;
       mpg = mp;
       weight = wei;
       year = y;
    }
    
    @Override
    public String toString (){
        String temp = "Model:" + model + " Make:" + make + " mpg:" + mpg + " weight:" + weight + " year:" + year;
        return temp;
    }
}
