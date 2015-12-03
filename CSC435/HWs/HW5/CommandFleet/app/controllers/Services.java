package controllers;
import com.google.gson.*;
import models.*;
import play.api.Routes;
import play.db.ebean.Transactional;
import play.mvc.*;

import java.util.*;

import static play.mvc.Controller.response;
import static play.mvc.Results.ok;

public class Services {

    private static final String INFOTYPE_BASIC = "basic";
    private static final String INFOTYPE_BATTLES = "battles";
    private static final String INFOTYPE_FULLHISTORY = "history";
    private static final String INFOTYPE_COMMENTS = "comments";
    private static final String INFOTYPE_FRIENDS = "friends";

    private static final String METHODTYPE_USER = "user";
    private static final String METHODTYPE_LIKE = "like";

    private static final String SETTYPE_FRIEND = "friends";
    private static final String SETTYPE_COMMENT = "comments";
    private static final String SETTYPE_BACK = "back";
    private static final String SETTYPE_PROF = "prof";

    @Transactional
    public static Result doGet(String infoType, String method,String userName){
        if(method.equals(METHODTYPE_USER)) {
            return doForUser(infoType, userName);
        }else if(method.equals(METHODTYPE_LIKE)) {
            return doForLike(infoType, userName);
        }else {
            return ok(errorJson());
        }
    }

    @Transactional
    private static Result doForUser(String infoType, String userName){
        User user = User.getByUserName(userName);
        if(user != null){
            if(infoType.equals(INFOTYPE_BASIC)) {
                return ok(user.toJson());
            }else if(infoType.equals(INFOTYPE_BATTLES)) {
                List<Battle> battles = Battle.getBattlesForUser(user.getUsername());
                if(battles != null && battles.size() > 0) {
                    // return ok(new Gson().toJson(new HashMap<String,List<Battle>>().put("battles",battles)));
                    /*HashMap<String, List<Battle>> map = new HashMap<>();
                    map.put("battles", battles);
                    return ok(new Gson().toJson(map));*/

                    JsonObject object = new JsonObject();
                    JsonArray array = new JsonArray();
                    JsonParser parser = new JsonParser();
                    for (Battle battle : battles) {
                        array.add(parser.parse(battle.toJson()));
                    }
                    object.add("Comments", array);
                    response().setContentType("application/json");
                    return ok(object.toString());

                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_FULLHISTORY)) {
                String fh = Battle.fullHistory(user.getUsername());
                if(fh != null && !fh.isEmpty()) {
                    return ok(fh);
                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_COMMENTS)) {
                List<Comment> comments = Comment.getAllToUser(user.getUsername());

                if(comments != null && comments.size() > 0) {
                   /* HashMap<String, List<Comment>> map = new HashMap<>();
                    map.put("comments", comments);
                    String to = new Gson().toJson(map);
                    System.out.println(to);*/



                    JsonObject object = new JsonObject();
                    JsonArray array = new JsonArray();
                    JsonParser parser = new JsonParser();
                    for (Comment comment : comments) {
                       // System.out.println(comment.toJson());
                        array.add(parser.parse(comment.toJson()));
                    }
                    object.add("Comments", array);
                    response().setContentType("application/json");
                    return ok(object.toString());


                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_FRIENDS)) {
                List<Friend> friends = Friend.getFriendsForUser(user.getUsername());
                if(friends != null && friends.size() > 0) {
                    // return ok(new Gson().toJson(new HashMap<String,List<Friend>>().put("friends",friends)));
                    /*HashMap<String, List<Friend>> map = new HashMap<>();
                    map.put("friends", friends);
                    return ok(new Gson().toJson(map));*/

                    JsonObject object = new JsonObject();
                    JsonArray array = new JsonArray();
                    JsonParser parser = new JsonParser();
                    for (Friend friend : friends) {
                        array.add(parser.parse(friend.toJson()));
                    }
                    object.add("friends", array);
                    response().setContentType("application/json");
                    return ok(object.toString());
                }else {
                    return ok(errorJson());
                }
            }else {
                return ok(errorJson());
            }
        }else {
            return ok(errorJson());
        }
    }

