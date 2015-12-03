package Models;

import java.sql.*;

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
        if((displayName != null && !displayName.isEmpty()) && (userName != null && !getUserName().isEmpty()) && (getPassword() != null && !getPassword().isEmpty())){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                String testQuery = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + getUserID();
                ResultSet set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if(newEntry) {
                    String queryString = "INSERT INTO " + USERS_TABLE_NAME + "(" + buildCommaSeperated(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN, USER_ID_COLUMN) +
                            ") VALUES(?,?,?,?)";
                    //System.out.println(queryString);
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1,getDisplayName());
                    preparedStatement.setString(2,getUserName());
                    preparedStatement.setString(3, getPassword());
                    preparedStatement.setNull(4,Types.NULL);
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }else {
                    String queryString = "UPDATE " + USERS_TABLE_NAME + " SET " + buildPreparedParameters(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN) +
                            " WHERE " + USER_ID_COLUMN + " = ?";
                    System.out.println(queryString);
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, getDisplayName());
                    preparedStatement.setString(2, getUserName());
                    preparedStatement.setString(3, getPassword());
                    preparedStatement.setInt(4, getUserID());
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("One or more values are null or empty");
            return false;
        }
    }

    public static boolean save(User user) {
        if((user.getDisplayName() != null && !user.getDisplayName().isEmpty()) && (user.getUserName() != null && !user.getUserName().isEmpty()) && (user.getPassword() != null && !user.getPassword().isEmpty())){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                String testQuery = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + user.getUserID();
                ResultSet set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if(newEntry) {
                    String queryString = "INSERT INTO " + USERS_TABLE_NAME + "(" + buildCommaSeperated(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN, USER_ID_COLUMN) +
                            ") VALUES(?,?,?,?)";
                    //System.out.println(queryString);
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1,user.getDisplayName());
                    preparedStatement.setString(2,user.getUserName());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setNull(4,Types.NULL);
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }else {
                    String queryString = "UPDATE " + USERS_TABLE_NAME + " SET " + buildPreparedParameters(DISPLAY_NAME_COLUMN, USER_NAME_COLUMN, PASSWORD_COLUMN) +
                            " WHERE " + USER_ID_COLUMN + " = ?";
                    System.out.println(queryString);
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setString(1, user.getDisplayName());
                    preparedStatement.setString(2, user.getUserName());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setInt(4,user.getUserID());
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("One or more values are null or empty");
            return false;
        }
    }

    public static User retrieve(int id)  {
        try{
            User newUser = null;
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            String userQueryString = "SELECT " + buildCommaSeperated(USER_NAME_COLUMN, PASSWORD_COLUMN, DISPLAY_NAME_COLUMN, USER_ID_COLUMN) + " FROM " + USERS_TABLE_NAME +
                    " WHERE " + USER_ID_COLUMN + " = " + id ;
            System.out.println(userQueryString);
            ResultSet resultSet = statement.executeQuery(userQueryString);
            if(resultSet != null && resultSet.next()){
                String u = resultSet.getString(USER_NAME_COLUMN);
                String p = resultSet.getString(PASSWORD_COLUMN);
                String d = resultSet.getString(DISPLAY_NAME_COLUMN);
                int i = resultSet.getInt(USER_ID_COLUMN);
                if((u != null && !u.isEmpty()) && (p != null && !p.isEmpty()) && (d != null && !d.isEmpty()) && (i > 0)){
                    newUser = new User(d,u,p,i);
                }
            }
            return newUser;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static User retrieve(String uName) {
        try{
            User newUser = null;
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            String userQueryString = "SELECT " + buildCommaSeperated(USER_NAME_COLUMN, PASSWORD_COLUMN, DISPLAY_NAME_COLUMN, USER_ID_COLUMN) + " FROM " + USERS_TABLE_NAME +
                    " WHERE " + USER_NAME_COLUMN + " = " + "\'" + uName + "\'";
            //String usersQueryString = "SELECT display_name, user_name, password, user_id FROM users  WHERE  user_name = " + "\'" + uName + "\'";
            ResultSet resultSet = statement.executeQuery(userQueryString);
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
        }
    }

    public static String retrieveName(int id){
        try {
            String name = "";
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            String queryString = "SELECT " + USER_NAME_COLUMN + " FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id;
            ResultSet resultSet = statement.executeQuery(queryString);
            if(resultSet != null && resultSet.next()){
                name = resultSet.getString(USER_NAME_COLUMN);
            }
            return name;
        }catch (SQLException e){
            System.out.println("Failed to get name");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString(){
        return String.format("%s:%s,%s:%s,%s:%s,%s:%s",DISPLAY_NAME_COLUMN,displayName,USER_NAME_COLUMN,userName,PASSWORD_COLUMN,password,USER_ID_COLUMN,userID);
    }

    public void alter() {
        displayName += " Cool Guy";
    }

}
