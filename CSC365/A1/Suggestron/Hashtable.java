package Suggestron;

import java.util.ArrayList;
public class Hashtable {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_ARRAY_SIZE = 1024;

    private int distinctlements = 0;

    private Node[] table;

    public Hashtable(){
        table = new Node[INITIAL_ARRAY_SIZE];
    }

    public String[] getAllKeyValuePairs(){
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < table.length; i++) {
            Node node = table[i];
            if(node != null){
                node.allPairsIntoList(temp);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }

    public int getDistinctlements(){
        return distinctlements;
    }

    public String[] getAllKeys(){
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < table.length; i++) {
            Node node = table[i];
            if(node != null){
                node.allKeysIntoList(temp);
            }
        }
        return temp.toArray(new String[temp.size()]);
    }

    public void add(String k,int v){
        int bucket = Math.abs(k.hashCode())%table.length;
        if(table[bucket] == null){
            table[bucket] = new Node(k,v);
            distinctlements++;
        }else{
            int preCount = table[bucket].nodeCount();
            table[bucket].append(k,v);
            int postCount = table[bucket].nodeCount();
            if(postCount > preCount){
                distinctlements++;
            }
        }
        if(distinctlements >= (table.length * LOAD_FACTOR)){
            rehash();
        }
    }

    public void add(String k){
        int bucket = Math.abs(k.hashCode())%table.length;
        if(table[bucket] == null){
            table[bucket] = new Node(k,1);
            distinctlements++;
        }else{
            int preCount = table[bucket].nodeCount();
            table[bucket].append(k,1);
            int postCount = table[bucket].nodeCount();
            if(postCount > preCount){
                distinctlements++;
            }
        }
        if(distinctlements >= (table.length * LOAD_FACTOR)){
            rehash();
        }
    }

    public int occurences(String k){
        int bucket = Math.abs(k.hashCode()) % table.length;
        if(table[bucket] != null){
            return table[bucket].search(k);
        }else {
            return -1;
        }
    }

    public int nodeCount(){
        int size = 0;
        for (int i = 0; i < table.length; i++) {
            Node node = table[i];
            if(node != null){
                size += node.nodeCount();
            }
        }
        return size;
    }

    public int wordCount(){
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            Node node = table[i];
            if(node != null){
                count += node.wordCount();
            }
        }
        return count;
    }

    private void rehash(){
        Node[] oldTable = table;
        distinctlements = 0;
        table = new Node[oldTable.length * 2];
        for (int i = 0; i < oldTable.length; i++) {
            Node link = oldTable[i];
            if(link != null){
                ArrayList<String> pairs = new ArrayList<String>();
                link.allPairsIntoList(pairs);
                for (int j = 0; j < pairs.size(); j++) {
                    String p = pairs.get(j);
                    String[] split = p.split(",");
                    add(split[0],Integer.parseInt(split[1]));
                }
            }
        }
    }

    private class Node{
        private String key;
        private int value;
        private Node next;

        public Node(String k,int v){
            key = k;
            value = v;
        }

        public String getKey(){
            return  key;
        }

        public int getValue(){
            return value;
        }

        public Node getNext(){
            return next;
        }

        public void incrementValue(){
            value++;
        }

        public int nodeCount(){
            int size = 0;
            Node node = this;
            while (node != null){
                size++;
                node = node.getNext();
            }
            return size;
        }

        public int wordCount(){
            int count = 0;
            Node node = this;
            while (node != null){
                count+=node.getValue();
                node = node.getNext();
            }
            return count;
        }

        public void append(String k, int v){
            if(k.equalsIgnoreCase(key)){
                incrementValue();
            }else {
                if(next == null){
                    next = new Node(k,v);
                }else {
                    next.append(k, v);
                }
            }
        }

        public int search(String k){
            if(key.equals(k)){
                return this.value;
            }else {
                if(next != null){
                    return next.search(k);
                }else {
                    return -1;
                }
            }
        }

        public void allPairsIntoList(ArrayList<String> list){
            list.add(String.format("%s,%s",key,value));
            if(next != null){
                next.allPairsIntoList(list);
            }
        }

        public void allKeysIntoList(ArrayList<String> list){
            list.add(key);
            if(next != null){
                next.allKeysIntoList(list);
            }
        }

        @Override
        public String toString(){
            return String.format("Key:%s,Value:%s",key,value);
        }
    }

}




















