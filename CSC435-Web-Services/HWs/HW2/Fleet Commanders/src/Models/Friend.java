package Models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
        if(userID > 0 && friendOf > 0) {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryStirng = "INSERT INTO " + FRIENDS_TABLE_NAME + "(" + buildCommaSeperated(USER_ID_COLUMN, FRIEND_OF_COLUMN, DATE_ADDED_COLUMN) +
                        ") VALUES(?,?,?)";
                statement = connection.prepareStatement(queryStirng);
                statement.setInt(1, userID);
                statement.setInt(2, friendOf);
                statement.setTimestamp(3, dateAdded);
                System.out.println("Friends Prepared: " + statement.toString());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Failed to save");
                e.printStackTrace();
                return false;
            }finally {
                closeConnection(connection);
                closePreparedStatement(statement);
            }
        }else {
            return false;
        }
    }

    public static boolean save(Friend friend){
        if(friend.getUserID()> 0 && friend.getFriendOf() > 0) {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryStirng = "INSERT INTO " + FRIENDS_TABLE_NAME + "(" + buildCommaSeperated(USER_ID_COLUMN, FRIEND_OF_COLUMN, DATE_ADDED_COLUMN) +
                        ") VALUES(?,?,?)";
                statement = connection.prepareStatement(queryStirng);
                statement.setInt(1, friend.getUserID());
                statement.setInt(2, friend.getFriendOf());
                statement.setTimestamp(3, friend.getDateAdded());
                System.out.println("Friends Prepared: " + statement.toString());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Failed to save");
                e.printStackTrace();
                return false;
            }finally {
                closeConnection(connection);
                closePreparedStatement(statement);
            }
        }else {
            return false;
        }
    }

    public static boolean addFriend(int user,int friendOf){
        if(user > 0 && friendOf > 0) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet testUser = null;
            ResultSet testFriend = null;
            try {
                Friend testExistsAsFriend = hasFriend(user, friendOf);
                if (testExistsAsFriend == null) {
                    connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                    String queryString = "SELECT * FROM " + User.USERS_TABLE_NAME + " WHERE " + User.USER_ID_COLUMN + " = ?";
                    preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setInt(1, user);
                    testUser = preparedStatement.executeQuery();
                    boolean a1 = testUser != null;

                    preparedStatement.setInt(1, friendOf);

                    testFriend = preparedStatement.executeQuery();
                    boolean a2 = testFriend != null;
                    if (a1 && a2) {
                        Friend newFriend = new Friend(user, friendOf, new Timestamp(new java.util.Date().getTime()));
                        return newFriend.save();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Failed to add friend");
                e.printStackTrace();
                return false;
            }finally {
                /*closeConnection(connection);
                closePreparedStatement(preparedStatement);
                closeResultset(testUser);
                closeResultset(testFriend);*/
            }
        }else {
            return false;
        }
    }


    public static Friend[] retrieveAll(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            ArrayList<Friend> friends = new ArrayList<Friend>();
            String queryString = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE " + FRIEND_OF_COLUMN + " = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            if (resultSet != null) {
                while (resultSet.next()) {
                    int u = resultSet.getInt(USER_ID_COLUMN);
                    int fo = resultSet.getInt(FRIEND_OF_COLUMN);
                    Timestamp da = resultSet.getTimestamp(DATE_ADDED_COLUMN);
                    friends.add(new Friend(u, fo, da));
                }
            }
            return friends.toArray(new Friend[friends.size()]);
        } catch (SQLException e) {
            System.out.println("Failed to get all friends");
            e.printStackTrace();
            return null;
        }//finally {
//            closeConnection(connection);
//            closeStatement(statement);
//            closeResultset(resultSet);
//        }
    }

    public static String retrieveAllToJson(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            //ArrayList<Friend> friends = new ArrayList<Friend>();
            ArrayList<JsonObject> friends = new ArrayList<JsonObject>();
            String queryString = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE " + FRIEND_OF_COLUMN + " = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            if (resultSet != null) {
                while (resultSet.next()) {
                    int u = resultSet.getInt(USER_ID_COLUMN);
                    User user = User.retrieve(u);
                    String uName = (user != null) ? user.getUserName() : "null";
                    //int fo = resultSet.getInt(FRIEND_OF_COLUMN);
                    Timestamp da = resultSet.getTimestamp(DATE_ADDED_COLUMN);
                    //friends.add(new Friend(u, fo, da));
                    JsonObject object = new JsonObject();
                    object.addProperty(User.USER_ID_COLUMN,u);
                    object.addProperty(User.USER_NAME_COLUMN,uName);
                    object.addProperty(DATE_ADDED_COLUMN,da.toString());
                    friends.add(object);
                }
            }
            HashMap<String, JsonObject[]> map = new HashMap<String, JsonObject[]>();
            map.put(FRIENDS_TABLE_NAME, friends.toArray(new JsonObject[friends.size()]));
            return new Gson().toJson(map);
            //return friends.toArray(new Friend[friends.size()]);
        } catch (SQLException e) {
            System.out.println("Failed to get all friends");
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
        }
    }

    public static Friend hasFriend(int id, int friendID) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Friend friend = null;
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            String queryString = "SELECT * FROM " + FRIENDS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id + " AND " + FRIEND_OF_COLUMN + " = " + friendID;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
            if (resultSet != null && resultSet.next()) {
                int u = resultSet.getInt(USER_ID_COLUMN);
                int fo = resultSet.getInt(FRIEND_OF_COLUMN);
                Timestamp da = resultSet.getTimestamp(DATE_ADDED_COLUMN);
                friend = new Friend(u, fo, da);
            }
            return friend;
        } catch (SQLException e) {
            System.out.println("Failed to find friend");
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
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
