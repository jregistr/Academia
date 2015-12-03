package Suggestron;

import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageReader {

    private static PageReader instance;

    private static final String TEMPFILENAME = "temp.html";

   // private URL pageUrl;

    /*public PageReader(String url){
        try {
            pageUrl = new URL(url);
        }catch (MalformedURLException ex){
            pageUrl = null;
            System.out.println("Badly formatted detected");
        }
    }*/

    public static PageReader getInstance(){
        if(instance == null){
            instance = new PageReader();
            return instance;
        }else {
            return instance;
        }
    }

    /*public String[] parsePage(String url) throws IOException {
        if(pageUrl != null){
            siteToHtml();
            if(!failedToCreateFile)
                return htmlToArray();
            else
                return null;
        }else {
            return null;
        }
    }*/

    public String[] parsePage(String url) throws IOException {
        URL pageUrl = new URL(url);
        boolean success = siteToHtml(pageUrl);
        return  (success) ? htmlToArray() : null;
    }



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
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    private String[] htmlToArray() throws IOException {
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
        return words.toArray(new String[words.size()]);
    }

}
















