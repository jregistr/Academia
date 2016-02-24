/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab12;

/**
 *
 * @author Jeffrey R
 */
public class StringList {

    private String value;
    private StringList next;

    public StringList(String v) {
        value = v;
        next = null;
    }

    public StringList prepend(String v) {
        // This puts a new StringList object at the front of the list
        StringList s = new StringList(v);
        s.next = this;
        return s;
    }
    
    public void append (String v){
        if(next == null){
            StringList temp = new StringList(v);
            next = temp;
        }else{
            next.append(v);
        }
    }
    
    public boolean contains (String v){
        if(v.equals(value)){
            return true;
        }else if(next == null){
            return false;
        }else{
            return next.contains(v);
        }
    }
    
    @Override
    public String toString() {
        // This recursively generates a string representation of the list
        String temp = value;
        if (next != null) {
            temp = temp + " : " + next.toString();
        }
        return temp;
    }
}
