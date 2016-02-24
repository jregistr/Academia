package Suggestron;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

//this class is responsible for making hashtables out of the base maintained sites to be used for comparison and determinine similarity given a website
public class Comparetron {
    private static Comparetron instance;
    //private HashtableURLPair[] bases;
    //private Pair<Hashtable,String>[] bases;
    private ArrayList<Pair<Hashtable,String>> bases;

    private static final String REFERENCE_FILE_NAME ="websites.txt";

    private Comparetron (){

    }

    public static Comparetron getInstance(){
        if(instance == null){
            instance = new Comparetron();
            return instance;
        }else {
            return instance;
        }
    }

    public void setBases() throws IOException {
        if(bases == null){
            File sites = new File(REFERENCE_FILE_NAME);
            Scanner scanner = new Scanner(sites);
            ArrayList<String> urlStrings = new ArrayList<String>();
            while (scanner.hasNextLine()){
                urlStrings.add(scanner.nextLine());
            }
            bases = new ArrayList<Pair<Hashtable, String>>();
            for (String currentUrl : urlStrings) {
                String[] words = PageReader.getInstance().parsePage(currentUrl);
                bases.add(new Pair<Hashtable, String>(wordsToTable(words),currentUrl));
            }
        }
    }

    private Hashtable wordsToTable(String[] words){
        Hashtable temp = new Hashtable();
        for (String word : words) {
            temp.add(word);
        }
        return temp;
    }

    public String getMostSimilar(String urlString) throws IOException {
        ArrayList<Pair<String,Double>> similarities = new ArrayList<Pair<String, Double>>();
        int mostSimilarIndex = 0;
        double mostSimilarity = 0;
        Hashtable other = wordsToTable(PageReader.getInstance().parsePage(urlString));
        int index = -1;
        for (Pair<Hashtable, String> base : bases) {
            Pair<String,Double> sim = new Pair<String, Double>(base.getObjectTwo(),similarity(base.getObjectOne(),other));
            similarities.add(sim);
            index++;
            if(sim.getObjectTwo() > mostSimilarity){
                mostSimilarity = sim.getObjectTwo();
                mostSimilarIndex = index;
            }
        }
        Pair<String,Double> most = similarities.get(mostSimilarIndex);
        return most.getObjectOne();
    }

    private Double similarity(Hashtable base, Hashtable other){
        String[] baseWords = base.getAllKeys();
        double sim = 0;
        for (String baseWord : baseWords) {
            sim += (other.occurences(baseWord) > 0) ? (base.occurences(baseWord)/other.occurences(baseWord)) * 100 : 0;
        }
        return sim/base.getDistinctlements();
    }

    private class Pair<T,V>{
        private T objectOne;
        private V objectTwo;

        public Pair(T ob1,V ob2){
            objectOne = ob1;
            objectTwo = ob2;
        }

        public T getObjectOne(){
            return objectOne;
        }

        public V getObjectTwo(){
            return objectTwo;
        }
    }

}



























