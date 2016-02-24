/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw6;

/**
 *
 * @author Jeffrey R
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
public class Players {
    public ArrayList<Player> list;
    public ArrayList<String> ids;
    
    public Players (){
        list = new ArrayList<>();
        ids = new ArrayList<>();
    }
    
    public void load (String fileN) throws Exception{
        File f = new File (fileN);
        Scanner fScan = new Scanner (f);
        
        while (fScan.hasNextLine()){
            String line = fScan.nextLine();
            Scanner lScan = new Scanner (line);
            lScan.useDelimiter(",|\\s");
            String id = lScan.next();
            String fn = lScan.next();
            String ln = lScan.next();
            ids.add(id);
            Player p = new Player (fn, ln);
            list.add(p);
            //System.out.println("P " + p);
        }
    }
    
    public String getPlayerByID (String id){
        int index = ids.indexOf(id);
        if(index > -1){
            return list.get(index).toString();
        }else{
            return null;
        }
    }
    
    public void printIdsByName (String ln){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getLastName().equals(ln)){
                String message = String.format("%s: %s", ids.get(i),list.get(i));
                System.out.println(message);
            }
        }
    }
}
