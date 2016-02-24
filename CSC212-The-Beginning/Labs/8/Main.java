/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab08;

/**
 *
 * @author Jeffrey R
 */
import java.io.*;
import java.util.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        File candyFile = new File("Candy.txt");
        Scanner fileScan = new Scanner (candyFile);
        String[] candyNames;
        int numOfLines = (fileScan.hasNextInt()) ? fileScan.nextInt() : 10;
        fileScan.nextLine();
        candyNames = new String[numOfLines];
        for(int i = 0; i < candyNames.length; i ++){
            String curLine = fileScan.nextLine();
            candyNames[i] = curLine;
            System.out.println("Added " + candyNames[i]);
        }
        
        Random rng = new Random();
//        for(int i = 0; i < rng.nextInt(numOfLines); i ++){
//            int randIndex = rng.nextInt(numOfLines);
//            System.out.println("Random Candy " + candyNames[randIndex]);
//        }
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("Welcome to candy Land");
        System.out.println("Press Enter for Candy And If you're not in the mood, type quit");
        String input = br.readLine();
        while(!input.equals("quit")){
            System.out.println("Cool, btw I have your credit card number");
            int randIndex = rng.nextInt(numOfLines);
            String randName = candyNames[randIndex];
            int priceMultiplier = rng.nextInt(1000);
            float randPrice = priceMultiplier * rng.nextFloat();
            int randOunces = rng.nextInt(100);
            Candy candy = new Candy(randName, randPrice, randOunces);
            System.out.println(candy);
            input = br.readLine();
        }
    }
}
