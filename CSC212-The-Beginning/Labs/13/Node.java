/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab13;

/**
 *
 * @author Jeffrey R
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private T value;
    public boolean isRoot = false;
    private boolean highLighted = false;
    public Node<T> leftNode;
    public Node<T> rightNode;

    public Node(T v) {
        value = v;
        leftNode = null;
        rightNode = null;
    }

    public T getValue() {
        return this.value;
    }
    
    public boolean isHightLighted (){
        return highLighted;
    }

    public int nodeCount() {
        int count = (isRoot) ? 1 : 0;
        if (leftNode != null) {
            count += 1;
            count += leftNode.nodeCount();
        }

        if (rightNode != null) {
            count += 1;
            count += rightNode.nodeCount();
        }
        return count;
    }

    public void insert(T v) {
        // System.out.println("Insert method called");
        if ((v.compareTo(value)) == 1) { //V is greater than value
            //put v in the right sub tree
            if (rightNode == null) {
                rightNode = new Node<>(v);
            } else {
                rightNode.insert(v);
            }
        } else if ((v.compareTo(value)) == -1) { //V is less than value
            //put v in the left sub tree
            if (leftNode == null) {
                leftNode = new Node<>(v);
            } else {
                leftNode.insert(v);
            }
        } else { // V is equal to value
            // tell the user duplicates are not allowed
            System.out.println("Duplicates not allowed");
        }
    }

    public boolean contains(T v) {
        if (v == value) {
            //System.out.println("Match found");
            return true;
        } else if (rightNode == null && leftNode == null) {
            //System.out.println("No more nodes to check");
            return false;
        } else if ((v.compareTo(value) == 1) && rightNode != null) {
            //System.out.println("Greater value");
            return rightNode.contains(v);
        } else {
            // System.out.println("Smaller value");
            if (leftNode != null) {
                return leftNode.contains(v);
            } else {
                return false;
            }
        }
    }

    public T search(T v) {
        if (v == value) {
            return value;
        } else if (rightNode == null && leftNode == null) {
            return null;
        } else if ((v.compareTo(value) == 1) && rightNode != null) {
            return rightNode.search(v);
        } else {
            if (leftNode != null) {
                return leftNode.search(v);
            } else {
                return null;
            }
        }
    }

    public Node<T> getNode(T v) {
        if (v == value) {
            return this;
        } else if (rightNode == null && leftNode == null) {
            return null;
        } else if ((v.compareTo(value) == 1) && rightNode != null) {
            return rightNode.getNode(v);
        } else {
            if (leftNode != null) {
                return leftNode.getNode(v);
            } else {
                return null;
            }
        }
    }

    public void hightLight(T v) {
        if (v == value) {
            if (!highLighted) {
                highLighted = true;
            } else {
                highLighted = false;
            }
            //return;
        } else if (rightNode == null && leftNode == null) {
            //return;
        } else if ((v.compareTo(value) == 1) && rightNode != null) {
            rightNode.hightLight(v);
        } else {
            if (leftNode != null) {
                leftNode.hightLight(v);
            }

        }
    }

    public void hightLight() {
        if (!highLighted) {
            highLighted = true;
        } else {
            highLighted = false;
        }

        if (rightNode != null) {
            rightNode.hightLight();
        } else {
            if (leftNode != null) {
                leftNode.hightLight();
            }

        }
    }

    public void printInOrder() {
        // Go left
        if (leftNode != null) {
            leftNode.printInOrder();
        }

        System.out.print(String.format("[ %s ]", value));

        // Go right
        if (rightNode != null) {
            rightNode.printInOrder();
        }
    }
    
    public void printReverseOrder(){
        if (rightNode != null) {
            rightNode.printReverseOrder();
        }
        System.out.print(String.format("[ %s ]", value));
         if (leftNode != null) {
            leftNode.printReverseOrder();
        }
    }

    public void printPreOrder() {
        System.out.print(String.format("[ %s ]", value));
        // Go left
        if (leftNode != null) {
            leftNode.printPreOrder();
        }

        if (rightNode != null) {
            rightNode.printPreOrder();
        }
    }

    public void printPostOrder() {
        // Go left
        if (leftNode != null) {
            leftNode.printPostOrder();
        }
        if (rightNode != null) {
            rightNode.printPostOrder();
        }
        System.out.print(String.format("[ %s ]", value));
    }

    @Override
    public int compareTo(Node<T> other) {
        return (getValue().compareTo(other.getValue()));
    }
}
