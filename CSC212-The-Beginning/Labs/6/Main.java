/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab06;

/**
 *
 * @author Jeffrey R
 */
import java.io.*;
//import java.util.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
       String line;
       BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
       String userArtist, userTittle;
        System.out.println("Welcome to Song List");
        System.out.println("How many songs are you adding to the list?");
        line = br.readLine();
        int songListSize = (isInt(line)) ? Integer.parseInt(line) : 5;
        System.out.println("Waiting for " + songListSize + " items");
        Song[] songs = new Song[songListSize];
        for(int i = 0; i < songs.length; i ++){
            System.out.println("Enter song " + (i + 1) + "'s tittle");
            userTittle = br.readLine();
            System.out.println("Enter song " + (i + 1) + "'s artist");
            userArtist = br.readLine();
            Song temp = new Song(userTittle, userArtist);
            songs[i] = temp;
        }
        System.out.println("List is Done");
        System.out.println("Would you like to make a request?");
        System.out.println("You can always press Q to quit");
        
        line  = br.readLine();
        boolean quit = shouldQuit (line, songs.length);
        while(!quit){
            int temp = Integer.parseInt(line);
            System.out.println("Song " + temp + ": " + songs[temp]);
            System.out.println("Would you like to make a request?");
            System.out.println("You can always press Q to quit");
            line = br.readLine();
            quit = shouldQuit (line, songs.length);
        }
    }
 
    public static boolean shouldQuit (String s, int i){
        boolean quit = (((s.equals("q") || s.equals("Q")) ) || (!isInt(s))) ? true : false;
        if(quit){
            return quit;
        }
        if(isInt(s)){
            int temp = Integer.parseInt(s);
            if(temp < 0 || temp >= i){
                quit = true;
                System.out.println("The list isn't that big");
            }
        }
        return quit;
    }
    
    public static boolean isInt (String s){
        
        try{
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException e){
            System.out.println("that Isn't a number " + e.getMessage());
            return false;
        }
    }
}
