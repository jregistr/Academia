/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab07;

/**
 *
 * @author Jeffrey R
 */
import java.util.Random;
public class PlayList {
    Song[] songs;
    int filled = 0;
    
    public PlayList(int size){
        songs = new Song[size];
    }
    
    public PlayList(Song[] s){
        songs = s;
    }
    
    public boolean isFull (){
        boolean full = (filled >= songs.length) ? true : false;
        return full;
    }
  
    public void addSong(String t, String a) {
        boolean added = false;
        for (int i = 0; i < songs.length; i++) {
            if (songs[i] == null) {
                Song temp = new Song(t, a);
                songs[i] = temp;
                filled++;
                added = true;
                break;
            }
        }
        String message = (added) ? "Added Song: " + t + " by " + a : "Failed to add Song";
        System.out.println(message);
    }
    
    
    public Song[] shufflePlayList(Song[] songArray) {
        Random rng = new Random();
        for (int i = songArray.length - 1; i >= 0; i--) {
            int curIndex = rng.nextInt(i + 1);
            Song temp = songArray[curIndex];
            songArray[curIndex] = songArray[i];
            songArray[i] = temp;
        }
        return songArray;
    }
    
    public void shufflePlayList() {
        Random rng = new Random();
        for (int i = songs.length - 1; i >= 0; i--) {
            int randIndex = rng.nextInt(i + 1);
            Song temp = songs[randIndex];
            songs[randIndex] = songs[i];
            songs[i] = temp;
        }
    }
    
    public void display(){
        for(int i = 0; i < songs.length; i ++){
            if(songs[i] != null){
                System.out.println(songs[i]);
            }
        }
    }
    
    public void display(int index){
          boolean displayAble = false;
        if (!(index >= songs.length)) {
            if (songs[index] != null) {
                System.out.println(songs[index]);
                displayAble = true;
            }
        }
        if(!displayAble){
            String message = "Failed to display song at index " + index;
                  System.out.println(message);
        }
    }
    
    public void remove(String t) {
        boolean removed = false;
        String a = "";
        for (int i = 0; i < songs.length; i++) {
            if ((songs[i] != null) && t.equals(songs[i].getTittle())) {
                a = songs[i].getArtist();
                songs[i] = null;
                removed = true;
                filled--;
                break;
            }
        }
        String message = (removed) ? "Removed Song: " + t + " by " + a : "Failed to remove Song: " + t;
        System.out.println(message);
    }
    
    public void remove(int index) {
        String a = "";
        String t = "";
        boolean removed = false;
        if (songs[index] != null) {
            a = songs[index].getArtist();
            t = songs[index].getTittle();
            songs[index] = null;
            filled--;
            removed = true;
        }
        String message = (removed) ? "Removed Song: " + t + " by " + a : "Failed to remove Song";
        System.out.println(message);
    }
}
