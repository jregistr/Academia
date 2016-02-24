/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab12;

/**
 *
 * @author Jeffrey R
 */
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner (System.in);
        StringList sL = new StringList("String_1");
        System.out.println("Enter a command (p = prepend, a = append, c = contains, d = display, or quit)");
        while(!scan.hasNext("q".toLowerCase())){
            String input = scan.next();
            switch(input){
                case "p":{
                    sL = sL.prepend(scan.next());
                    break;
                }
                case "a":{
                    sL.append(scan.next());
                    break;
                }
                case "c":{
                    if(sL.contains(scan.next())){
                        System.out.println("It is there");
                    }else{
                        System.out.println("String not found");
                    }
                    break;
                }
                case "d":{
                    System.out.print(String.format("[ %s ]",sL.toString()));
                    System.out.println("");
                    break;
                }
                default :{
                    System.out.println("Unknown");
                    break;
                }
            }
            System.out.println("Enter a command (p = prepend, a = append, c = contains, d = display, or quit)");
        }
    }
}
