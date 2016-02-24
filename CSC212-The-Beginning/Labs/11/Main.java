/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab11;

/**
 *
 * @author Jeffrey R
 */
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Scanner sc = new Scanner (System.in);
        Players ps = new Players  ();
        ps.load("players.csv");
        
        System.out.println("Enter id, name, or quit");
        
        while (!sc.hasNext("quit")){
            String inp = sc.nextLine();
            inp = inp.toLowerCase();
            
            switch (inp){
                case "id":{
                    System.out.println("Enter id");
                    String id = sc.nextLine();
                    String name = ps.getPlayerByID(id);
                    if(name != null){
                        String message = String.format("|Player: %s|", name);
                        System.out.println(message);
                    }else{
                        System.out.println("ID not found");
                    }
                    break;
                }
                case "name":{
                    System.out.println("Enter the last Name");
                    String ln = sc.nextLine();
                    ps.printIdsByName(ln);
                    break;
                }
                default :{
                    System.out.println("Your command is Unknown");
                    break;
                }
            }
            System.out.println("Enter id, name, or quit");
        }
    }
}
