/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab06;

/**
 *
 * @author Jeffrey R
 */
public class Song {
    private String tittle;
    private String artist;
    
    public Song (String t, String a){
        tittle  = t;
        artist = a;
    }
    
    @Override
    public String toString (){
        String temp = "Tittle " + tittle + " Artist " +  artist;
        return temp;
    }
}
