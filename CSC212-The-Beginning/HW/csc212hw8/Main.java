/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212hw8;

/**
 *
 * @author jregistr
 */
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        BinaryTreeNode root = null;

        System.out.println("Welcome to Sorter 2.0");
        System.out.println("Enter a command (i, c, in, pre, post, or q):");
        while (!sc.hasNext("q")) {

            String input = sc.next();
            if (input.equalsIgnoreCase("i")) {
                String temp = sc.next();
                if (root == null) {
                    root = new BinaryTreeNode(temp);
                } else {
                    root.insert(temp);
                }
            } else if (input.equalsIgnoreCase("c")) {
                String temp = sc.next();
                if (root == null) {
                    System.out.println("The tree has no nodes.");
                } else {
                    boolean contained = root.contains(temp);
                    if (contained) {
                        System.out.println(String.format("%s is contained in the tree.", temp));
                    } else {
                        System.out.println(String.format("%s is not in the tree.", temp));
                    }
                }
            } else if (input.equalsIgnoreCase("in")) {

                if (root != null) {
                    System.out.println("In-order:");
                    root.printInOrder();
                    System.out.println("");
                } else {
                    System.out.println("The tree has no nodes.");
                }

            } else if (input.equalsIgnoreCase("pre")) {

                if (root != null) {
                    System.out.println("Pre-order:");
                    root.printPreOrder();
                    System.out.println("");
                } else {
                    System.out.println("The tree has no nodes.");
                }

            } else if (input.equalsIgnoreCase("post")) {

                if (root != null) {
                    System.out.println("Post-order:");
                    root.printPostOrder();
                    System.out.println("");
                } else {
                    System.out.println("The tree has no nodes.");
                }

            } else {
                System.out.println(String.format("Invalid command -- %s", input));
            }
            System.out.println("Enter a command (i, c, in, pre, post, or q):");
        }
        System.out.println("Closing Sorter 2.0");
    }
}
