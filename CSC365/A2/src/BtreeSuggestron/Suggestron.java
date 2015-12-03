package BtreeSuggestron;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Suggestron {

    private static Suggestron instance;

    private int readFromFile;
    private int readFromCurrent;

    public static int NUMBER_OF_SITES_FROM_FILE = 31;
    public static int MAX_SITES_FROM_EACH_PAGES = 20;


    private Suggestron(){

    }

    public static Suggestron getInstance(){
        if(instance == null) {
            instance = new Suggestron();
        }
        return instance;
    }

    public boolean isInitialsed() {
        return HashTable.getInstance().isInitialised();
    }

    public boolean needsUpdate() {
        //return false;
        try {
            boolean needs = false;
            ArrayList<Pair<String, Long>> lastUpdates = HashTable.getInstance().getAllLastModified();
            if (lastUpdates != null && lastUpdates.size() > 0) {
                for (Pair<String, Long> lastUpdate : lastUpdates) {
                    if (PageReader.getInstance().parsePage(lastUpdate.getKey())) {
                        long last = PageReader.getInstance().lastModifiedAslong();
                        if(last != lastUpdate.getValue()) {
                            needs = true;
                        }
                    }
                }
            }
            return needs;
        } catch (IOException e) {
            return false;
        }
    }

    public void Initialiase() throws IOException {
        if(!isInitialsed()){
            doInit();
        }else {
            System.out.println("Already Initialised");
        }
    }

    public void DoUpdate(){
        try{
            ArrayList<Pair<String, Long>> lastUpdates = HashTable.getInstance().getAllLastModified();
            for (Pair<String, Long> lastUpdate : lastUpdates) {
                if (PageReader.getInstance().parsePage(lastUpdate.getKey())) {
                    long last = PageReader.getInstance().lastModifiedAslong();
                    if(last != lastUpdate.getValue()) {//update this one
                        ArrayList<String> siteWords = PageReader.getInstance().currentSiteToArray();
                        String treeName = HashTable.getInstance().get(lastUpdate.getKey());
                        BTree tree = new BTree(treeName);
                        siteWords.forEach(tree::put);
                        tree.close();
                        HashTable.getInstance().updateLastModified(lastUpdate.getKey(),last);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void doInit() throws IOException {//init the files and stuff for the program to work
        Path path = Paths.get("./websites.txt");
        ArrayList<String> fileSites = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach(fileSites::add);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to init: Couldn't get lines");
        }

        int number = 0;
        boolean madeDir =  new File(BTree.TREES_DIRECTORY_NAME).mkdir();

        if(madeDir){
            for (String fileSite : fileSites) {
                PageReader.getInstance().parsePage(fileSite);
                long lastMod = PageReader.getInstance().lastModifiedAslong();
                ArrayList<String> siteWords = PageReader.getInstance().currentSiteToArray();

                //
                ArrayList<String> siteWordsLower = new ArrayList<>();
                siteWords.forEach(x-> siteWordsLower.add(x.toLowerCase()));
                siteWords = siteWordsLower;
                //

                //consider making all the words lower case
                BTree tree = new BTree(number);
                siteWords.forEach(tree::put);
                String rafName = tree.getRafName();
                tree.close();
                tree = null;
                HashTable.getInstance().put(fileSite,rafName,lastMod);
                ArrayList<String> linksFromSite = PageReader.getInstance().currentSiteLinksToArray();

                if(linksFromSite != null && linksFromSite.size() > 0){
                   if(linksFromSite.size() <= 10) {
                       for (String s : linksFromSite) {
                           if (PageReader.getInstance().parsePage(s)) {
                               //System.out.println(s);
                               ArrayList<String> words = PageReader.getInstance().currentSiteToArray();
                               if (words != null && words.size() > 0) {
                                   number++;
                                   BTree temp = new BTree(number);
                                   System.out.println(s + "-------------------------------------- " + temp.getRafName());
                                   HashTable.getInstance().put(s, temp.getRafName(), PageReader.getInstance().lastModifiedAslong());
                                   words.forEach(temp::put);
                                   temp.close();
                               }
                           }
                       }
                   }else {
                       for (int i = 0; i < 10; i++) {
                           String link = linksFromSite.remove(new Random().nextInt(linksFromSite.size()));
                           if(PageReader.getInstance().parsePage(link)) {
                               //System.out.println(link);
                               ArrayList<String> words = PageReader.getInstance().currentSiteToArray();
                               if(words != null && words.size() > 0) {
                                   number++;
                                   BTree temp = new BTree(number);
                                   System.out.println(link + "-----------------------------------------------" + temp.getRafName());
                                   HashTable.getInstance().put(link, temp.getRafName(), PageReader.getInstance().lastModifiedAslong());
                                   words.forEach(temp::put);
                                   temp.close();
                               }
                           }
                       }
                   }
                }
                number++;
            }
        }else {
            System.out.println("Didnt make dir");
        }

    }

    public String[] getRecommendations(String urlString) throws IOException {
        String rafName = HashTable.getInstance().get(urlString);
        if(rafName == null) {
            if (PageReader.getInstance().parsePage(urlString)) {
                ArrayList<String> words = PageReader.getInstance().currentSiteToArray();

                //
                ArrayList<String> siteWordsLower = new ArrayList<>();
                words.forEach(x-> siteWordsLower.add(x.toLowerCase()));
                words = siteWordsLower;
                //

                //consider making all the words lowercase
                return getTopThree(getWordFrequencies(words));
            }else
                return null;
        }else {
            BTree tree = new BTree(rafName);
            ArrayList<Pair<String,Integer>> pairs = tree.toArrayList();
            tree.close();
            tree = null;
            return getTopThree(pairs);
        }
    }

    public ArrayList<Pair<String,Integer>> getWordFrequencies(ArrayList<String> words) {
        HashMap<String, Integer> pairs = new HashMap<>();
        words.forEach(s-> {
            Integer val = pairs.get(s);
            if(val == null) {
                pairs.put(s, 1);
            }else {
                pairs.put(s, val + 1);
            }
        });
        ArrayList<Pair<String,Integer>> pairArrayList = new ArrayList<>();
        //pairArrayList.get(0).
        //pairs.keySet().forEach(s->pairArrayList.add(new Pair<String,Integer>(s,pairs.get(s))));
        pairs.keySet().forEach(s->pairArrayList.add(new Pair<String,Integer>(s,pairs.get(s))));
        return pairArrayList;
    }


    private String[] getTopThree(ArrayList<Pair<String,Integer>> valuePairs) throws IOException {
        ArrayList<Pair<String, String>> siteToTreeName = HashTable.getInstance().getAllPairs();
        ArrayList<Pair<String, Double>> distances = new ArrayList<>();
        //siteToTreeName.forEach(x-> System.out.println(x.getKey() + "," + x.getValue()));

        for (Pair<String, String> stringPair : siteToTreeName) {
            //System.out.println(stringPair.getKey() + "-----------------" + stringPair.getValue());
            Pair<String, Double> pair = new Pair<>(stringPair.getKey(), getDistanceBetween(valuePairs, stringPair.getValue()));
            distances.add(pair);
        }

        ArrayList<String> sorted = new ArrayList<>();
        distances.stream().sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())).forEach(x->sorted.add(x.getKey()));
        return sorted.subList(0, 3).toArray(new String[3]);
    }

    private double getDistanceBetween(ArrayList<Pair<String,Integer>> one, String treeName) throws IOException {
     /*
     *//*   BTree tree = new BTree(treeName);
        float total = 0;
        for (Pair<String, Integer> pair : one) {
            int val = tree.getValue(pair.getKey());
            if(val > 0) {
                total += (val / pair.getValue()) * 100;
                total += (val / pair.getValue()) * 100;
            }
        }
        System.out.println(treeName + " " +  total/(tree.countDistinct()));
        return total/tree.countDistinct();*//*

        doTest(one, treeName);

        BTree tree = new BTree(treeName);
        ArrayList<Pair<String, Integer>> treeToArray = tree.toArrayList();
        tree.close();
        tree = null;

        float total = 0;
        for (Pair<String, Integer> pair : one) {
            Pair<String, Integer> inTree = null;
            for (Pair<String, Integer> stringIntegerPair : treeToArray) {
                if(stringIntegerPair.getKey().equals(pair.getKey())) {
                    inTree = stringIntegerPair;
                    break;
                }
            }
            if(inTree != null) {
                total += (((float)(inTree.getValue() * 1.10f) / (float)pair.getValue()) * 100) * 1;
            }
        }
        return total/treeToArray.size();
        */

        BTree tree = new BTree(treeName);
        ArrayList<Pair<String, Integer>> treeToArray = tree.toArrayList();
        tree.close();
        tree = null;

        float dotProduct = 0;
        float oneSquaredFreqs = 1;
        float treeSquaredFreqs = 1;

        for (Pair<String, Integer> pair : one) {
            Pair<String, Integer> inTree = null;
            for (Pair<String, Integer> stringIntegerPair : treeToArray) {
                if(stringIntegerPair.getKey().equals(pair.getKey())) {
                    inTree = stringIntegerPair;
                    break;
                }
            }
            if(inTree != null) {
                dotProduct += pair.getValue() * inTree.getValue();
                oneSquaredFreqs += Math.pow(pair.getValue(), 2);
                treeSquaredFreqs += Math.pow(inTree.getValue(), 2);
            }else {
                //oneSquaredFreqs += (Math.pow(pair.getValue(), 2));
            }
        }
        double div = dotProduct / (Math.sqrt(oneSquaredFreqs) * Math.sqrt(treeSquaredFreqs));
        return Math.acos(div);
    }

}




















