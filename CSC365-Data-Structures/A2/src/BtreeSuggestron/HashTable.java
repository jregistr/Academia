package BtreeSuggestron;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class HashTable {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_ARRAY_SIZE = 4;

    public static final int KEY_SIZE = 256;
    private static final int NODE_STARTING = 4;
    private static final int VALUE_SIZE = 64;
    private static final int POINTER_SIZE = 8;
    private static final int LAST_MOD_SIZE = 8;
    private static final int NULL_POINTER = -1;
    private static final String ENCODING = "UTF-8";
    private static final String CACHE_FILE_NAME = "cache.hash";


    private RandomAccessFile accessFile;
    private int currentArraySize;

    private static HashTable instance;

    public static HashTable getInstance(){
        if(instance == null)
            try{
                instance = new HashTable();
            } catch (IOException e) {
                instance = null;
            }
        return instance;
    }

    private HashTable() throws IOException {
        accessFile = new RandomAccessFile(CACHE_FILE_NAME,"rw");
        /*if(!isInitialised()) {
            allocateEmptyTable(INITIAL_ARRAY_SIZE);
        }else {
            accessFile.seek(0);
            currentArraySize = accessFile.readInt();
            System.out.println("Current:" + currentArraySize);
        }*/
        if(isInitialised()){
            accessFile.seek(0);
            currentArraySize = accessFile.readInt();
            //System.out.println("Current:" + currentArraySize);
        }
    }

    public void close() throws IOException {
        accessFile.close();
    }

    public void put(String key, String value,long lastMod) {
        try {
            if(!isInitialised()) {
                doInit();
            }
            String test = get(key);
            if(test == null || test.isEmpty()){
                int bucket = Math.abs(key.hashCode()) % currentArraySize;
                long position = bucketPosition(bucket);

                Node node = read(position);
                if (node != null) {
                    node.append(key, value,lastMod, position);
                } else {
                    flush(new Node(key, value,lastMod), position);
                }
                Node[] all = getAllNodes();
                if (all.length >= (currentArraySize * LOAD_FACTOR)) {
                    rehash();
                }
            }
        }catch (IOException e){
            System.out.println("Failed to add:" + key);
        }
    }

    public String get(String key){
        try{
            int bucket = Math.abs(key.hashCode())% currentArraySize;
            long position = bucketPosition(bucket);
            Node node = read(position);
            if(node != null) {
                return node.search(key);
            }else {
                return null;
            }
        }catch (IOException e) {
            return null;
        }
    }

    private void rehash() throws IOException {
        Node[] all = getAllNodes();
        clearFile();
        allocateEmptyTable(currentArraySize * 2);
        for (Node node : all) {
            put(node.key, node.value,node.lastModified);
        }
    }

    private long bucketPosition(int index) {
        return NODE_STARTING + (nodeSize() * index);
    }

    public boolean isInitialised() {
        try {
            accessFile.seek(0);
            int size = accessFile.readInt();
            //System.out.println(size);
            return size > 0;
        } catch (IOException e) {
            return false;
        }
    }

    private void doInit() throws IOException {
        allocateEmptyTable(INITIAL_ARRAY_SIZE);
    }

    private void allocateEmptyTable(int initSize) throws IOException {
        currentArraySize = initSize;
        accessFile.seek(0);
        accessFile.writeInt(currentArraySize);
        long position = accessFile.getFilePointer();
        //System.out.println("Pointer:" + position);
        accessFile.seek(position);
        int totalSize = nodeSize() * currentArraySize;
        for (int i = 0; i < totalSize; i++) {
            accessFile.write(" ".getBytes());
        }
    }

    private void flush(Node node, long position) throws IOException {
        accessFile.seek(position);
        byte[] keyToBytes = node.key.getBytes(ENCODING);
        String fill = filler(keyToBytes.length,KEY_SIZE);
        accessFile.write(keyToBytes);
        if(fill != null)
            accessFile.write(fill.getBytes());
        byte[] valueToBytes = node.value.getBytes(ENCODING);
        fill = filler(valueToBytes.length,VALUE_SIZE);
        accessFile.write(valueToBytes);
        if(fill != null)
            accessFile.write(fill.getBytes());
        accessFile.writeLong(node.getLastModified());
        accessFile.writeLong(node.next);
    }

    private Node read(long position) throws IOException {
        FileChannel channel = accessFile.getChannel();
        ByteBuffer keyBuffer = ByteBuffer.allocate(KEY_SIZE);
        ByteBuffer valueBuffer = ByteBuffer.allocate(VALUE_SIZE);
        accessFile.seek(position);
        channel.read(keyBuffer);
        channel.read(valueBuffer);
        long lastMod = accessFile.readLong();
        long nextNode = accessFile.readLong();
        valueBuffer.flip();
        keyBuffer.flip();

        String keyRead = new String(keyBuffer.array(), ENCODING);
        String valueRead = new String(valueBuffer.array(),ENCODING);
        String k = keyRead.replace(" ", "");
        if(k != null && !k.isEmpty()) {
            String v = valueRead.replace(" ", "");
            return new Node(k, v,lastMod,nextNode);
        }else {
            return null;
        }
    }

    public void updateLastModified(String key,long newLast) throws IOException {
        int bucket = Math.abs(key.hashCode()) % currentArraySize;
        long bucketPos = bucketPosition(bucket);
        Node node = read(bucketPos);
        if(node != null){
            if(node.key.equals(key)) {
                long position = bucketPos + KEY_SIZE + VALUE_SIZE;
                accessFile.seek(position);
                accessFile.writeLong(newLast);
            }else {
                long position = node.searchForNode(key);
                if(position != -1) {
                    long newPos = position + KEY_SIZE + VALUE_SIZE;
                    accessFile.seek(newPos);
                    accessFile.writeLong(newLast);
                }
            }
        }
    }

    public void printAllNodes() throws IOException {
        Node[] all = getAllNodes();
        for (Node node : all) {
            System.out.println(node);
        }
    }

    public ArrayList<Pair<String,String>> getAllPairs() throws IOException {
        ArrayList<Pair<String, String>> pairs = new ArrayList<>();
        Node[] all = getAllNodes();
        for (Node node : all) {
            pairs.add(new Pair<>(node.getKey(),node.getValue()));
        }
        return (pairs.size() > 0) ? pairs : null;
    }

    public ArrayList<Pair<String,Long>> getAllLastModified() throws IOException {
        ArrayList<Pair<String, Long>> pairs = new ArrayList<>();
        Node[] all = getAllNodes();
        for (Node node : all) {
            pairs.add(new Pair<>(node.key, node.getLastModified()));
        }
        return pairs.size() > 0 ? pairs : null;
    }

    private Node[] getAllNodes() throws IOException {
        if(currentArraySize > 0) {
            ArrayList<Node> collection = new ArrayList<>();
            for (int i = 0; i < currentArraySize; i++) {
                long position = bucketPosition(i);
                Node temp = read(position);
                if(temp != null) {
                    collection.add(temp);
                    long next = temp.getNext();
                    while (next != NULL_POINTER) {
                        Node temper = read(next);
                        if(temper == null)
                            throw new NullPointerException("Next is not null pointer but read is null");
                        collection.add(temper);
                        next = temper.getNext();
                    }
                }
            }
            return collection.size() > 0 ? (collection.toArray(new Node[collection.size()])) : null;
        }else
            return null;
    }

    private long makeLinkNode() throws IOException {
        long position = accessFile.length();
        accessFile.seek(position);
        String fill = "";
        for (int i = 0; i < nodeSize(); i++) {
            fill += ' ';
        }
        accessFile.write(fill.getBytes());
        return position;
    }

    private void clearFile() throws IOException {
        accessFile.setLength(0);
    }

    private String filler(int length,int max){
        int left = max - length;
        if(left > 0){
            String fill = "";
            for (int i = 0; i < left; i++) {
                fill += ' ';
            }
            return fill;
        }else
            return null;
    }

    private int nodeSize() {
        return KEY_SIZE + VALUE_SIZE + POINTER_SIZE + LAST_MOD_SIZE;
    }

    private class Node{

        private String key;
        private String value;
        private long lastModified;
        private long next;


        private Node(String key, String value,long lastModified, long next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.lastModified = lastModified;
        }

        public Node(String key, String value,long lastModified) {
            this.key = key;
            this.value = value;
            this.lastModified = lastModified;
            this.next = NULL_POINTER;
        }

        public void append(String key, String value,long last, long myPosition) throws IOException {
            if(next == NULL_POINTER) {
                long newNodePosition = makeLinkNode();
                Node temp = new Node(key, value,last);
                flush(temp, newNodePosition);
                this.next = newNodePosition;
                flush(this,myPosition);
            }else {
                Node temp = read(next);
                temp.append(key, value,last,next);
            }
        }

        public String search(String key) throws IOException {
            if(key.equals(this.key)) {
                return this.value;
            }else {
                if(this.next != NULL_POINTER) {
                    Node temp = read(next);
                    return temp.search(key);
                }else {
                    return null;
                }
            }
        }

        public long searchForNode(String key) throws IOException{
            if(key.equals(this.key)) {
                return this.getLastModified();
            }else {
                if (this.next != NULL_POINTER) {
                    Node temp = read(next);
                    return temp.searchForNode(key);
                }else {
                    return -1;
                }
            }
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public long getNext() {
            return next;
        }

        public long getLastModified() {
            return lastModified;
        }

        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }

        public void setNext(long next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    ", next=" + next +
                    '}';
        }
    }
}
