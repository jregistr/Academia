/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw5;

/**
 *
 * @author Jeffrey R
 */
public class Plane {
    
    private String flightNumber;
    private Passenger[][] seats;
    
    public Plane (String fn, int nr, int spr){
        flightNumber = fn;
        seats = new Passenger[nr][spr];
    }
    
    public String getFlightNumber () {
        return flightNumber;
    }
    
    public boolean addPassenger(String pName, int pRow, int pSeat) {
        //System.out.println("Add Pass method");
        boolean succesful = false;
        boolean validRow = (seats != null && (!(pRow >= seats.length) && !(pRow < 0))) ? true : false;
        if (validRow) {
            boolean availableSeat = ((!(pSeat >= seats[0].length) && !(pSeat < 0)) && seats[pRow][pSeat] == null ) ? true : false;
            if (availableSeat) {
                Passenger person = new Passenger(pName);
                seats[pRow][pSeat] = person;
                succesful = true;
            } 
        }
        
        if(succesful){
            return true;
        }else{
            return false;
        }
        
    }
    
    public boolean removePassenger(int pRow, int pSeat) {
        boolean succesful = false;
        boolean validRow = (seats != null && (!(pRow >= seats.length) && !(pRow < 0))) ? true : false;
        if(validRow){
            boolean availableSeat = ((!(pSeat >= seats[0].length) && !(pSeat < 0)) && seats[pRow][pSeat] == null ) ? true : false;
            if(availableSeat){
                seats[pRow][pSeat] = null;
                succesful = true;
            }
        }
        
        if (succesful) {
            return true;
        } else {
            return false;
        }
    }
    
    public void showSeats(){
        System.out.print("  ");
        for (int i = 0; i < seats[0].length; i++) {
            System.out.printf("|%25d|", i);
        }
        System.out.println();
        for (int i = 0; i < seats.length; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < seats[0].length; j++) {
                if(seats[i][j] == null){
                    System.out.printf("|%25s|", "No Passenger");
                }else{
                    System.out.printf("|%25s|", seats[i][j].getName());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void passengerList (){
        //System.out.println("Pass List method");
        for (int i = 0; seats != null && i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                if(seats[i][j] != null){
                    System.out.println(seats[i][j]);
                }
            }
        }
    }
}
