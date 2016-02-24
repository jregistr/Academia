/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw6;

/**
 *
 * @author Jeffrey R
 */
public class Award {
    private String tittle,league;
    private int year;
    
    public Award (String t, int y, String l){
        tittle = t;
        year = y;
        league = l;
    }
    
    //year league then tittle
    @Override
    public String toString (){
        String temp = String.format ("%s %s %s", year, league, tittle);
        return temp;
    }
}
