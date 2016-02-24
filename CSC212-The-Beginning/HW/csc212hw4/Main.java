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

public class Main {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        CarDatabase cb;
        System.out.println("Welcome to the Car Database");
        System.out.println("Enter the size of the array:");
        if (sc.hasNextInt()) {
            cb = new CarDatabase(Integer.parseInt(sc.nextLine()));
        } else {
            System.out.println("You did not enter an int, ENDING PROGRAM");
            return;
        }

        System.out.println("Enter the name of the input file:");
        //if (sc.hasNext()) {
            //String inputFname = sc.nextLine();
            //File tempFile = new File(inputFname);
            //if (tempFile.exists()) {
                cb.readFile(sc.nextLine());
           // } else {
            //    System.out.println("File doesn't Exist, ENDING PROGRAM");
             //   return;
            //}
       // }
        System.out.println("Enter make, mpg, weight, all, or quit:");

        while (sc.hasNext() && !sc.hasNext("quit")) {
            String commandInput = sc.nextLine();
            //switch (commandInput) {
                
                if(commandInput.equalsIgnoreCase("make")) {
                    //System.out.println("Make " + temp);
                    System.out.println("Enter the make:");
                    String temp = sc.nextLine();
                  //  System.out.println("Make entered " + temp);
                    cb.displayMake(temp);
                   // break;
                }
                   
                else if(commandInput.equalsIgnoreCase("mpg")){
                    //System.out.println("mpg " + temp);
                    System.out.println("Enter the mpg range:");
                    String mpgInput = sc.nextLine();
                    Scanner stringScan = new Scanner(mpgInput);
                    double min, max;
                    if (stringScan.hasNextDouble()) {
                        min = stringScan.nextDouble();
                    } else {
                        System.out.println("You didn't enter a number");
                        System.out.println("Remember to enter a number followed by a space and then a number");
                        break;
                    }
                    if (stringScan.hasNextDouble()) {
                        max = stringScan.nextDouble();
                    } else {
                        System.out.println("You didn't enter a number");
                        System.out.println("Remember to enter a number followed by a space and then a number");
                        break;
                    }
                    cb.mpgRange(min, max);
                    
                    //break;
                }
                    
                else if(commandInput.equalsIgnoreCase("weight")) {
                    //System.out.println("mpg " + temp);
                    System.out.println("Enter the weight range:");
                    String weightInput = sc.nextLine();
                    Scanner stringScan = new Scanner(weightInput);
                    int min = 0, max = 0;
                    if (stringScan.hasNextInt()) {
                        min = stringScan.nextInt();
                    } else {
                        System.out.println("You didn't enter a number");
                        System.out.println("Remember to enter a number followed by a space and then a number");
                        //break;
                    }
                    if (stringScan.hasNextInt()) {
                        max = stringScan.nextInt();
                    } else {
                        System.out.println("You didn't enter a number");
                        System.out.println("Remember to enter a number followed by a space and then a number");
                        //break;
                    }
                    cb.weightRange(min, max);
                    
                    //break;
                }

                    
                else if(commandInput.equalsIgnoreCase("all")) {
                    //System.out.println("all " + temp);
                    cb.displayAll();
                   // break;
                }
                    
                else {
                    System.out.println("Unknown command -- please try again.");
                  //  break;
                }
                System.out.println("Enter make, mpg, weight, all, or quit:");
            }
            System.out.println("Closing Car Database");
        }
       // 
    }


