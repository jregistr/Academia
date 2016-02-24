/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Jeffrey R
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        File candyFile = new File("Candy.txt");
        Scanner fileScan = new Scanner(candyFile);
        String[] candyNames;
        int numOfLines = (fileScan.hasNextInt()) ? fileScan.nextInt() : 10;
        fileScan.nextLine();
        candyNames = new String[numOfLines];
        for (int i = 0; i < candyNames.length; i++) {
            String curLine = fileScan.nextLine();
            candyNames[i] = curLine;
            //System.out.println("Added " + candyNames[i]);
        }

        Scanner keyboard = new Scanner(System.in);

        System.out.println("How many shelves in the vending machine?");
        int shelves = keyboard.nextInt();
        keyboard.nextLine();

        System.out.println("How many slots on each shelf?");
        int slotsPerShelf = keyboard.nextInt();
        keyboard.nextLine();
        VendingMachine vm = new VendingMachine(shelves, slotsPerShelf);
        vm.load(candyNames);
        vm.display();
        
        //while (!keyboard.hasNext("quit")) {
            System.out.println("What shelf do you want?");
            int shelfSelection = keyboard.nextInt();
            keyboard.nextLine();
            System.out.println("What slot do you want?");
            int slotSelection = keyboard.nextInt();
            keyboard.nextLine();
            vm.purchase(shelfSelection, slotSelection);
            vm.display();
       // }
    }
}
