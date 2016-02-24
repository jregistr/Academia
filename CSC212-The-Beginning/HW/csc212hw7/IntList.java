/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw7;

/**
 *
 * @author Jeffrey R
 */
public class IntList {
    public int value;
    public IntList next;
    
    public IntList (int v){
        value = v;
        next = null;
    }
 
    public IntList insert (int v){
        if(v <= value){
            IntList temp = new IntList (this.value);
        this.value = v;
        temp.next = this.next;
        this.next = temp;
        return this;
            //return prepend (v);
        }else if(next == null){
            //append (v);
            IntList temp = new IntList (v);
            next = temp;
            return this;
        }else{
            next = next.insert(v);
            return this;
        }
    }

    @Override
    public String toString (){
        String temp = String.format("%s", String.valueOf(value) );
        if(next != null){
            temp = temp + ":" + next.toString();
        }
        return temp;
    }
    
    public String toStringReverse (){
        String temp = String.format("%s", String.valueOf(value) );
        if(next != null){
            temp = next.toStringReverse() + ":" + temp;
        }
        return temp;
    }
    
}
