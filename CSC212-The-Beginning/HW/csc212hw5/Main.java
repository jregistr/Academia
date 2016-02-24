/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw5;

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
        Plane[] flights;
        System.out.println("Welcome to Oswego Airlines");
        System.out.println("Enter the number of planes:");
        int numFlights = scan.nextInt();
        flights = new Plane[numFlights];
        scan.nextLine();

        for (int i = 0; i < numFlights; i++) {
            System.out.println("Enter a flight number:");
            String fn = scan.nextLine();
            System.out.println("Enter the number of rows:");
            int nR = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter the number of seats per row:");
            int spr = scan.nextInt();
            scan.nextLine();
            //System.out.println("Made a plane with FN: " + fn + " nR:" + nR + " spr:" + spr);
            Plane temp = new Plane(fn, nR, spr);
            flights[i] = temp;
        }

        System.out.println("Enter add, remove, seats, list, or quit:");
        while (!scan.hasNext("quit")) {
            String command = scan.nextLine();
            //System.out.println("Command " + command);
            if (command.equalsIgnoreCase("add")) {
                System.out.println("Enter the flight number, passenger name, row, and seat:");
                String fn = scan.next();
                String pn = scan.next();
                int r = scan.nextInt();
                int s = scan.nextInt();
                scan.nextLine();
                // System.out.println("fn:" + fn + " pn:" + " r:" + r + " s:" + s);
                boolean foundFlightNum = false;
                for (int i = 0; i < flights.length; i++) {
                    if (flights[i] != null) {
                        if (flights[i].getFlightNumber().equals(fn)) {
                            foundFlightNum = true;
                            //flights[i].addPassenger(pn, r, s);
                            if (flights[i].addPassenger(pn, r, s)) {
                                String message = String.format("Passenger %s was added to flight %s.", pn, fn);
                                System.out.println(message);
                            } else {
                                System.out.println("Invalid seat -- please try again.");
                            }
                            break;
                        }
                    }
                }
                if (!foundFlightNum) {
                    String message = String.format("Flight %s not found -- Please try again.", fn);
                    System.out.println(message);
                }
            } else if (command.equalsIgnoreCase("remove")) {
                System.out.println("Enter the flight number, row, and seat:");
                String fn = scan.next();
                int r = scan.nextInt();
                int s = scan.nextInt();
                scan.nextLine();
                boolean foundFlightNum = false;
                for (int i = 0; i < flights.length; i++) {
                    if (flights[i] != null) {
                        if (flights[i].getFlightNumber().equals(fn)) {
                            foundFlightNum = true;
                            //flights[i].removePassenger(r, s);
                            if (flights[i].removePassenger(r, s)) {
                                System.out.println("Passenger was removed.");
                            } else {
                                System.out.println("Invalid seat -- please try again.");
                            }
                            break;
                        }
                    }
                }
                if (!foundFlightNum) {
                    System.out.println(String.format("Flight %s not found -- Please try again.", fn));
                }
            } else if (command.equalsIgnoreCase("seats")) {
                System.out.println("Enter the flight number:");
                String fn = scan.nextLine();

                boolean foundFlightNum = false;
                for (int i = 0; i < flights.length; i++) {
                    if (flights[i] != null) {
                        if (flights[i].getFlightNumber().equals(fn)) {
                            foundFlightNum = true;
                            flights[i].showSeats();
                            break;
                        }
                    }
                }
                if (!foundFlightNum) {
                    System.out.println(String.format("Flight %s not found -- Please try again.", fn));
                }
            } else if (command.equalsIgnoreCase("list")) {

                System.out.println("Enter the flight number:");
                String fn = scan.nextLine();

                boolean foundFlightNum = false;
                for (int i = 0; i < flights.length; i++) {
                    if (flights[i] != null) {
                        if (flights[i].getFlightNumber().equals(fn)) {
                            foundFlightNum = true;
                            flights[i].passengerList();
                            break;
                        }
                    }
                }
                if (!foundFlightNum) {
                    System.out.println(String.format("Flight %s not found -- Please try again.", fn));
                }
            }
            System.out.println("Enter add, remove, seats, list, or quit:");
        }
        System.out.println("Closing Oswego Airlines");
    }
}
