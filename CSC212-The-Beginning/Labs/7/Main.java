/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab07;

/**
 *
 * @author Jeffrey R
 */
import java.util.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlayList pl = new PlayList(5);
        Scanner sc = new Scanner(System.in);
        
        //sc.useDelimiter("[,]");
        String input, title, artist;

        
         System.out.println("Enter a to Add, r to Remove,s to Shuffle, d to Display, or q to Quit");
        while (sc.hasNext() && (!sc.hasNext("q"))) {
            input = sc.nextLine();
           
            if (input.equals("a")) {
               // System.out.println("Enter Tittle and Artist seperated by a comma and end the setence with a point");
                System.out.println("Enter Tittle of the song");
               // sc.useDelimiter("[,//.]");
                title = sc.nextLine();
                System.out.println("Enter name of artist");
                artist = sc.nextLine();
                pl.addSong(title, artist);
                sc.reset();
            } 
            else if (input.equals("r")) {
                System.out.println("Enter a tittle to remove or an index");
               if(sc.hasNextInt()){
                   pl.remove(sc.nextInt());
               }
               else{
                   pl.remove(sc.nextLine());
               }
            } 
            else if(input.equals("s")){
                System.out.println("Shuffling the playlist");
                pl.shufflePlayList();
                System.out.println("Shuffle complete");
            }
            else if (input.equals("d")) {
                System.out.println("Enter any letter to display all or an index");
                if(sc.hasNextInt()){
                    pl.display(sc.nextInt());
                }
                else{
                    pl.display();
                    String t = sc.nextLine();
                }
            }
             System.out.println("Enter a to Add, r to Remove, d to Display, or a number to quit, End of loop");
        }
        System.out.println("Program Ended");
        sc.close();
    }
}
