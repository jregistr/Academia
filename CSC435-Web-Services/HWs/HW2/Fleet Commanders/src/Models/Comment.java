package Models;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.*;

public class Comment extends Model {

    public static final String COMMENTS_TABLE_NAME = "comments";

    public static final String MSG_COLUMN = "msg";
    public static final String TIME_POSTED_COLUMN = "time_posted";
    public static final String POSTER_USER_COLUMN = "poster_user";
    public static final String POSTED_TO_USER_COLUMN = "posted_to_user";


    private String msg;
    private Timestamp timePosted;
    private int postedToUser;
    private int posterUser;


    public Comment(String msg, Timestamp timePosted, int postedToUser, int posterUser) {
        this.msg = msg;
        this.timePosted = timePosted;
        this.postedToUser = postedToUser;
        this.posterUser = posterUser;
    }

    public String getMsg() {
        return msg;
    }

    public int getPosterUser() {
        return posterUser;
    }

    public int getPostedToUser() {
        return postedToUser;
    }

    public Timestamp getTimePosted() {
        return timePosted;
    }

    public boolean save() {
        if(posterUser > 0 && postedToUser > 0){
            Connection connection = null;
            try{
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "INSERT INTO " + COMMENTS_TABLE_NAME + "(" + buildCommaSeperated(MSG_COLUMN,TIME_POSTED_COLUMN,POSTER_USER_COLUMN,POSTED_TO_USER_COLUMN)+
                        ") VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1,msg);
                preparedStatement.setTimestamp(2,timePosted);
                preparedStatement.setInt(3,postedToUser);
                preparedStatement.setInt(4, posterUser);
                System.out.println("Comment Query: " + preparedStatement.toString());
                preparedStatement.executeUpdate();
                return true;
            }catch (SQLException e) {
                System.out.println("Failure to save comment");
                e.printStackTrace();
                return false;
            }finally {
                if(connection != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return false;
        }
    }

    public static boolean save(Comment comment) {
        if(comment.getPosterUser() > 0 && comment.getPostedToUser() > 0){
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try{
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "INSERT INTO " + COMMENTS_TABLE_NAME + "(" + buildCommaSeperated(MSG_COLUMN,TIME_POSTED_COLUMN,POSTER_USER_COLUMN,POSTED_TO_USER_COLUMN)+
                        ") VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1,comment.getMsg());
                preparedStatement.setTimestamp(2,comment.getTimePosted());
                preparedStatement.setInt(3,comment.getPostedToUser());
                preparedStatement.setInt(4, comment.getPosterUser());
                System.out.println("Comment Query: " + preparedStatement.toString());
                preparedStatement.executeUpdate();
                return true;
            }catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally {
               if(connection != null){
                   try {
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return false;
        }
    }

    public static Comment[] retrieve(int id){
        if(id > 0){
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                ArrayList<Comment> comments = new ArrayList<Comment>();
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT * FROM " + COMMENTS_TABLE_NAME + " WHERE " + POSTED_TO_USER_COLUMN + " = " + id + " ORDER BY " + TIME_POSTED_COLUMN + " ASC";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
                if(resultSet != null){
                    while (resultSet.next()) {
                        String c = resultSet.getString(MSG_COLUMN);
                        Timestamp t = resultSet.getTimestamp(TIME_POSTED_COLUMN);
                        int pt = resultSet.getInt(POSTED_TO_USER_COLUMN);
                        int pu = resultSet.getInt(POSTER_USER_COLUMN);
                        comments.add(new Comment(c, t, pt, pu));
                    }
                }
                return comments.toArray(new Comment[comments.size()]);
            }catch (SQLException e) {
                System.out.println("Failed to get comments");
                e.printStackTrace();
                return null;
            }finally {
               if(connection != null){
                   try {
                       connection.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
                if(statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(resultSet != null){
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return null;
        }
    }

    public static String retrieveToJson(int id){
        if(id > 0){
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                //ArrayList<Comment> comments = new ArrayList<Comment>();
                ArrayList<JsonObject> comments = new ArrayList<JsonObject>();
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT * FROM " + COMMENTS_TABLE_NAME + " WHERE " + POSTED_TO_USER_COLUMN + " = " + id + " ORDER BY " + TIME_POSTED_COLUMN + " ASC";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        String c = resultSet.getString(MSG_COLUMN);
                        Timestamp t = resultSet.getTimestamp(TIME_POSTED_COLUMN);
                        int pt = resultSet.getInt(POSTED_TO_USER_COLUMN);
                        int pu = resultSet.getInt(POSTER_USER_COLUMN);
                        User ptU = User.retrieve(pt);
                        User puU = User.retrieve(pu);
                        //comments.add(new Comment(c, t, pt, pu));
                        JsonObject object = new JsonObject();
                        object.addProperty(MSG_COLUMN, c);
                        object.addProperty(TIME_POSTED_COLUMN, t.toString());
                        if(ptU != null)
                            object.addProperty(POSTED_TO_USER_COLUMN,ptU.getUserName());
                        else
                            object.addProperty(POSTED_TO_USER_COLUMN,pt);
                        if(puU != null)
                            object.addProperty(POSTER_USER_COLUMN, puU.getUserName());
                        else
                            object.addProperty(POSTER_USER_COLUMN, pu);
                        comments.add(object);
                    }
                }
               /* return (comments != null && comments.size() > 0) ?
                        new Gson().toJson(new HashMap<String,String[]>().put(COMMENTS_TABLE_NAME,comments.toArray(new String[comments.size()])))
                        : null;*/
                if(comments != null && comments.size() > 0) {
                    HashMap<String, JsonObject[]> map = new HashMap<String, JsonObject[]>();
                    map.put(COMMENTS_TABLE_NAME, comments.toArray(new JsonObject[comments.size()]));
                    return new Gson().toJson(map);
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("error", "nothing");
                    return new Gson().toJson(map);
                }
                //return comments.toArray(new Comment[comments.size()]);
            }catch (SQLException e) {
                System.out.println("Failed to get comments");
                e.printStackTrace();
                return null;
            }finally {
                if(connection != null){
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(resultSet != null){
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return null;
        }
    }

    public static Comment[] retrieveByUser(int id){
        if(id > 0){
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                ArrayList<Comment> comments = new ArrayList<Comment>();
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT * FROM " + COMMENTS_TABLE_NAME + " WHERE " + POSTER_USER_COLUMN + " = " + id + " ORDER BY " + TIME_POSTED_COLUMN + " ASC";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
                if(resultSet != null){
                    while (resultSet.next()) {
                        String c = resultSet.getString(MSG_COLUMN);
                        Timestamp t = resultSet.getTimestamp(TIME_POSTED_COLUMN);
                        int pt = resultSet.getInt(POSTED_TO_USER_COLUMN);
                        int pu = resultSet.getInt(POSTER_USER_COLUMN);
                        comments.add(new Comment(c, t, pt, pu));
                    }
                }
                return comments.toArray(new Comment[comments.size()]);
            }catch (SQLException e) {
                System.out.println("Failed to get comments");
                e.printStackTrace();
                return null;
            }finally {
                if(connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(statement != null){
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(resultSet != null){
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return null;
        }
    }

    public static Comment add(int poster,int postedTo,String s){
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        Comment comment = new Comment(s,now,postedTo,poster);
        comment.save();
        return comment;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", posterUser=" + posterUser +
                ", postedToUser=" + postedToUser +
                ", timePosted=" + timePosted +
                '}';
    }
}
