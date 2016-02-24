package Models;


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
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
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
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean save(Comment comment) {
        if(comment.getPosterUser() > 0 && comment.getPostedToUser() > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "INSERT INTO " + COMMENTS_TABLE_NAME + "(" + buildCommaSeperated(MSG_COLUMN,TIME_POSTED_COLUMN,POSTER_USER_COLUMN,POSTED_TO_USER_COLUMN)+
                        ") VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(queryString);
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
            }
        }else {
            return false;
        }
    }

    public static Comment[] retrieve(int id){
        if(id > 0){
            try {
                ArrayList<Comment> comments = new ArrayList<Comment>();
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT * FROM " + COMMENTS_TABLE_NAME + " WHERE " + POSTED_TO_USER_COLUMN + " = " + id + " ORDER BY " + TIME_POSTED_COLUMN + " ASC";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryString);
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
            }
        }else {
            return null;
        }
    }

    public static Comment[] retrieveByUser(int id){
        if(id > 0){
            try {
                ArrayList<Comment> comments = new ArrayList<Comment>();
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT * FROM " + COMMENTS_TABLE_NAME + " WHERE " + POSTER_USER_COLUMN + " = " + id + " ORDER BY " + TIME_POSTED_COLUMN + " ASC";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryString);
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
            }
        }else {
            return null;
        }
    }

    public static void add(int poster,int postedTo,String s){
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        Comment comment = new Comment(s,now,postedTo,poster);
        comment.save();
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
