/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw2;

/**
 *
 * @author Jeffrey R
 */
import java.io.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        //System.out.println("Welcome to the WindChill Calculator");
//        System.out.println("What is your name?");
//        String usrInpN = br.readLine();
//        
//        String reply = (usrInpN.equals("Jeff")) ? "Super Awesome" : usrInpN;
//        System.out.println("Hi " + reply);
        System.out.println("Enter the observation date:");
        
        String usrInpD = br.readLine(); //what the user typed in after request for date
        
        //System.out.println(usrInpN + " Would you be so kind to tell me the Wind Speed");
        System.out.println("Enter the wind speed in MPH:");
        
        String usrInpWs = br.readLine(); // Windspeed in string form
        
        //System.out.println("Alright " + usrInpN + " Now tell me the temperature in degress F");
        System.out.println("Enter the temperature in degrees F:");
        
        String usrInpT = br.readLine(); //temperature in string form
        
        //System.out.println("Gotcha, Now let me do my thing!!");
        
        int usrTempIntForm = Integer.parseInt(usrInpT);
        int usrWsIntForm  = Integer.parseInt(usrInpWs);
        
        Observation observ = new Observation (usrInpD,usrWsIntForm,usrTempIntForm);
        
//        String message = "Wind Chill for date: " + observ.getDate() + " Is " + observ.getWindChill();
//        System.out.println(message);
//        System.out.println("Good bye");
        System.out.println("Observation date: " + observ.getDate());
        System.out.println("Wind chill: " + observ.getWindChill());
    }
}
