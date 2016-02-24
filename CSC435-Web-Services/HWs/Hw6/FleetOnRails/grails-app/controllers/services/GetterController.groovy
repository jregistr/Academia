package services

import com.google.gson.Gson
import Models.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class GetterController {

    private static final String TYPE_LIKE = "like"
    private static final String TYPE_USER = "user"
    private static final String TYPE_BATTLES = "battles"
    private static final String TYPE_COMMENTS = "comments"
    private static final String TYPE_FRIENDS = "friends"
    private static final String TYPE_CUSTOMIZATION = "customization"


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
            render(text: user != null ? user.customization : errorJson("No such a user"),contentType: "application/json",encoding: "UTF-8")
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
        }else {
            render(text: errorJson("No such type of information exists"),contentType: "application/json",encoding: "UTF-8")
        }
    }

    private static String errorJson(String error){
        HashMap<String,String> map = new HashMap<>();
        map.put("Error",error);
        new Gson().toJson(map);
    }

}
