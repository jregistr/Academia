package models

class Site {

    String url
    String title
    ArrayList<String> links
    ArrayList<String> words
    ArrayList<AbstractMap.SimpleEntry<String,Integer>> freqList

    static transients = ['url','title','links','words','freqList']

    static constraints = {
    }

}
