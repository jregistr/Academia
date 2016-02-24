/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw8;

/**
 *
 * @author jregistr
 */
public class BinaryTreeNode {
    public String value;
    public BinaryTreeNode left, right;
    
    public BinaryTreeNode (String v){
        value = v;
        left = null;
        right = null;
    }
    
    public BinaryTreeNode insert(String v){
        if(v.compareTo(value) > 0 ){
            if(right == null){
                BinaryTreeNode temp = new BinaryTreeNode (v);
                right = temp;
                return temp;
            }else{
                return right.insert(v);
            }
        }else {
            if(left == null){
                left = new BinaryTreeNode (v);
                return left;
            }else{
                return left.insert(v);
            }
        }
    }
    
//    public void insert(String v){
//        if(v.compareTo(value) > 0 ){
//            if(right == null){
//                BinaryTreeNode temp = new BinaryTreeNode (v);
//                right = temp;
//            }else{
//                right.insert(v);
//            }
//        }else if (v.compareTo(value) < 0 ){
//            if(left == null){
//                left = new BinaryTreeNode (v);
//            }else{
//                left.insert(v);
//            }
//        }
//    }
    
    public boolean contains(String v) {
        if (v.equals(value)) {
            return true;
        } else if (left == null && right == null) {
            return false;
        } else if (v.compareTo(value) >= 0) {
            if (right != null) {
                return right.contains(v);
            } else {
                return false;
            }
        } else {
            if (left != null) {
                return left.contains(v);
            } else {
                return false;
            }
        }
    }
    
     public void printInOrder() {
        // Recurse - Go left
        if (left != null) {
            left.printInOrder();
        }
        // Base case - Print the current node
        System.out.print(String.format("[%s]", value));
        
        // Recurse - Go right
        if (right != null) {
            right.printInOrder();
        }
    }
    
    public void printPostOrder(){
         if (left != null) {
            left.printPostOrder();
        }
         
         if (right != null) {
            right.printPostOrder();
        }
         
        System.out.print(String.format("[%s]", value));

    }
    
    public void printPreOrder(){
        // Base case - Print the current node
        System.out.print(String.format("[%s]", value));
        
        if (left != null) {
            left.printPreOrder();
        }
        
        if (right != null) {
            right.printPreOrder();
        }
    }
}
