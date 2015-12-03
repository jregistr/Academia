package services

import com.google.gson.Gson
import Models.*
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class GetterController {

    private static final String TYPE_LIKE = "like"
    private static final String TYPE_USER = "user"
    private static final String TYPE_BATTLES = "battles"
    private static final String TYPE_COMMENTS = "comments"
    private static final String TYPE_FRIENDS = "friends"
    private static final String TYPE_CUSTOMIZATION = "customization"
    private static final String TYPE_IMAGE = "images";
    private static final String TYPE_DEFAULTS = "defaults";


    private static final String SEARCH_METHOD_FLIKR = "flickr.photos.search";
    private static final String GET_SIZES_METHOD_FLIKR = "flickr.photos.getSizes";

    private static final String API_KEY = "b075f9401d775a97e225f22780e648d6";
    private static final String BASE_URI = "https://api.flickr.com/services/rest/?method=";


    def index(String type,String target) {
        if(type.equals(TYPE_LIKE)){
            def users = User.findAllByUsernameLike("%" + target + "%")
            if(users != null && users.size() > 0){
                def array = new JsonArray()
                users.each {x->
                    if(x != null)
                        array.add(x.toJsonObject())
                }
                def object = new JsonObject()
                object.add("users",array)
                render(text: object.toString(),contentType: "application/json",encoding: "UTF-8")
            }else {
                render(text: errorJson("No such users"),contentType: "application/json",encoding: "UTF-8")
            }
        }else if(type.equals(TYPE_USER)){
            User user = User.findByUsername(target)
            render(text: user != null ? user.toJson() : errorJson("No such user"),contentType: "application/json",encoding: "UTF-8")
        }else if(type.equals(TYPE_COMMENTS)){
            User user = User.findByUsername(target)
            if(user != null){
                def comments = user.comments
                if(comments != null && comments.size() > 0){
                    def array = new JsonArray()
                    comments.each {x->
                        array.add(x.toJsonObject())
                    }
                    def obj = new JsonObject()
                    obj.add("comments",array)
                    render(text: obj.toString(),contentType: "application/json",encoding: "UTF-8")
                }else
                    render(text: errorJson("No comments for:" + user.getUsername()),contentType: "application/json",encoding: "UTF-8")
            }else {
                render(text: errorJson("No such user"),contentType: "application/json",encoding: "UTF-8")
            }
        }else if(type.equals(TYPE_FRIENDS)){
            User user = User.findByUsername(target)
            if(user != null){
                def friends = user.friends
               // return friends != null && friends.size() > 0 ? friends : errorJson()
                if(friends != null && friends.size() > 0){
                    def array = new JsonArray()
                    friends.each {x->
                        array.add(x.toJsonObject())
                    }
                    def obj = new JsonObject()
                    obj.add("friends",array)
                    render(text: obj.toString(),contentType: "application/json",encoding: "UTF-8")
                }else {
                    render(text: errorJson("No friends for:" + user.getUsername()),contentType: "application/json",encoding: "UTF-8")
                }
            }else {
                render(text: errorJson("No such user"),contentType: "application/json",encoding: "UTF-8")
            }
        }else if(type.equals(TYPE_CUSTOMIZATION)){
            User user = User.findByUsername(target)
            render(text: user != null ? user.customization.toJson() : errorJson("No such a user"),contentType: "application/json",encoding: "UTF-8")
        }else if(type.equals(TYPE_BATTLES)){
            User user = User.findByUsername(target)
            if(user != null){
                def reds = user.redBattles
                def blues = user.blueBattles
                def array = new JsonArray()
                boolean empty = true
                if(reds != null && reds.size() > 0){
                    empty = false
                    reds.each {x->
                        array.add(x.toJsonObject())
                    }
                }
                if(blues != null && blues.size() > 0){
                    empty = false
                    blues.each {x->
                        array.add(x.toJsonObject())
                    }
                }
                if(!empty){
                    def object = new JsonObject()
                    object.add("battles",array)
                    render(text: object.toString(),contentType: "application/json",encoding: "UTF-8")
                }else
                    render(text: errorJson("No battles for:" + user.getUsername()),contentType: "application/json",encoding: "UTF-8")

            }else {
                render(text: errorJson("No such user"),contentType: "application/json",encoding: "UTF-8")
            }
        }else if(type.equals(TYPE_IMAGE)){
            def list = getImageList(target)
            if(list != null && list.size() > 0){
                render(text: new Gson().toJson(list),contentType: "application/json",encoding: "UTF-8")
            }else {
                render(text: errorJson("No images found or something went wrong"),contentType: "application/json",encoding: "UTF-8")
            }
        }else if(type.equals(TYPE_DEFAULTS)){
            render(text: new Gson().toJson(Customization.PROFILE_IMAGES + Customization.BACKGROUND_IMAGES),contentType: "application/json",encoding: "UTF-8")
        }else {
            render(text: errorJson("No such type of information exists"),contentType: "application/json",encoding: "UTF-8")
        }
    }

    private static String errorJson(String error){
        HashMap<String,String> map = new HashMap<>();
        map.put("Error",error);
        new Gson().toJson(map);
    }

    private static ArrayList<String> getImageList(String searchText){
        if(searchText != null && !searchText.isEmpty()){
            String uri = BASE_URI + SEARCH_METHOD_FLIKR + "&api_key=" + API_KEY + "&text=" + searchText + "&format=json";
            HttpURLConnection connection = getConnection(uri);
            if(connection != null){
                BufferedReader connectionStreamReader = getConnectionStreamReader(connection);
                if(connectionStreamReader != null){
                    String conString;
                    String complete = "";
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
                                return imageUrls
                            }else {
                                return null
                            }
                        }else
                            return null
                    }else {
                        return null
                    }
                }else {
                    return null
                }
            }else
                return null
        }else {
            return null
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
