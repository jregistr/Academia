package controllers;

import com.google.gson.*;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static play.mvc.Controller.response;
import static play.mvc.Results.ok;

public class Flikr {

    private static final String METHOD_SEARCH_PARAMETER = "search";

    private static final String SEARCH_METHOD_FLIKR = "flickr.photos.search";
    private static final String GET_SIZES_METHOD_FLIKR = "flickr.photos.getSizes";

    private static final String API_KEY = "b075f9401d775a97e225f22780e648d6";
    private static final String BASE_URI = "https://api.flickr.com/services/rest/?method=";


    public static Result searchForImage(String searchText) throws IOException {
        //String searchText = request.getParameter(METHOD_SEARCH_PARAMETER);
        if(searchText != null && !searchText.isEmpty()){
            String uri = BASE_URI + SEARCH_METHOD_FLIKR + "&api_key=" + API_KEY + "&text=" + searchText + "&format=json";
            HttpURLConnection connection = getConnection(uri);
            if(connection != null){
                /*connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");*/
                BufferedReader connectionStreamReader = getConnectionStreamReader(connection);
                if(connectionStreamReader != null){
                    String conString;
                    String complete = "";
                   // PrintWriter writer = response.getWriter();
                    while ((conString = connectionStreamReader.readLine()) != null){
                        complete += conString;
                    }
                    int firstCurly = complete.indexOf("{");
                    if(firstCurly > 0){
                        complete = complete.substring(firstCurly,complete.length()-1);
                        JsonObject object = new JsonParser().parse(complete).getAsJsonObject();
                        if(object != null) {
                            JsonElement[] ids = getIds(object);
                            ArrayList<String> imageUrls = new ArrayList<String>();
                            for (JsonElement id : ids) {
                                String imgUrl = getImage(id);
                                if(imgUrl != null)
                                    imageUrls.add(imgUrl);
                            }
                            if(imageUrls.size() > 0) {
                                HashMap<String, String[]> map = new HashMap<String, String[]>();
                                map.put("images", imageUrls.toArray(new String[imageUrls.size()]));
                                //response.setContentType("application/json");
                                //writer.write(new Gson().toJson(map));
                                response().setContentType("application/json");
                                return ok(new Gson().toJson(map));
                            }else {
                                return ok(Services.errorJson());
                            }
                        }else
                            return ok(Services.errorJson());
                    }else {
                        return ok(Services.errorJson());
                    }
                }else {
                    return ok(Services.errorJson());
                }
            }else
                return ok(Services.errorJson());
        }else {
            return ok(Services.errorJson());
        }
    }


    private static JsonElement[] getIds(JsonObject flikrObj){
        JsonObject photosObj = flikrObj.getAsJsonObject("photos");
        if(photosObj != null){
            JsonArray photosArray = photosObj.getAsJsonArray("photo");
            if(photosArray != null){
                ArrayList<JsonElement> ids = new ArrayList<JsonElement>();
                int count = 0;
                int max = 32;
                for (JsonElement jsonElement : photosArray) {
                    if(count >= max)
                        break;
                    if(jsonElement != null){
                        //writer.write(jsonElement.toString());
                        JsonObject photo = jsonElement.getAsJsonObject();
                        ids.add(photo.get("id"));
                        count++;
                    }
                }

                return  ids.toArray(new JsonElement[ids.size()]);
            }else
                return null;
        }else
            return null;
    }

    private static String getImage(JsonElement id) {
        String uri = BASE_URI + GET_SIZES_METHOD_FLIKR + "&api_key=" + API_KEY + "&photo_id=" + id.getAsString() + "&format=json";
        System.out.println("Get Sizes: " + uri);
        HttpURLConnection connection = getConnection(uri);
        if(connection != null){
            BufferedReader connectionStreamReader = getConnectionStreamReader(connection);
            if(connectionStreamReader != null){
                String conString = "";
                String complete = "";
                while (conString != null){
                    try {
                        conString = connectionStreamReader.readLine();
                    } catch (IOException e) {
                        conString = null;
                    }
                    if(conString != null && !conString.isEmpty())
                        complete += conString;
                }
                int firstCurly = complete.indexOf("{");
                if(firstCurly > 0){
                    complete = complete.substring(firstCurly,complete.length()-1);
                    JsonObject object = new JsonParser().parse(complete).getAsJsonObject();
                    if(object != null) {
                        JsonObject sizes = object.getAsJsonObject("sizes");
                        if(sizes != null) {
                            JsonArray sizeArray = sizes.getAsJsonArray("size");
                            if(sizeArray != null) {
                                String source = null;
                                int largest = 0;
                                for (JsonElement element : sizeArray) {
                                    JsonObject size = element.getAsJsonObject();
                                    int width = size.get("width").getAsInt();
                                    int height = size.get("height").getAsInt();
                                    int wxh = width * height;
                                    String curSource = size.get("source").getAsString();
                                    if(curSource != null && wxh > largest && wxh <= (960*540)) {
                                        largest = wxh;
                                        source = curSource;
                                    }
                                }
                                return (source != null && !source.isEmpty()) ? source : null;
                            }else
                                return null;
                        }else
                            return null;
                    }else
                        return null;
                }else
                    return null;
            }else
                return null;
        }else {
            return null;
        }
    }

    private static HttpURLConnection getConnection(String uri) {
        URL url;
        try {
            url =new URL(uri);
        } catch (MalformedURLException e) {
            url = null;
            System.out.println("Bad url");
        }
        if(url != null){
            HttpURLConnection connection;
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
            }catch (IOException e){
                connection = null;
            }
            return connection;
        }else
            return null;
    }

    private static BufferedReader getConnectionStreamReader(HttpURLConnection connection){
        if(connection != null){
            BufferedReader connectionStreamReader;
            try{
                connectionStreamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }catch (IOException e){
                connectionStreamReader = null;
            }
            return connectionStreamReader;
        }else
            return null;
    }
}
