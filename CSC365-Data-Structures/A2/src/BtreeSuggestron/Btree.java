package BtreeSuggestron;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class BTree {

    /*private static final int DEGREE = 12;
    private static final int KEY_SIZE = 150;
    private static final int VALUE_SIZE = 4;
    private static final int POINTER_SIZE = 8;
    private static final int NULL_POINTER = -1;*/

    private static final int DEGREE = 6;
    private static final int KEY_SIZE = 256;
    private static final int VALUE_SIZE = 4;
    private static final int POINTER_SIZE = 8;
    private static final long NULL_POINTER = -1;

    private static final String RANDOM_ACCESS_FILE_PRENAME = "RAF";
    private static final String RANDOM_ACCESS_FILE_SUFFIX = ".tree";
    public static final String TREES_DIRECTORY_NAME = "Trees";
    private static final String ENCODING = "UTF-8";

    private RandomAccessFile accessFile = null;

    private boolean empty = true;

    private boolean addAllowed;
    private int treeNumber;

    /*public BTree(RandomAccessFile randomAccessFile) throws IOException {
        assert randomAccessFile != null;
        accessFile = randomAccessFile;
        addAllowed = false;
    }*/

    public BTree(String fileName)throws IOException {
        accessFile = new RandomAccessFile(TREES_DIRECTORY_NAME + "/" + fileName, "rw");
        //addAllowed = false;
        addAllowed = true;
    }

    public BTree(int number) throws IOException {
        treeNumber = number;
        accessFile = new RandomAccessFile(TREES_DIRECTORY_NAME + "/" + number + "_" + RANDOM_ACCESS_FILE_PRENAME + RANDOM_ACCESS_FILE_SUFFIX, "rw");
        //System.out.println("Total:" + totalNodeSize());
        addAllowed = true;
    }

    public void close() throws IOException {
        accessFile.close();
    }

    public void clear() throws IOException {
        accessFile.setLength(0);
    }

    public void put(String key){
        try{
            if(addAllowed){
                if(key.length() > KEY_SIZE) {
                    key = key.substring(0, KEY_SIZE - 1);
                }
                if(!empty){
                    long nodePosition = getNodeForKey(key);
                    //System.out.println(nodePosition);
                    if(nodePosition != NULL_POINTER) {//it exists
                        Entry[] entries = allEntriesInNode(nodePosition);
                        boolean found = false;
                        for (int i = 0; i < entries.length; i++) {
                            Entry entry = entries[i];
                            if(key.equals(entry.getKey())) {
                                found = true;
                                incrementValue(nodePosition, i);
                                break;
                            }
                        }
                        if(!found)
                            throw new IllegalArgumentException("It was supposed to be found node at positon:" + nodePosition + "But read didnt find it");
                    }else {
                        insert(key);
                    }
                }else {
                    insert(key);
                }
            }else {
                throw new IllegalArgumentException("Add not allowed");
            }
        }catch (IOException e){
            System.out.println("Failed to put");
        }
    }

    private void insert(String key) throws IOException {
        if(addAllowed){
            /*if(key.length() > KEY_SIZE) {
                key = key.substring(0, KEY_SIZE - 1);
            }*/
            if(empty){
                empty = false;
                long newNodeStart = createNewEmptyNode();
                accessFile.seek(newNodeStart);
                accessFile.writeLong(NULL_POINTER);
                long position = accessFile.getFilePointer();
                appendKeyValuePair(position, key,1);
                accessFile.writeLong(NULL_POINTER);
            }else {
                Entry[] entries = readNodeIntoEntriesWithChildren(0l);
                /*boolean existed = false;
                int existsAtIndex = keyExists(key, entries);
                if(existsAtIndex != -1) {
                    existed = true;
                    incrementValue(0l, existsAtIndex);
                }*/

                if(isLeafNode(0l)) {
                    if (entries.length == (DEGREE - 1)) {//full leaf root
                        splitRootNode();
                        insert(key);
                    } else {//non full leaf root
                        addEntryToLeafNode(0l, key,1);
                    }
                }else {
                    if(entries.length == (DEGREE-1)){
                        splitRootNode();
                        insert(key);
                    }else {
                        for (int i = 0; i < entries.length; i++) {
                            Entry entry = entries[i];
                            if(i == entries.length-1){
                                if(key.compareTo(entry.getKey()) < 0){
                                    insert(key,entry.left,0);
                                    break;
                                }else if(key.compareTo(entry.getKey()) > 0){
                                    insert(key,entry.right,0);
                                    break;
                                }
                            }else {
                                if(key.compareTo(entry.getKey()) < 0){
                                    insert(key,entry.left,0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else {
            throw new IllegalArgumentException("Cant add");
        }
    }

    private void insert(String key, long nodeStart, long parent) throws IOException {
        Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);

        if(isLeafNode(nodeStart)){
            if(entries.length == (DEGREE-1)) {//full leaf node
                splitNode(nodeStart, parent);

                entries = readNodeIntoEntriesWithChildren(parent);
                for (int i = 0; i < entries.length; i++) {
                    Entry entry = entries[i];
                    if(i == entries.length-1){
                        if(key.compareTo(entry.getKey()) < 0) {
                            insert(key, entry.left, parent);
                            break;
                        }else if(key.compareTo(entry.getKey()) > 0){
                            insert(key, entry.right, parent);
                            break;
                        }
                    }else {
                        if(key.compareTo(entry.getKey()) < 0) {
                            insert(key, entry.left, parent);
                            break;
                        }
                    }
                }

            }else {//nonfull leaf node
                addEntryToLeafNode(nodeStart,key,1);
            }
        }else {
            if(entries.length == (DEGREE-1)){//full non leaf node
                //  System.out.println("AAAAA");//////////////////////////////////////////////////////////////////////////////////////
                splitNode(nodeStart,parent);
                insert(key);
            }else {//non full leaf node
                for (int i = 0; i < entries.length; i++) {
                    Entry entry = entries[i];
                    if(i == entries.length-1){
                        if(key.compareTo(entry.getKey()) < 0){
                            insert(key,entry.left,nodeStart);
                            break;
                        }else if(key.compareTo(entry.getKey()) > 0){
                            insert(key,entry.right,nodeStart);
                            break;
                        }
                    }else {
                        if(key.compareTo(entry.getKey()) < 0){
                            insert(key,entry.left,nodeStart);
                            break;
                        }
                    }
                }
            }
        }
    }


    private void splitNode(long nodeStart,long parent) throws IOException {
        Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
        int middleIndex = entries.length / 2;
        Entry middleEntry = entries[middleIndex];
        emptyANode(nodeStart);
        int index = 0;
        for (int i = 0; i < middleIndex; i++) {
           // System.out.println("Placed Into Left:" + entries[index].getKey());
            appendOrderedIntoEmpty(nodeStart,i,entries[index].getKey(),entries[index].getValue(),entries[index].left,entries[index].right);
            index++;

        }

        index = (middleIndex + 1);
        long rightNode = createNewEmptyNode();
        for (int i = 0; i < middleIndex; i++) {
            //System.out.println("Placed Into Right:" + entries[index].getKey());
            appendOrderedIntoEmpty(rightNode,i,entries[index].getKey(),entries[index].getValue(),entries[index].left,entries[index].right);
            index++;

        }

        middleEntry.left = nodeStart;
        middleEntry.right = rightNode;
        //System.out.println("Promoting:" + middleEntry.getKey() + " Left:" + middleEntry.left + " Right:" + middleEntry.right);

        promote(middleEntry,parent);
    }


    private void promote(Entry entry,long parent) throws IOException {
        Entry[] nodeEntries = readNodeIntoEntriesWithChildren(parent);
        emptyANode(parent);
        if(nodeEntries.length == 1) {
            Entry inParent = nodeEntries[0];
            if (entry.getKey().compareTo(inParent.getKey()) < 0){
                accessFile.seek(parent);
                accessFile.writeLong(entry.left);
                accessFile.write(entry.getKey().getBytes());
                String fill = filler(entry.getKey().length());
                if(fill != null)
                    accessFile.write(fill.getBytes());
                accessFile.writeInt(entry.getValue());
                accessFile.writeLong(entry.right);
                accessFile.write(inParent.getKey().getBytes());
                String fill2 = filler(inParent.getKey().length());
                if(fill2 != null)
                    accessFile.write(fill2.getBytes());
                accessFile.writeInt(inParent.getValue());
                accessFile.writeLong(inParent.right);
            }else {
                accessFile.seek(parent);
                accessFile.writeLong(inParent.left);
                accessFile.write(inParent.getKey().getBytes());
                String fill = filler(inParent.getKey().length());
                if(fill != null)
                    accessFile.write(fill.getBytes());
                accessFile.writeInt(inParent.getValue());
                accessFile.writeLong(entry.left);
                accessFile.write(entry.getKey().getBytes());
                String fill2 = filler(entry.getKey().length());
                if(fill2 != null)
                    accessFile.write(fill2.getBytes());
                accessFile.writeInt(entry.getValue());
                accessFile.writeLong(entry.right);
            }
        }else {
           // System.out.println("More than ");
            int indexForNewEntry = -1;
            for (int i = 0; i < nodeEntries.length; i++) {
                Entry current = nodeEntries[i];
                if(entry.getKey().compareTo(current.getKey()) < 0){
                    indexForNewEntry = i;
                    break;
                }
            }
          //  System.out.println("///////////////////////// Index for new Entry:" + indexForNewEntry);
            if(indexForNewEntry == -1)
                indexForNewEntry = nodeEntries.length;

            int index = 0;
            for (int i = 0; i <= nodeEntries.length; i++) {
                if(i != indexForNewEntry) {
                    Entry nodeEntry = nodeEntries[index];
                    appendOrderedIntoEmpty(parent, i, nodeEntry.getKey(), nodeEntry.getValue(), nodeEntry.left, nodeEntry.right);
                    index++;
                }
            }
            appendOrderedIntoEmpty(parent,indexForNewEntry,entry.getKey(),entry.getValue(),entry.left,entry.right);
        }
    }

    private int keyExists(String key,Entry[] entries){
        for (int i = 0; i < entries.length; i++) {
            Entry entry = entries[i];
            if(key.compareTo(entry.getKey()) == 0) {
                return i;
            }
        }
        return -1;
    }

    private void splitRootNode() throws IOException {
        //System.out.println("Split Root");
        Entry[] entries = readNodeIntoEntriesWithChildren(0l);
        if(entries == null || entries.length != DEGREE-1) {
            throw new IllegalArgumentException("Root Entries Length:" + ((entries != null) ? entries.length : "Null") + " Degree is: " + (DEGREE-1));
        }
        emptyANode(0);
        int middleIndex = entries.length/2;
        Entry middleEntry = entries[middleIndex];
        long leftNode = createNewEmptyNode();
        int index = 0;
        for (int i = 0; i < middleIndex; i++) {
            appendOrderedIntoEmpty(leftNode, i, entries[index].getKey(), entries[index].getValue(), entries[index].left, entries[index].right);
            index++;
        }

        long rightNode = createNewEmptyNode();
        index = middleIndex+1;
        for (int i = 0; i < middleIndex; i++) {
            appendOrderedIntoEmpty(rightNode, i, entries[index].getKey(), entries[index].getValue(), entries[index].left, entries[index].right);
            index++;
        }

        middleEntry.left = leftNode;
        middleEntry.right = rightNode;

        //System.out.println("Split LefT:" + middleEntry.left + " Right:" + middleEntry.right);

        accessFile.seek(0);
        accessFile.writeLong(middleEntry.left);
        accessFile.write(middleEntry.getKey().getBytes());
        String fill = filler(middleEntry.getKey().length());
        if(fill != null)
            accessFile.write(fill.getBytes());
        accessFile.writeInt(middleEntry.getValue());
        accessFile.writeLong(middleEntry.right);
    }


    private boolean isLeafNode(long nodeStart) throws IOException {
        Entry[] entries = allEntriesInNode(nodeStart);
        for (int i = 0; i < entries.length; i++) {
            long pointerPos = startOfKeyValueBlock(nodeStart, i);
            accessFile.seek(pointerPos);
            long point = accessFile.readLong();
            //System.out.println(point);
            if(point >= 0)
                return false;
        }
        return true;
    }

    private void addEntryToLeafNode(long nodeStart,String key,int value) throws IOException {
        /*if (key.length() > KEY_SIZE) {
            key = key.substring(0, KEY_SIZE - 1);
        }*/
        Entry[] entries = allEntriesInNode(nodeStart);

        assert entries.length < DEGREE - 1;
        boolean append = true;
        int indexToReplace = -1;
        for (int i = 0; i < entries.length; i++) {
            Entry entry = entries[i];
            if (key.compareTo(entry.getKey()) < 0) {
                // System.out.println(key + " smaller than " + entry.getKey() + " Index:" + i);
                append = false;
                indexToReplace = i;
                break;
            }
        }
        if (!append) {
            long repPosition = startOfKeyValueBlock(nodeStart, indexToReplace) + POINTER_SIZE;
            accessFile.seek(repPosition);
            accessFile.write(key.getBytes());
            String fill = filler(key.length());
            if (fill != null)
                accessFile.write(fill.getBytes());
            accessFile.writeInt(value);

            String replacedKey = entries[indexToReplace].getKey();
            int replacedValue = entries[indexToReplace].getValue();
            if (entries.length == 1) {
                long position = startOfKeyValueBlock(nodeStart, 1);
                accessFile.seek(position);
                accessFile.writeLong(NULL_POINTER);
                accessFile.write(replacedKey.getBytes());
                String fillString = filler(replacedKey.length());
                if (fillString != null)
                    accessFile.write(fillString.getBytes());
                accessFile.writeInt(replacedValue);
                accessFile.writeLong(NULL_POINTER);
            } else {
                for (int i = indexToReplace; i < entries.length; i++) {
                    // System.out.println("index: " + (indexToReplace + 1));
                    replacedKey = entries[i].getKey();
                    replacedValue = entries[i].getValue();

                    // System.out.println("Replaced:" + replacedKey + "," + replacedValue);
                    long position = startOfKeyValueBlock(nodeStart, i + 1);
                    //System.out.println("Position:" + position);
                    accessFile.seek(position);
                    accessFile.writeLong(NULL_POINTER);
                    accessFile.write(replacedKey.getBytes());
                    String fillerString = filler(replacedKey.length());
                    if (fillerString != null)
                        accessFile.write(fillerString.getBytes());
                    accessFile.writeInt(replacedValue);
                    accessFile.writeLong(NULL_POINTER);
                }
            }

        } else {
            long position = startOfKeyValueBlock(nodeStart, entries.length);
            accessFile.seek(position);
            accessFile.writeLong(NULL_POINTER);
            accessFile.write(key.getBytes());
            String fill = filler(key.length());
            if (fill != null)
                accessFile.write(fill.getBytes());
            accessFile.writeInt(value);
            accessFile.writeLong(NULL_POINTER);
        }
    }


    private void appendOrderedIntoEmpty(long nodeStart,int index,String key,int value,long left,long right) throws IOException {
        long position = startOfKeyValueBlock(nodeStart, index);
        accessFile.seek(position);
        accessFile.writeLong(left);
        accessFile.write(key.getBytes());
        String fill = filler(key.length());
        if(fill != null){
            accessFile.write(fill.getBytes());
        }
        accessFile.writeInt(value);
        accessFile.writeLong(right);
    }

    private Entry[] readNodeIntoEntriesWithChildren(long nodeStart) throws IOException {
        //System.out.println("Reading Entire At Node Start:" + nodeStart);
        ArrayList<Entry> entries = new ArrayList<Entry>();
        //System.out.println("Given : " + nodeStart);
        Entry[] nodeEntries = allEntriesInNode(nodeStart);
        for (int i = 0; i < nodeEntries.length; i++) {
            long left = readChild(nodeStart, i, true);
            long right = readChild(nodeStart, i, false);
            Entry temp = new Entry(nodeEntries[i].getKey(), nodeEntries[i].getValue());
            temp.left = left;
            temp.right = right;
            entries.add(temp);
        }
        return entries.toArray(new Entry[entries.size()]);
    }

    private long readChild(long nodeStart, int childIndex,boolean left) throws IOException {
        long position = (left) ? startOfKeyValueBlock(nodeStart, childIndex) : startOfKeyValueBlock(nodeStart, childIndex) + totalEntryBlockSize();
        accessFile.seek(position);
        return accessFile.readLong();
    }

    private long startOfKeyValueBlock(long nodeStart,int index) {
        assert index >=0 && index <= (DEGREE - 1);
        if(index == 0) {
            return nodeStart;
        }else {
            return nodeStart + (index * totalEntryBlockSize());
        }
    }

    private Entry[] allEntriesInNode(long nodeStart) throws IOException {
        accessFile.seek(nodeStart);
        long position = accessFile.getFilePointer() + POINTER_SIZE;
        accessFile.seek(position);

        ArrayList<ByteBuffer> keysBuffers = new ArrayList<ByteBuffer>();
        ArrayList<ByteBuffer> valueBuffers = new ArrayList<ByteBuffer>();

        while (accessFile.getFilePointer() < totalNodeSize() + nodeStart) {
            FileChannel channel = accessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(KEY_SIZE);
            channel.read(buffer);
            buffer.flip();

            FileChannel channel1 = accessFile.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(VALUE_SIZE);
            channel1.read(buffer1);
            buffer1.flip();

            keysBuffers.add(buffer);
            valueBuffers.add(buffer1);
            accessFile.seek(accessFile.getFilePointer() + POINTER_SIZE);
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < keysBuffers.size(); i++) {
            ByteBuffer keyBuffer = keysBuffers.get(i);
            byte[] bytes = keyBuffer.array();
            String currentKey = new String(bytes);
            String striped = currentKey.replace("-", "");
            if (striped != null && !striped.isEmpty()) {
                int val = valueBuffers.get(i).getInt();
                entries.add(new Entry(striped, val));
            }
        }
        return entries.toArray(new Entry[entries.size()]);
    }

    private String filler(int length) {
        int leftOver = KEY_SIZE - length;
        if(leftOver > 0) {
            String fill = "";
            for (int i = 0; i < leftOver; i++) {
                fill += '-';
            }
            return fill;
        }else {
            return null;
        }
    }
    private void appendKeyValuePair(long pos, String key,int value) throws IOException {
        /*if(key.length() > KEY_SIZE) {
            key = key.substring(0, KEY_SIZE - 1);
        }*/
        accessFile.seek(pos);
        byte[] keyByteArray = key.getBytes();
        accessFile.write(keyByteArray);
        int numLeft = KEY_SIZE - key.length();
        if(numLeft > 0){
            String fill = "";
            for (int i = 0; i < numLeft; i++) {
                fill += '-';
            }
            accessFile.write(fill.getBytes());
        }
        accessFile.writeInt(value);
    }

    private void incrementValue(long nodeStart,int index) throws IOException {
        long position = startOfKeyValueBlock(nodeStart, index) + POINTER_SIZE + KEY_SIZE;
        accessFile.seek(position);
        /*FileChannel channel = accessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(VALUE_SIZE);
        channel.read(buffer);
        buffer.flip();
        int val = buffer.getInt();*/
        int val = accessFile.readInt();

        val += 1;
        accessFile.seek(position);
        accessFile.writeInt(val);
    }

    private long createNewEmptyNode(){
        try{
            long position = accessFile.length();
            accessFile.seek(position);
            int remain = totalNodeSize();
            String block = "";
            for (int i = 0; i < remain; i++) {
                block += '-';
            }
            accessFile.write(block.getBytes());
            return position;
        }catch (IOException e) {
            return -1;
        }
    }

    private void emptyANode(long nodeStart) throws IOException {
        accessFile.seek(nodeStart);
        String fill = "";
        for (int i = 0; i < totalNodeSize(); i++) {
            fill += '-';
        }
        accessFile.write(fill.getBytes());
    }

    public ArrayList<Pair<String,Integer>> toArrayList() throws IOException {
        Entry[] rootEntries = readNodeIntoEntriesWithChildren(0);
        ArrayList<Pair<String,Integer>> entries = new ArrayList<>();
        for (Entry rootEntry : rootEntries) {
            entries.add(new Pair<>(rootEntry.getKey(), rootEntry.getValue()));
        }

        if(!isLeafNode(0)){
            for (Entry entry : rootEntries) {
                toArrayList(entry.left,entries);
            }
            toArrayList(rootEntries[rootEntries.length-1].right,entries);
        }
        return (entries.size() > 0) ? entries : null;
    }

    private void toArrayList(long nodestart,ArrayList<Pair<String,Integer>> entries) throws IOException {
        if(isLeafNode(nodestart)){
            Entry[] nodeEntries = readNodeIntoEntriesWithChildren(nodestart);
            for (Entry nodeEntry : nodeEntries) {
                entries.add(new Pair<>(nodeEntry.getKey(), nodeEntry.getValue()));
            }
        }else {
            Entry[] nodeEntries = readNodeIntoEntriesWithChildren(nodestart);
            for (Entry nodeEntry : nodeEntries) {
                toArrayList(nodeEntry.left, entries);
                entries.add(new Pair<>(nodeEntry.getKey(), nodeEntry.getValue()));
            }
            Entry back = nodeEntries[nodeEntries.length - 1];
            toArrayList(back.right, entries);
        }
    }

    public int count() throws IOException {
        if (isLeafNode(0)) {
            int total = 0;
            Entry[] all = readNodeIntoEntriesWithChildren(0);
            for (Entry entry : all) {
                total += entry.getValue();
            }
            return total;
        }else {
            int counter = 0;
            Entry[] all = readNodeIntoEntriesWithChildren(0);
            //counter += all.length;
            for (Entry entry : all) {
                counter += entry.getValue();
                counter += count(entry.left);
            }
            Entry back = all[all.length - 1];
            counter += count(back.right);
            return counter;
        }
    }

    private int count(long nodestart) throws IOException {
        if(isLeafNode(nodestart)) {
            int total = 0;
            Entry[] all = readNodeIntoEntriesWithChildren(0);
            for (Entry entry : all) {
                total += entry.getValue();
            }
            return total;
        }else {
            Entry[] all = readNodeIntoEntriesWithChildren(nodestart);
            int counter = 0;
            //counter += all.length;
            for (Entry entry : all) {
                counter += entry.getValue();
                counter += count(entry.left);
            }
            Entry back = all[all.length - 1];
            counter += count(back.right);
            return counter;
        }
    }

    public int countDistinct() throws IOException {
        if (isLeafNode(0)) {
            return readNodeIntoEntriesWithChildren(0).length;
        }else {
            int counter = 0;
            Entry[] all = readNodeIntoEntriesWithChildren(0);
            counter += all.length;
            for (Entry entry : all) {
                counter += countDistinct(entry.left);
            }
            Entry back = all[all.length - 1];
            counter += countDistinct(back.right);
            return counter;
        }
    }

    private int countDistinct(long nodestart) throws IOException {
        if(isLeafNode(nodestart)) {
            return readNodeIntoEntriesWithChildren(nodestart).length;
        }else {
            Entry[] all = readNodeIntoEntriesWithChildren(nodestart);
            int counter = 0;
            counter += all.length;
            for (Entry entry : all) {
                counter += countDistinct(entry.left);
            }
            Entry back = all[all.length - 1];
            counter += countDistinct(back.right);
            return counter;
        }
    }

    public void printInOrder() throws IOException {
        Entry[] rootEntries = readNodeIntoEntriesWithChildren(0);
        if (isLeafNode(0)) {
            for (Entry rootEntry : rootEntries) {
                System.out.println(rootEntry.getKey());
            }
        }else {
            for (Entry rootEntry : rootEntries) {
                print(rootEntry.left);
                System.out.println(rootEntry.getKey());
            }
            Entry back = rootEntries[rootEntries.length - 1];
            print(back.right);
        }
    }

    private void print(long nodeStart) throws IOException {
        if(isLeafNode(nodeStart)) {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry entry : entries) {
                System.out.println(entry.getKey());
            }
        }else {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry rootEntry : entries) {
                print(rootEntry.left);
                System.out.println(rootEntry.getKey());
            }
            Entry back = entries[entries.length - 1];
            print(back.right);
        }
    }

    public int getValue(String key) throws IOException {
        if(key.length() > KEY_SIZE) {
            key = key.substring(0, KEY_SIZE - 1);
        }
        Entry[] entries = readNodeIntoEntriesWithChildren(0);
        if(isLeafNode(0)){
            for (Entry entry : entries) {
                if(entry.getKey().compareTo(key) == 0) {
                    return entry.getValue();
                }
            }
            return -1;
        }else {
            for (Entry entry : entries) {
                if(key.compareTo(entry.getKey()) == 0) {
                    return entry.getValue();
                }else if(key.compareTo(entry.getKey()) < 0) {
                    return getValue(key, entry.left);
                }
            }
            return getValue(key, entries[entries.length - 1].right);
        }
    }

    private int getValue(String key,long nodeStart) throws IOException {
        if(isLeafNode(nodeStart)) {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry entry : entries) {
                if(key.compareTo(entry.getKey()) == 0) {
                    return entry.getValue();
                }
            }
            return -1;
        }else {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry entry : entries) {
                if(key.compareTo(entry.getKey()) == 0) {
                    return entry.getValue();
                }else if(key.compareTo(entry.getKey()) < 0) {
                    return getValue(key, entry.left);
                }
            }
            return getValue(key, entries[entries.length - 1].right);
        }
    }

    private long getNodeForKey(String key) throws IOException {
        /*if(key.length() > KEY_SIZE) {
            key = key.substring(0, KEY_SIZE - 1);
        }*/
        Entry[] entries = readNodeIntoEntriesWithChildren(0);
        if(isLeafNode(0)){//root node is a leaf node
            for (Entry entry : entries) {
                if(entry.getKey().equals(key)) {
                    // return allEntriesInNode(0);
                    return 0;
                }
            }
            return NULL_POINTER;
        }else {//root node is not leaf node
            for (Entry entry : entries) {
                if(key.equals(entry.getKey())) {//found the key in the root
                    // return allEntriesInNode(0);
                    return 0;
                }else if(key.compareTo(entry.getKey()) < 0) {
                    return getNodeForKey(key, entry.left);
                }
            }
            return getNodeForKey(key, entries[entries.length - 1].right);
        }
    }

    private long getNodeForKey(String key,long nodeStart) throws IOException {
        if(isLeafNode(nodeStart)) {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry entry : entries) {
                if(key.equals(entry.getKey())) {
                    //return allEntriesInNode(nodeStart);
                    return nodeStart;
                }
            }
            return NULL_POINTER;
        }else {
            Entry[] entries = readNodeIntoEntriesWithChildren(nodeStart);
            for (Entry entry : entries) {
                if(key.equals(entry.getKey())) {
                    //   return allEntriesInNode(nodeStart);
                    return nodeStart;
                }else if(key.compareTo(entry.getKey()) < 0) {
                    return getNodeForKey(key, entry.left);
                }
            }
            return getNodeForKey(key, entries[entries.length - 1].right);
        }
    }

    public String getRafName() {
        return treeNumber + "_" + RANDOM_ACCESS_FILE_PRENAME + RANDOM_ACCESS_FILE_SUFFIX;
    }


    private int totalNodeSize() {
         return ((KEY_SIZE + VALUE_SIZE) * (DEGREE - 1)) + (POINTER_SIZE * DEGREE);
    }

    private int totalEntryBlockSize() {
        return KEY_SIZE + VALUE_SIZE + POINTER_SIZE;
    }

    public class Entry{
        private String key;
        private int value;

        public long left;
        public long right;

        private Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

}
