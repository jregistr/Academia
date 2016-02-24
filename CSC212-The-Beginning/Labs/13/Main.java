/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab13;

import java.util.Scanner;

/**
 *
 * @author Jeffrey R
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Node<Integer> root = null;
        int vl = 15;

        //System.out.println(String.format("%vls", "wow"));

        System.out.println("Enter a command (i, c, d, s, h, r, count, in, pre, post, or q):");
        while (!sc.hasNext("q")) {
            String input = sc.next();

            if (input.equalsIgnoreCase("i")) {
                int num = sc.nextInt();
                if (root == null) {
                    root = new Node<>(num);
                    root.isRoot = true;
                } else {
                    root.insert(num);
                }
            } else if (input.equalsIgnoreCase("c")) {
                int num = sc.nextInt();
                if (root != null) {
                    System.out.println(root.contains(num));
                }
            } else if (input.equalsIgnoreCase("d")) {
                if (root != null) {
                    System.out.println("<--------------------------New Line-------------------------->");
                    System.out.println("--------------------------------------------------------------");
                    printBinaryTree(root, 0);
                }
            } else if (input.equalsIgnoreCase("s")) {
                int num = sc.nextInt();
                if (root != null) {
                    Integer temp = root.search(num);
                    System.out.println("Found " + temp);
                }
            }else if(input.equalsIgnoreCase("h")){
                boolean foundInt = false;
                int num = 0;
                String secIn = "";
                if(sc.hasNextInt()){
                    num = sc.nextInt();
                }else{
                    secIn = sc.next();
                }
                if(foundInt){
                    if(root != null){
                    root.hightLight(num);
                    }
                }else{
                    if(secIn.equalsIgnoreCase("a")){
                        if(root != null){
                            root.hightLight();
                        }
                    }
                }
            } else if(input.equalsIgnoreCase("r")){
                if(root != null){
                    root.printReverseOrder();
                }
            }
            else if (input.equalsIgnoreCase("in")) {
                if (root == null) {
                    System.out.println("The tree has no nodes");
                } else {
                    System.out.println("[In Order]");
                    root.printInOrder();
                    System.out.println("");
                }

            } else if (input.equalsIgnoreCase("pre")) {
                if (root == null) {
                    System.out.println("The tree has no nodes");
                } else {
                    System.out.println("[Pre-Order]");
                    root.printPreOrder();
                    System.out.println("");
                }

            } else if (input.equalsIgnoreCase("post")) {
                if (root == null) {
                    System.out.println("The tree has no nodes");
                } else {
                    System.out.println("[Post-Order]");
                    root.printPostOrder();
                    System.out.println("");
                }
            } else if (input.equalsIgnoreCase("count")) {
                if (root != null) {
                    System.out.println("Count " + root.nodeCount());
                }
            } else {
                System.out.println("Incorect input");
            }
            //System.out.println("Enter a command (i, in, pre, post, or q):");
        }


    }

    public static void printBinaryTree(Node<Integer> node, int level) {
        if (node == null) {
            return;
        }
        printBinaryTree(node.rightNode, level + 1);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t");
            }
            if(node.isHightLighted()){
                System.out.println(String.format("|-------{%s}", node.getValue()));
            }else{
            System.out.println("|-------" + node.getValue());
            }
        } else {
            if(node.isHightLighted()){
                System.out.println(String.format("|-------{%s}", node.getValue()));
            }else{
            System.out.println(node.getValue());
            }
        }
        printBinaryTree(node.leftNode, level + 1);
    }
}
