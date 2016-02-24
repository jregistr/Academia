/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab02;

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
        Cone cone1 = new Cone (8,18);
	int cone1V = (int) (cone1.getVolume());
	//System.out.println( "The Volume is " + cone1.getVolume());
	System.out.println( "The Volume is " + cone1V);
        Cone cone2 = new Cone (12, 50);
        int cone2V = (int) cone2.getVolume();
        Cone cone3 = new Cone (60, 20);
        int cone3V = (int) cone3.getVolume();
        System.out.println("Cone 2 Volume is " + cone2V);
        System.out.println("Cone 3 Volume is " + cone3V);
//	cone1.scale(3);
//	System.out.println( "Scaled by 4 Volume is " + (int)(cone1.getVolume()));
//	cone1.shrink(2);
//	System.out.println( "Shrunk by 2 Volume is " + (int)(cone1.getVolume()));
//        Cone cone2 = new Cone (10,25);
//        int cone2V = (int) (cone2.getVolume());
//        System.out.println("Cone 2 Volume is " + cone2V);
//        cone2.shrink(2);
//                 
//        
//        Scanner kbInput = new Scanner (System.in);
//        System.out.println("You wrote  " + kbInput.nextLine());
    }
}
