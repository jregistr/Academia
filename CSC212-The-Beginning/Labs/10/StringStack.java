/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab10;

/**
 *
 * @author Jeffrey R
 */
public class StringStack {

    private String[] stack;
    private int top;

    public StringStack(int maxSize) {
        stack = new String[maxSize];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }
    
    public boolean isFull (){
        return top == stack.length - 1;
    }
    
    public void push (String temp){
        if(!this.isFull()){
            top ++;
            stack[top] = temp;
        }
    }
    
    public String pop (){
        if(!this.isEmpty()){
            String temp = stack[top];
            stack[top] = null;
            top --;
            return temp;
        }else{
            return null;
        }
    }
    
    public String peek (){
        if(!this.isEmpty()){
            return stack[top];
        }else{
            return null;
        }
    }
}
