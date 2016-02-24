/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw3;

/**
 *
 * @author Jeffrey R
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Calculator calc = new Calculator();
        double big1 = calc.max(12, 53);
        double big2 = calc.max(12.3, 2.3, 200);
        double min1 = calc.min(90, 5);
        double min2 = calc.min(90.1, 100, 32);
        double avr1 = calc.avg(43.3, 12.3, 56);
        double med = calc.median(68, 157, 56674);
        
        System.out.println("big 1: " + big1 + " big2: " + big2);
        System.out.println("min 1: " + min1 + " min2: " + min2);
        System.out.println("Average: " + avr1);
        System.out.println("Median: " + med);
    }
}
