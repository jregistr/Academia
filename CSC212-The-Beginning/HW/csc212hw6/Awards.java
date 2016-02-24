/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw6;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jeffrey R
 */
public class Awards {
    private ArrayList<String> ids;
    private ArrayList<Award> awards;
    
    public Awards (){
        ids = new ArrayList<>();
        awards = new ArrayList<>();
    }
    
    public void load (String fn) throws Exception{
        File f = new File (fn);
        Scanner fileScan = new Scanner(f);
        while (fileScan.hasNextLine()){
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner (line);
            lineScan.useDelimiter(",");
            String id = lineScan.next();
            String tit = lineScan.next();
            int y = lineScan.nextInt();
            String le = lineScan.next();
            ids.add(id);
            Award temp = new Award (tit, y,le);
            awards.add(temp);
        }
    }
    public void printAwards (String id){
        boolean foundAnAward = false;
        for (int i = 0; i < ids.size(); i++) {
            if(id.equals(ids.get(i))){
                System.out.println(awards.get(i));
                foundAnAward = true;
            }
        }
        if(!foundAnAward){
            System.out.println("No awards for this player.");
        }
    }
}
