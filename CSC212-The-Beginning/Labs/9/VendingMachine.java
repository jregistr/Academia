/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab09;

import java.util.Random;

/**
 *
 * @author Jeffrey R
 */
public class VendingMachine {
    public Candy[][] slots;
    public int shelves;
    public int slotsPerShelves;
    
    public VendingMachine (int s, int sl){
        shelves = s;
        slotsPerShelves = sl;
        slots = new Candy[shelves][slotsPerShelves];
    }
    
    public void load(String[] names){
        Random rng = new Random();
        for(int i = 0; i < slots.length; i ++){
            for(int j = 0; j < slots[0].length; j ++){
                String randName = names[rng.nextInt(names.length)];
                int randOunces = 1 + rng.nextInt(4);
                float randPrice = (rng.nextInt(1000)) * rng.nextFloat();
                Candy temp = new Candy(randName, randPrice, randOunces);
                slots[i][j] = temp;
                //System.out.printf(randName, names)
                //System.out.println("I:" + i + " J:" + j + " Candy" + temp);
            }
        }
    }
    
    public void display() {
        System.out.print(" ");
        for (int j = 0; j < slots[0].length; j++) {
            System.out.printf("|%25d|", j);
        }
        System.out.println();
        for (int i = 0; i < slots.length; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < slots[0].length; j++) {
                if (slots[i][j] == null) {
                    // slot is empty 
                    System.out.printf("|%25s|", "No Candy");
                } else {
                    System.out.printf("|%25s|", slots[i][j].getName());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void purchase(int r, int s) {
        // Attempt to purchase the item in row r, slot s
        if ((!(r >= slots.length) && !(s >= slots[0].length)) && (slots[r][s] != null)) {
            System.out.println("You purchased: " + slots[r][s]);
            slots[r][s] = null;
           // display();
            // Add the missing statement
        } else {
            System.out.println("Invalid selection");
        }
    }
   
}
