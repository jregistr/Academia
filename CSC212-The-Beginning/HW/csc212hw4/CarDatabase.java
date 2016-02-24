/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw4;

/**
 *
 * @author Jeffrey R
 */
import java.io.File;
import java.util.*;
public class CarDatabase {
    private Car[] cars;
    private int filled = 1;
    
    /** Takes one integer which it uses to set the size of the cars array */
    public CarDatabase (int size){
        cars = new Car[size];
    }
    
     /** This methods returns true if the cars array is full */
    public boolean isFull(){
        boolean full = (filled >= cars.length) ? true : false;
        return full;
    }
    
    /** This methods reads the defined file and creates car objects from it */
    public void readFile(String fName) throws Exception{
        //System.out.println("I'm reading");
        File file = new File (fName);
        Scanner fileScan = new Scanner (file);
        //String line = fileScan.nextLine();
        //System.out.println("Line read " + line);
        
        for (int i = 0; i < cars.length && fileScan.hasNextLine(); i++) {
            String line = fileScan.nextLine();
                Scanner stringScan = new Scanner(line);
                stringScan.useDelimiter(",");
                String mod, mak;
                int wei, ye;
                double mp;
                mod = stringScan.next();
                mak = stringScan.next();
                mp = stringScan.nextDouble();
                wei = stringScan.nextInt();
                ye = stringScan.nextInt();
                //System.out.println("Mod " + mod + " mak " + mak + " mp " + mp + " wei " + wei + " year " + ye);
                
                Car tempCar = new Car(mod,mak, mp, wei,ye);
                cars[i] = tempCar;
                filled ++;
                //System.out.println(cars[i]);
                
            
        }
    }
    
    /** This methods takes a string "make" and displays all cars with this make */
    public void displayMake(String m){
        for(int i = 0; i < cars.length; i ++){
            if(cars[i] != null && cars[i].make.equals(m)){
                System.out.println(cars[i]);
            }
        }
    }
    
    /** This methods takes 2 doubles and prints all cars with MPG in the range */
    public void mpgRange (double min, double max){
        //System.out.println("Min: " + min + " Max: " + max);
        double min1,max1;
        if(min > max){
            min1 = max;
            max1 = min;
        }else{
            min1 = min; max1 = max;
        }
        for(int i =0; i < cars.length; i ++){
            if(cars[i] != null){
                Car tempCar = cars[i];
                if(tempCar.mpg >= min1 && tempCar.mpg <= max1){
                    System.out.println(tempCar);
                }
            }
        }
    }
    
    /** This methods takes 2 doubles and prints all cars with weight in the range */
    public void weightRange (double min, double max){
         
        for(int i =0; i < cars.length; i ++){
            if( cars[i] != null && (cars[i].weight >= min && cars[i].weight <= max)){
                    System.out.println(cars[i]);
            }
        }
    }
    
    /** This methods prints all cars in  the database */
    public void displayAll (){
        for(int i =0; i < cars.length; i ++){
            if(cars[i] != null){
                System.out.println(cars[i]);
            }
        }
    }
}
