package Models;

import java.sql.*;
import java.util.*;

public class Friend  extends Model{

    public static final String FRIENDS_TABLE_NAME = "friends";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String FRIEND_OF_COLUMN = "friend_of";
    public static final String DATE_ADDED_COLUMN = "date_added";

    private int userID;
    private int friendOf;
    private Timestamp dateAdded;

    public Friend(int userID, int friendOf, Timestamp dateAdded) {
        this.userID = userID;
        this.friendOf = friendOf;
        this.dateAdded = dateAdded;
    }

    public int getUserID() {
        return userID;
    }

    public int getFriendOf() {
        return friendOf;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }


    @Override
    public boolean save() {
        if(userID > 0 && friendOf > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryStirng = "INSERT INTO " + FRIENDS_TABLE_NAME + "(" + buildCommaSeperated(USER_ID_COLUMN,FRIEND_OF_COLUMN,DATE_ADDED_COLUMN) +
                        ") VALUES(?,?,?)";
                PreparedStatement statement = connection.prepareStatement(queryStirng);
                statement.setInt(1,userID);
                statement.setInt(2,friendOf);
                statement.setTimestamp(3,dateAdded);
                System.out.println("Friends Prepared: " + statement.toString());
                statement.executeUpdate();
                return true;
            }catch (SQLException e){
                System.out.println("Failed to save");
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean save(Friend friend){
        if(friend.getUserID()> 0 && friend.getFriendOf() > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryStirng = "INSERT INTO " + FRIENDS_TABLE_NAME + "(" + buildCommaSeperated(USER_ID_COLUMN,FRIEND_OF_COLUMN,DATE_ADDED_COLUMN) +
                        ") VALUES(?,?,?)";
                PreparedStatement statement = connection.prepareStatement(queryStirng);
                statement.setInt(1,friend.getUserID());
                statement.setInt(2,friend.getFriendOf());
                statement.setTimestamp(3,friend.getDateAdded());
                System.out.println("Friends Prepared: " + statement.toString());
                statement.executeUpdate();
                return true;
            }catch (SQLException e){
                System.out.println("Failed to save");
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean addFriend(int user,int friendOf){
        if(user > 0 && friendOf > 0){
            try {
                Friend testExistsAsFriend = retrieve(user, friendOf);
                if(testExistsAsFriend == null){
                    Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                    String queryString = "SELECT * FROM " + User.USERS_TABLE_NAME + " WHERE " + User.USER_ID_COLUMN + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setInt(1,user);
                    ResultSet testUser = preparedStatement.executeQuery();
                    preparedStatement.setInt(1,friendOf);
                    ResultSet testFriend = preparedStatement.executeQuery();
                    if(testUser != null && testUser.next() && testFriend != null && testFriend.next()) {
                        Friend newFriend = new Friend(user, friendOf, new Timestamp(new java.util.Date().getTime()));
                        return newFriend.save();
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
            }catch (SQLException e){
                System.out.println("Failed to add friend");
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public static Friend[] retrieveAll(int id){
        try {
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            ArrayList<Friend> friends = new ArrayList<Friend>();
            String queryString = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE " + FRIEND_OF_COLUMN + " = " + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            if(resultSet != null){
                while (resultSet.next()){
                    int u = resultSet.getInt(USER_ID_COLUMN);
                    int fo = resultSet.getInt(FRIEND_OF_COLUMN);
                    Timestamp da = resultSet.getTimestamp(DATE_ADDED_COLUMN);
                    friends.add(new Friend(u,fo,da));
                }
            }
            return friends.toArray(new Friend[friends.size()]);
        }catch (SQLException e){
            System.out.println("Failed to get all friends");
            e.printStackTrace();
            return null;
        }
    }

    public static Friend retrieve(int id, int friendID){
        try {
            Friend friend = null;
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            String queryString = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id + " AND " + FRIEND_OF_COLUMN + " = " + friendID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);
            if(resultSet != null && resultSet.next()){
                int u = resultSet.getInt(USER_ID_COLUMN);
                int fo = resultSet.getInt(FRIEND_OF_COLUMN);
                Timestamp da = resultSet.getTimestamp(DATE_ADDED_COLUMN);
                friend = new Friend(u,fo,da);
            }
            return friend;
        }catch (SQLException e){
            System.out.println("Failed to find friend");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Friend{" +
                "userID=" + userID +
                ", friendOf=" + friendOf +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
