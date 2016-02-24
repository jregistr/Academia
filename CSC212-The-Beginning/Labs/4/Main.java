/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab04;

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
        String school = "Oswego State";
        String favMovie = "Best in Show";
        int gradYear = 2016;
        int matchCount = 0;
        
        String myMovie, mySchool, line;
        int myYear;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your school name");
        mySchool = br.readLine();
        
        if (school.equals(mySchool)) {
            System.out.println("Me too!");
            matchCount ++;
        }
        else{
            System.out.println("I hear " + mySchool + " is nice");
        }
        System.out.println("What's your favorite movie");
        myMovie = br.readLine();
        
        if(favMovie.equals(myMovie))
        {
            System.out.println("Me too!!");
            matchCount ++;
        }
        else{
            System.out.println("I heard " + myMovie + " Is really great");
            System.out.println("But I haven't seen it yet");
        }
        
        System.out.println("What year did you graduate");
        line = br.readLine();
        if(isInt(line))
        {
            myYear = Integer.parseInt(line);
            if(myYear == gradYear)
            {
                System.out.println("Me too!");
                matchCount ++;
            }
            else
                System.out.println("I graduated in " + gradYear);
        }
        else{
            System.out.println("You didn't type a year");
        }
        
        if(matchCount >= 3)
        {
            System.out.println("We're Soulmates");
        }
        else if(matchCount == 2)
        {
            System.out.println("We have a lot in common!!!!");
        }
        else
        {
            System.out.println("We should get to know each other a bit more");
        }
        System.out.println("Our matchcount is " + matchCount);
    }
    
    private static boolean isInt (String temp)
    {
      try{
          Integer.parseInt(temp);
      }catch (NumberFormatException numForm){
          numForm.toString();
          System.out.println("You typed " + numForm);
          return false;
      }
     return true;
    }
}
