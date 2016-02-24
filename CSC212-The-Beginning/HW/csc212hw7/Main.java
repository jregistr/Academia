/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw7;

import java.util.Scanner;

/**
 *
 * @author Jeffrey R
 */
public class Main {
    
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
       Scanner scan = new Scanner (System.in);
        System.out.println("Welcome to Sorter");
        System.out.println("Enter a sequence of numbers, d, r, or quit:");
        IntList iL = null;
        
        while (!scan.hasNext("quit")){
            String input = scan.next();
            if(input.equals("d")){
               // System.out.println("D");
                if(iL == null){
                    System.out.println("List is empty.");
                }else{
                    //System.out.println("Il " + iL.value);
                    System.out.println(iL.toString());
                }
            }else if (input.equals("r")){
                //System.out.println("R");
                if(iL == null){
                    System.out.println("List is empty.");
                }else{
                    System.out.println(iL.toStringReverse());
                }
            }else{
                Scanner nSc = new Scanner (input);
                if(nSc.hasNextInt()){
                    int temp = nSc.nextInt();
                    //iL = iL.insert(temp);
                    if(iL == null){
                        iL = new IntList (temp);
                        
                    }else{
                        iL = iL.insert(temp);
                    }
                }else{
                    System.out.println("Invalid input: " + input);
                }
            }
        }
        System.out.println("Closing Sorter");
    }
    
   
}
