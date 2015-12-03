package Models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;

public class User extends Model{

    public static final String USERS_TABLE_NAME = "users";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String USER_NAME_COLUMN = "user_name";
    public static final String DISPLAY_NAME_COLUMN = "display_name";
    public static final String PASSWORD_COLUMN = "password";


    private String displayName;
    private String userName;
    private String password;
    private int userID;

    private User(String dispName,String uName,String pass,int uID){
        displayName = dispName;
        userName = uName;
        password = pass;
        userID = uID;
    }

    public User (String dispName,String uName,String pass){
        displayName = dispName;
        userName = uName;
        password = pass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public boolean save() {
        if((displayName != null && !displayName.isEmpty()) && (userName != null && !getUserName().isEmpty()) && (getPassword() != null && !getPassword().isEmpty())) {
            Connection connection = null;
            Statement statement = null;
            ResultSet set = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                statement = connection.createStatement();
                String testQuery = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + getUserID();
                set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if (newEntry) {
                    String queryString = "INSERT INTO " + USERS_TABLE_NAME + "(" + buildCommaSeperated(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN, USER_ID_COLUMN) +
                            ") VALUES(?,?,?,?)";
                    //System.out.println(queryString);
                    preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, getDisplayName());
                    preparedStatement.setString(2, getUserName());
                    preparedStatement.setString(3, getPassword());
                    preparedStatement.setNull(4, Types.NULL);
                    //System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                } else {
                    String queryString = "UPDATE " + USERS_TABLE_NAME + " SET " + buildPreparedParameters(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN) +
                            " WHERE " + USER_ID_COLUMN + " = ?";
                    //System.out.println(queryString);
                    preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, getDisplayName());
                    preparedStatement.setString(2, getUserName());
                    preparedStatement.setString(3, getPassword());
                    preparedStatement.setInt(4, getUserID());
                   // System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                System.out.println("Failed to save user");
                e.printStackTrace();
                return false;
            }finally {
                closeConnection(connection);
                closeStatement(statement);
                closeResultset(set);
                closePreparedStatement(preparedStatement);
            }
        }else {
            System.out.println("One or more values are null or empty");
            return false;
        }
    }

    public static boolean save(User user) {
        if((user.getDisplayName() != null && !user.getDisplayName().isEmpty()) && (user.getUserName() != null && !user.getUserName().isEmpty()) && (user.getPassword() != null && !user.getPassword().isEmpty())) {
            Connection connection = null;
            Statement statement = null;
            ResultSet set = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                statement = connection.createStatement();
                String testQuery = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + user.getUserID();
                set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if (newEntry) {
                    String queryString = "INSERT INTO " + USERS_TABLE_NAME + "(" + buildCommaSeperated(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN, USER_ID_COLUMN) +
                            ") VALUES(?,?,?,?)";
                    //System.out.println(queryString);
                    preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, user.getDisplayName());
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setNull(4, Types.NULL);
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                } else {
                    String queryString = "UPDATE " + USERS_TABLE_NAME + " SET " + buildPreparedParameters(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN) +
                            " WHERE " + USER_ID_COLUMN + " = ?";
                    System.out.println(queryString);
                    preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, user.getDisplayName());
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setInt(4, user.getUserID());
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally {
                closeConnection(connection);
                closeStatement(statement);
                closeResultset(set);
                closePreparedStatement(preparedStatement);
            }
        }else {
            System.out.println("One or more values are null or empty");
            return false;
        }
    }

    public static User retrieve(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            User newUser = null;
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            statement = connection.createStatement();
            String userQueryString = "SELECT " + buildCommaSeperated(USER_NAME_COLUMN, PASSWORD_COLUMN, DISPLAY_NAME_COLUMN, USER_ID_COLUMN) + " FROM " + USERS_TABLE_NAME +
                    " WHERE " + USER_ID_COLUMN + " = " + id;
            //System.out.println(userQueryString);
            resultSet = statement.executeQuery(userQueryString);
            if (resultSet != null && resultSet.next()) {
                String u = resultSet.getString(USER_NAME_COLUMN);
                String p = resultSet.getString(PASSWORD_COLUMN);
                String d = resultSet.getString(DISPLAY_NAME_COLUMN);
                int i = resultSet.getInt(USER_ID_COLUMN);
                if ((u != null && !u.isEmpty()) && (p != null && !p.isEmpty()) && (d != null && !d.isEmpty()) && (i > 0)) {
                    newUser = new User(d, u, p, i);
                }
            }
            return newUser;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
        }
    }

    public static User retrieve(String uName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            User newUser = null;
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            statement = connection.createStatement();
            String userQueryString = "SELECT " + buildCommaSeperated(USER_NAME_COLUMN, PASSWORD_COLUMN, DISPLAY_NAME_COLUMN, USER_ID_COLUMN) + " FROM " + USERS_TABLE_NAME +
                    " WHERE " + USER_NAME_COLUMN + " = " + "\'" + uName + "\'";
            //String usersQueryString = "SELECT display_name, user_name, password, user_id FROM users  WHERE  user_name = " + "\'" + uName + "\'";
            resultSet = statement.executeQuery(userQueryString);
            if(resultSet != null && resultSet.next()){
                String u = resultSet.getString(USER_NAME_COLUMN);
                String p = resultSet.getString(PASSWORD_COLUMN);
                String d = resultSet.getString(DISPLAY_NAME_COLUMN);
                int i = resultSet.getInt(USER_ID_COLUMN);
                //System.out.println(String.format("USER:%s, PASS:%s, DISPLAY:%s,ID:%s",u,p,d,i));
                if((u != null && !u.isEmpty()) && (p != null && !p.isEmpty()) && (d != null && !d.isEmpty()) && (i > 0)){
                    newUser = new User(d,u,p,i);
                }
            }
            return newUser;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
        }
    }

    public static String retrieveToJson(String uName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //User newUser = null;
            String uToJ = null;
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            statement = connection.createStatement();
            String userQueryString = "SELECT " + buildCommaSeperated(USER_NAME_COLUMN, PASSWORD_COLUMN, DISPLAY_NAME_COLUMN, USER_ID_COLUMN) + " FROM " + USERS_TABLE_NAME +
                    " WHERE " + USER_NAME_COLUMN + " = " + "\'" + uName + "\'";
            //String usersQueryString = "SELECT display_name, user_name, password, user_id FROM users  WHERE  user_name = " + "\'" + uName + "\'";
            resultSet = statement.executeQuery(userQueryString);
            if (resultSet != null && resultSet.next()) {
                String u = resultSet.getString(USER_NAME_COLUMN);
                // String p = resultSet.getString(PASSWORD_COLUMN);
                String d = resultSet.getString(DISPLAY_NAME_COLUMN);
                int i = resultSet.getInt(USER_ID_COLUMN);
                //System.out.println(String.format("USER:%s, PASS:%s, DISPLAY:%s,ID:%s",u,p,d,i));
                if ((u != null && !u.isEmpty()) && (d != null && !d.isEmpty()) && (i > 0)) {
                    //newUser = new User(d,u,p,i);
                    JsonObject object = new JsonObject();
                    object.addProperty(USER_NAME_COLUMN, u);
                    object.addProperty(DISPLAY_NAME_COLUMN, d);
                    object.addProperty(USER_ID_COLUMN, i);
                    uToJ = object.toString();
                }
            }
            return uToJ;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
        }
    }

    public static String retrieveName(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String name = "";
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            statement = connection.createStatement();
            String queryString = "SELECT " + USER_NAME_COLUMN + " FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id;
            resultSet = statement.executeQuery(queryString);
            if (resultSet != null && resultSet.next()) {
                name = resultSet.getString(USER_NAME_COLUMN);
            }
            return name;
        } catch (SQLException e) {
            System.out.println("Failed to get name");
            e.printStackTrace();
            return null;
        }finally {
            closeConnection(connection);
            closeStatement(statement);
            closeResultset(resultSet);
        }
    }

    public static String[] retrieveNamesLike(String like) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ArrayList<String> names = new ArrayList<String>();
            connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            String queryString = "SELECT " + USER_NAME_COLUMN + " FROM " + USERS_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + " LIKE ?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, "%" + like + "%");
            System.out.println("Like Statement: " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    names.add(resultSet.getString(USER_NAME_COLUMN));
                }
            }
            return (names.size() > 0) ? names.toArray(new String[names.size()]) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting like");
            return null;
        }finally {
            closeConnection(connection);
            closePreparedStatement(preparedStatement);
            closeResultset(resultSet);
        }
    }

    public String toJson(){
        JsonObject object = new JsonObject();
        object.addProperty(USER_ID_COLUMN,userID);
        object.addProperty(USER_NAME_COLUMN,userName);
        object.addProperty(DISPLAY_NAME_COLUMN,displayName);
        return object.toString();
    }

    @Override
    public String toString(){
        return String.format("%s:%s,%s:%s,%s:%s,%s:%s",DISPLAY_NAME_COLUMN,displayName,USER_NAME_COLUMN,userName,PASSWORD_COLUMN,password,USER_ID_COLUMN,userID);
    }

}
