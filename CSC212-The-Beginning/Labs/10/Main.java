/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab10;

/**
 *
 * @author Jeffrey R
 */
import java.util.Scanner;
import java.util.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        StringStack ss = new StringStack(4);
        Scanner scan = new Scanner(System.in);

        // System.out.println("Enter push, pop, peek, combine, or quit:");

        System.out.println("Enter push, pop, peek, combine, or quit:");
        
        String[] tester = new String[]{"Jeff", "Hi", "Word", "Room"};
        boolean test = true;
        for(String x : tester){
            System.out.println("X: " + x);
        }
        do{
            System.out.println("Do while");
           // test = true;
        }while (test == false);
        
        
        String command = scan.next();
        while (!command.equals("quit")) {
            switch (command) {
              
                case "push":
                    {
                        String word = scan.next();
                        ss.push(word);
                        //scan.nextLine();
                       System.out.println("I pushed: " + word);
                        break;
                    }
                case "peek":
                    {
                        String word = ss.peek();
                        if (word != null) {
                            System.out.println("Top: " + word);
                        } else {
                            System.out.println("Stack is empty");
                        }
                        break;
                    }
                case "pop":{
                    String word = ss.pop();
                        if (word != null) {
                            System.out.println("Popped Top: " + word);
                        } else {
                            System.out.println("Stack is empty");
                        }
                    break;
                }
                case "combine":{
                    String word1 = ss.pop();
                    String word2 = ss.pop();
                    if(word1 != null && word2 != null){
                    String combined = String.format("%s %s", word2, word1);
                    ss.push(combined);
                    }else{
                        System.out.println("Stack doesn't have at least two elements");
                    }
                    break;
                }
                default: {
                    System.out.println("You entered an unknown command");
                }
            }
            // Get next command
            System.out.println("Enter push, pop, peek, combine, or quit:");
            command = scan.next();
        }
        System.out.println("Program Ending");
    }
}
