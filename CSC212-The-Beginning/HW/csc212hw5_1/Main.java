/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw5_1;

/**
 *
 * @author Jeffrey R
 */
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        Plane flights;
        System.out.println("Welcome to Oswego Airlines");

        System.out.println("Enter a flight number:");
        String fnum = scan.nextLine();
        System.out.println("Enter the number of rows:");
        int nR = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the number of seats per row:");
        int spr = scan.nextInt();
        scan.nextLine();

        Plane temp = new Plane(fnum, nR, spr);
        flights = temp;


        System.out.println("Enter add, remove, seats, list, or quit:");
        while (!scan.hasNext("quit")) {
            String command = scan.nextLine();
            if (command.equalsIgnoreCase("add")) {
                System.out.println("Enter passenger name, row, and seat:");
                String pn = scan.next();
                int r = scan.nextInt();
                int s = scan.nextInt();
                scan.nextLine();

                if (flights.addPassenger(pn, r, s)) {
                    String message = String.format("Passenger %s was added.", pn);
                    System.out.println(message);
                } else {
                    System.out.println("Invalid seat -- please try again.");
                }

            } else if (command.equalsIgnoreCase("remove")) {
                System.out.println("Enter row and seat:");
                //String fn = scan.next();
                int r = scan.nextInt();
                int s = scan.nextInt();
                scan.nextLine();

                if (flights.removePassenger(r, s)) {
                    System.out.println("Passenger was removed.");
                } else {
                    System.out.println("Invalid seat -- please try again.");
                }

            } else if (command.equalsIgnoreCase("seats")) {

                flights.showSeats();

            } else if (command.equalsIgnoreCase("list")) {

                flights.passengerList();

            } else {
                System.out.println("Unknown command -- please try again.");
            }
            System.out.println("Enter add, remove, seats, list, or quit:");
        }
        System.out.println("Closing Oswego Airlines");
    }
}
