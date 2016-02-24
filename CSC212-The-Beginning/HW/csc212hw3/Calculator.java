/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw3;

/**
 *
 * @author Jeffrey R
 */
public class Calculator {
    
    public int max (int int1, int int2)
    {
        int big = (int1 > int2) ? int1 : int2;
        return big;
    }
    
    public double max (double db1, double db2, double db3){
        double big1 = (db1 > db2) ? db1 : db2;
        double big2 = (big1 > db3) ? big1 : db3;
        return big2;
    }
    
    public int min (int int1, int int2)
    {
        int small = (int1 < int2) ? int1 : int2;
        return small;
    }
    
    public double min (double db1, double db2, double db3){
        double small1 = (db1 < db2) ? db1 : db2;
        double small2 = (small1 < db3) ? small1 : db3;
        return small2;
    }
    
    public double avg (double db1, double db2, double db3){
        double add = db1 + db2 + db3;
        double div = add / 3.0f;
        return div;
    }
    
    public double median (double db1, double db2, double db3){
        return db2;
    }
}
