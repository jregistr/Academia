package BtreeSuggestron;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageReader {

    private static PageReader instance;

    private static final String TEMPFILENAME = "temp.html";
    private long lastMod;

    public static PageReader getInstance(){
        if(instance == null){
            instance = new PageReader();
            return instance;
        }else {
            return instance;
        }
    }

    /*public String[] parsePage(String url) throws IOException {
        URL pageUrl = new URL(url);
        boolean success = siteToHtml(pageUrl);
        return  (success) ? htmlToArray() : null;
    }*/

    public boolean parsePage(String url) {
        lastMod = 0;
        try {
            URL pageUrl = new URL(url);
            Connection.Response doc = Jsoup.connect(url).execute();
            String dateString = doc.header("Last-Modified");

            if(dateString != null && !dateString.isEmpty()){
                SimpleDateFormat format;
                try{
                    format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                }catch (IllegalArgumentException e) {
                    format = null;
                }
                if(format != null) {
                    Date date;
                    try{
                        date  = format.parse(dateString);
                    } catch (ParseException e) {
                        date = null;
                    }
                    if(date != null) {
                        lastMod = date.getTime();
                        //System.out.println(lastMod);
                    }
                }
            }

            return siteToHtml(pageUrl);
        } catch (IOException e) {
            return false;
        }
    }

    public long lastModifiedAslong() {
        return lastMod;
    }

    public ArrayList<String> currentSiteToArray() {
        try{
            return htmlToArray();
        }catch (IOException e) {
            return null;
        }
    }

    public ArrayList<String> currentSiteLinksToArray()  {
        try{
            Document document = Jsoup.parse(new File(TEMPFILENAME),"UTF-8");
            Element element = document.getElementById("mw-content-text");

            if(element != null) {//the id exists. get links from within it.
                Elements aTags = element.getElementsByTag("a");
                return getLinksFromElements(aTags);
            }else {//id doesnt exist. get links from the page
                Elements elements = document.getElementsByTag("a");
                return getLinksFromElements(elements);
            }
            //return new ArrayList<String>();
        } catch (IOException e) {
            return null;
        }
    }

    private ArrayList<String> getLinksFromElements(Elements aTags){
        try{
            if(aTags != null && !aTags.isEmpty()) {
                ArrayList<String> links = new ArrayList<>();
                for (int i = 0; i < aTags.size(); i++) {
                    Element aTag = aTags.get(i);
                    if (aTag != null) {
                        if(aTag.hasAttr("href")) {
                            String href = aTag.attr("href");
                            //if(href.getBytes("UTF-8").length <= HashTable.KEY_SIZE && !(href.substring((href.length()-1)-4,href.length()).equals(".pdf"))){
                            if(href.getBytes("UTF-8").length <= HashTable.KEY_SIZE){
                                boolean proper = false;
                                try {
                                    URL url = new URL(href);
                                    proper = true;
                                }catch (MalformedURLException e) {
                                    proper = false;
                                }
                                if(proper){
                                    links.add(href);
                                }
                            }
                        }
                    }
                }
                return (links.size() > 0) ? links : null;
            }else
                return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

   /* private ArrayList<String> getLinksFromElement(Element element){
       try{
           if(element != null){

           }else
               return null;
       } catch (UnsupportedEncodingException e) {
           return null;
       }
    }*/

    private boolean siteToHtml(URL pageUrl) throws IOException {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) pageUrl.openConnection();
        } catch (IOException e) {
            System.out.println("Connection not established");
            return false;
        }
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        BufferedReader connectionStreamReader;
        try {
            connectionStreamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            System.out.println("Unable to get input stream");
            return false;
        }
        PrintWriter tempFileWriter;
        try {
            tempFileWriter = new PrintWriter(TEMPFILENAME,"UTF-8");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return false;
        }
        String nextSiteLine;
        while ((nextSiteLine = connectionStreamReader.readLine())!= null){
            tempFileWriter.write(nextSiteLine);
        }
        connectionStreamReader.close();
        tempFileWriter.close();
        return true;
    }

    private ArrayList<String> htmlToArray() throws IOException {
        File file = new File(TEMPFILENAME);
        String htmlTagsStripped = Jsoup.parse(file,"UTF-8").text();
        Pattern regex = Pattern.compile("([A-Za-z])+");

        Matcher matcher = regex.matcher(htmlTagsStripped);
        ArrayList<String> words = new ArrayList<String>();
        while (matcher.find()){
            String next = matcher.group();
            if(next != null && !next.isEmpty())
                words.add(next);
        }
        //return words.toArray(new String[words.size()]);

        return words;
    }

}