    @Transactional
    private static Result doForLike(String infoType, String userName) {
        List<User> users = User.getLike(userName);
        if(users != null && users.size() > 0){
            if(infoType.equals(INFOTYPE_BASIC)) {
                //return ok(new Gson().toJson(new HashMap<String, List<User>>().put("users", users)));
                JsonArray array = new JsonArray();
                JsonParser parser = new JsonParser();
                for (User user : users) {
                    array.add(parser.parse(user.toJson()));
                }
                JsonObject object = new JsonObject();
                object.add("likes",array);
                response().setContentType("application/json");
                return ok(object.toString());
            }else if(infoType.equals(INFOTYPE_BATTLES)) {
                ArrayList<HashMap<String, List<Battle>>> allBattles = new ArrayList<>();
                for (User user : users) {
                    if(user != null) {
                        List<Battle> battles = Battle.getBattlesForUser(user.getUsername());
                        if(battles != null && battles.size() > 0) {
                            HashMap<String, List<Battle>> map = new HashMap<>();
                            map.put(user.getUsername(), battles);
                            allBattles.add(map);
                        }
                    }
                }
                if(allBattles.size() > 0) {
                    //return ok(new Gson().toJson(new HashMap<String,ArrayList<HashMap<String, List<Battle>>>>().put("Battles",allBattles)));

                    /*HashMap<String,ArrayList<HashMap<String, List<Battle>>>> map = new HashMap<>();
                    map.put("Battles",allBattles);
                    return ok(new Gson().toJson(map));*/

                    JsonObject object = new JsonObject();
                    JsonParser parser = new JsonParser();
                    JsonArray userBlocks = new JsonArray();
                    for (HashMap<String, List<Battle>> allBattle : allBattles) {
                        JsonObject userObject = null;
                        JsonArray userBattlesArray = new JsonArray();
                        String uk = null;
                        List<Battle> ub = null;
                        Set<Map.Entry<String, List<Battle>>> entries = allBattle.entrySet();
                        for (Map.Entry<String, List<Battle>> entry : entries) {
                            uk = entry.getKey();
                            ub = entry.getValue();
                        }

                        if(ub != null && ub.size() > 0){
                            for (Battle battle : ub) {
                                if(battle != null){
                                    String bS = battle.toJson();
                                    userBattlesArray.add(parser.parse(bS));
                                }
                            }
                        }
                        if(uk != null) {
                            userObject = new JsonObject();
                            userObject.add(uk, userBattlesArray);
                            userBlocks.add(userObject);
                        }
                    }

                    if(userBlocks.size() > 0 && userBlocks.size() > 0){
                        object.add("Battles",userBlocks);
                        response().setContentType("application/json");
                        return ok(object.toString());
                    }else {
                        return null;
                    }

                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_FULLHISTORY)) {
                ArrayList<HashMap<String, String>> allHistories = new ArrayList<>();
                for (User user : users) {
                    if(user != null) {
                        String fh = Battle.fullHistory(user.getUsername());
                        if(fh != null && !fh.isEmpty()){
                            HashMap<String, String> map = new HashMap<>();
                            map.put(user.getUsername(), fh);
                            allHistories.add(map);
                        }
                    }
                }
                if(allHistories.size() > 0) {
                    //return ok(new Gson().toJson(new HashMap<String,ArrayList<HashMap<String, String>>>().put("AllHistories",allHistories)));
                    HashMap<String,ArrayList<HashMap<String, String>>> map = new HashMap<>();
                    map.put("AllHistories",allHistories);
                    return ok(new Gson().toJson(map));
                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_COMMENTS)) {
                ArrayList<HashMap<String, List<Comment>>> allComments = new ArrayList<>();
                for (User user : users) {
                    if(user != null) {
                        List<Comment> comments = Comment.getAllToUser(user.getUsername());
                        if(comments != null && comments.size() > 0) {
                            HashMap<String, List<Comment>> map = new HashMap<>();
                            map.put(user.getUsername(), comments);
                            allComments.add(map);
                        }
                    }
                }
                if(allComments.size() > 0) {
                   // return ok(new Gson().toJson(new HashMap<String,ArrayList<HashMap<String, List<Comment>>>>().put("AllComments",allComments)));
                    HashMap<String,ArrayList<HashMap<String, List<Comment>>>> map = new HashMap<>();
                    map.put("AllComments",allComments);
                    return ok(new Gson().toJson(map));
                }else {
                    return ok(errorJson());
                }
            }else if(infoType.equals(INFOTYPE_FRIENDS)) {
                ArrayList<HashMap<String, List<Friend>>> allFriends = new ArrayList<>();
                for (User user : users) {
                    if(user != null) {
                        List<Friend> friends = Friend.getFriendsForUser(user.getUsername());
                        if(friends != null && friends.size() > 0) {
                            HashMap<String, List<Friend>> map = new HashMap<>();
                            map.put(user.getUsername(), friends);
                            allFriends.add(map);
                        }
                    }
                }
                if(allFriends.size() > 0) {
                    //return ok(new Gson().toJson(new HashMap<String,ArrayList<HashMap<String, List<Friend>>>>().put("AllFriends",allFriends)));
                    HashMap<String,ArrayList<HashMap<String, List<Friend>>>> map = new HashMap<>();
                    map.put("AllFriends",allFriends);
                    return ok(new Gson().toJson(map));
                }else {
                    return ok(errorJson());
                }
            }else {
                return ok(errorJson());
            }
        }else {
            return ok(errorJson());
        }
    }

    @Transactional
    public static Result doSet(String setType, String userName, String target,String item) {
        User user = User.getByUserName(userName);
        User targ = User.getByUserName(target);
        if(user != null && targ != null){
            if(setType.equals(SETTYPE_FRIEND)) {
                Friend friend = new Friend(user.getUsername(), targ.getUsername());
                friend.save();
                return ok(new Gson().toJson(new HashMap<String, String>().put("Action", "Alrighty, Let's roll")));
            }else if(setType.equals(SETTYPE_COMMENT)) {
                System.out.println("Here:" + errorJson());
                Comment comment = new Comment(user.getUsername(), targ.getUsername(), item);
                comment.save();
                System.out.println("posted:" + comment.toJson());
                response().setContentType("application/json");
                return ok(comment.toJson());
            }else {
                return ok(errorJson());
            }
        }else {
            if(user != null) {
                Customization customization = Customization.getForUser(user.getUsername());
                if(customization != null){
                    if(target.equals(SETTYPE_BACK)){
                        customization.setBackPic(item);
                        customization.update();
                    }else if(target.equals(SETTYPE_PROF)){
                        customization.setProfPic(item);
                        customization.update();
                    }
                }else {
                    customization = new Customization(user.getUsername(), (target.equals(SETTYPE_PROF) ? item : Customization.DEFAULT_PROF),
                            target.equals(SETTYPE_BACK) ? item : Customization.DEFAULT_BACK);
                    customization.save();
                }
                return ok((customization != null) ? customization.toJson() : errorJson());
            }else
                return ok(errorJson());
        }
    }

    public static Result testService(String pl){
       // response().setContentType("application/json");
        System.out.println("Invoked");
        return ok("This is the test bra :" + pl);
    }

    public static String errorJson() {
        HashMap<String,String> map = new HashMap<>();
        map.put("Error","Wrong input or empty");
        return new Gson().toJson(map);
    }

}
