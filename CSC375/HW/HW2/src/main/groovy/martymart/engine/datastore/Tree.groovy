package martymart.engine.datastore

import java.util.concurrent.atomic.AtomicReference


class Tree<K extends Comparable<K>,V> {

    private static class Node<K extends Comparable<K>,V> implements Comparable<Node<K,V>> {

        protected final K key
        protected final V value

        protected AtomicReference<Node<K,V>> left
        protected AtomicReference<Node<K,V>> right

        public Node(K key, V value){
            this.key = key
            this.value = value
            left = new AtomicReference<>(null)
            right = new AtomicReference<>(null)
        }

        @Override
        int compareTo(Node<K, V> o) {
            key.compareTo(o.key)
        }
    }

    private AtomicReference<Node<K,V>> root;

    public Tree(){
        root = new AtomicReference<>(null)
    }

    public boolean isEmpty(){
        root == null
    }

    public V get(K key){
        if(root.get() == null)
            null
        else {
            if(key.compareTo(root.get().key) == 0){
                root.get().value
            }else if(key.compareTo(root.get().key) < 0){//go left
               if(root.get().left != null)
                   find(key, root.get().left)
               else
                    null
            }else {//go right
                if(root.get().right != null)
                    find(key, root.get().right)
                else
                    null
            }
        }
    }

    private V find(K key, AtomicReference<Node<K,V>> node){
        if(node.get() != null){
            def c = key.compareTo(node.get().key)
            if(c == 0){
                node.get().value
            }else if(c < 0){
                if(node.get().left != null)
                    find(key, node.get().left)
                else
                    null
            }else {
                if(node.get().right != null)
                    find(key, node.get().right)
                else
                    null
            }
        }else
            null
    }

    public void insert(K key, V value){
        Node<K,V> node = new Node<>(key, value)
        if(!root.compareAndSet(null, node)){
            insertNode(new AtomicReference<Node<K,V>>(node), root)
        }
    }

    private void insertNode(AtomicReference<Node<K,V>> newNode, AtomicReference<Node<K,V>> target){
        def c = newNode.get().compareTo(target.get())
        if(c < 0){
            if(!target.get().left.compareAndSet(null, newNode.get())){
                insertNode(newNode, target.get().left)
            }
        }else if(c > 0){
            if(!target.get().right.compareAndSet(null, newNode.get())){
                insertNode(newNode, target.get().right)
            }
        }
    }

    public void inOrder(){
        doInOrder(root.get())
    }

    private void doInOrder(Node<K,V> node){
        if(node){
            doInOrder(node.left.get())
            print node.key + " "
            doInOrder(node.right.get())
        }
    }

}
