/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw6;

import java.util.Scanner;

/**
 *
 * @author Jeffrey R
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Scanner sc = new Scanner (System.in);
        Players ps = new Players  ();
        Awards aw = new Awards();
        ps.load("players.csv");
        aw.load("awards.csv");
        System.out.println("Welcome to the Baseball Database");
        System.out.println("Enter id, name, awards, or quit:");
        
        while (!sc.hasNext("quit")){
            String inp = sc.nextLine();
            inp = inp.toLowerCase();
            
            switch (inp){
                case "id":{
                    System.out.println("Enter the id:");
                    String id = sc.nextLine();
                    String name = ps.getPlayerByID(id);
                    if(name != null){
                        String message = String.format("Player: %s", name);
                        System.out.println(message);
                    }else{
                        System.out.println("ID not found");
                    }
                    break;
                }
                case "name":{
                    System.out.println("Enter player's last name:");
                    String ln = sc.nextLine();
                    ps.printIdsByName(ln);
                    break;
                }
                case "awards":{
                    System.out.println("Enter the id:");
                    String id = sc.nextLine();
                    aw.printAwards(id);
                    break;
                }
                default :{
                    System.out.println("I don't understand your command.");
                    break;
                }
            }
            System.out.println("Enter id, name, awards, or quit:");
        }
        System.out.println("Closing Baseball Database");
    }
}
