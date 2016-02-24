package Models;

import java.sql.*;

public class Customization extends Model {

    public static final String CUSTOMIZATIONS_TABLE_NAME = "customizations";
    public static final String PROFILE_IMG_COLUMN = "profile_img";
    public static final String BACKGROUND_IMG_COLUMN = "background_img";
    public static final String USER_ID_COLUMN = "user_id";

    public static final String DEFAULT_PROFILE_IMG = "guy1.png";
    public static final String DEFAULT_BACK_IMG = "introBack.png";

    private String profileImage;
    private String backgroundImage;
    private int userID;

    public Customization(String prof,String back,int uID){
        profileImage = prof;
        backgroundImage = back;
        userID = uID;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public int getUserID() {
        return userID;
    }

    public boolean save(){
       try {
           Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
           String testQuery = "SELECT * FROM " + CUSTOMIZATIONS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + userID;
           Statement statement = connection.createStatement();
           ResultSet set = statement.executeQuery(testQuery);
           boolean newEntry = !set.next();
           if(newEntry){
               String insertQuery = "INSERT INTO " + CUSTOMIZATIONS_TABLE_NAME + " VALUES(" + "\'" + profileImage + "\'" + "," + "\'" + backgroundImage + "\'" + "," + userID + ")";
               statement.executeUpdate(insertQuery);
           }else {
               String updateQuery = "UPDATE " + CUSTOMIZATIONS_TABLE_NAME + " SET " + PROFILE_IMG_COLUMN + " = ?" + ", " + BACKGROUND_IMG_COLUMN + " = ? WHERE " + USER_ID_COLUMN + " = ?";
              // System.out.println(updateQuery);
               PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
               preparedStatement.setString(1,profileImage);
               preparedStatement.setString(2,backgroundImage);
               preparedStatement.setInt(3,userID);
               System.out.println("Update: " + preparedStatement.toString());
               preparedStatement.executeUpdate();
           }
           return true;
       }catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
    }

    public static boolean save(Customization customization){
        try {
            Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
            String testQuery = "SELECT * FROM " + CUSTOMIZATIONS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + customization.getUserID();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(testQuery);
            boolean newEntry = !set.next();
            if(newEntry){
                String insertQuery = "INSERT INTO " + CUSTOMIZATIONS_TABLE_NAME + " VALUES(" + "\'" + customization.getProfileImage() + "\'" + "," + "\'" + customization.getBackgroundImage() + "\'" + "," + customization.getUserID() + ")";
                statement.executeUpdate(insertQuery);
            }else {
                String updateQuery = "UPDATE " + CUSTOMIZATIONS_TABLE_NAME + " SET " + PROFILE_IMG_COLUMN + " = ?" + ", " + BACKGROUND_IMG_COLUMN + " = ? WHERE " + USER_ID_COLUMN + " = ?";
                // System.out.println(updateQuery);
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1,customization.getProfileImage());
                preparedStatement.setString(2,customization.getBackgroundImage());
                preparedStatement.setInt(3,customization.getUserID());
                System.out.println("Update: " + preparedStatement.toString());
                preparedStatement.executeUpdate();
            }
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customization retrieve(int uID){
        if(uID > 0){
            Customization customization = null;
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                String queryString = "SELECT " + buildCommaSeperated(PROFILE_IMG_COLUMN,BACKGROUND_IMG_COLUMN,USER_ID_COLUMN) + " FROM " + CUSTOMIZATIONS_TABLE_NAME +
                        " WHERE " + USER_ID_COLUMN + " = " + uID;
                System.out.println(queryString);
                ResultSet resultSet = statement.executeQuery(queryString);
                if(resultSet != null && resultSet.next()){
                    String p = resultSet.getString(PROFILE_IMG_COLUMN);
                    String b = resultSet.getString(BACKGROUND_IMG_COLUMN);
                    int i = resultSet.getInt(USER_ID_COLUMN);
                    if((p != null && !p.isEmpty()) && (b != null && !b.isEmpty()) && i > 0){
                        customization = new Customization(p,b,i);
                    }
                }
            }catch (SQLException e){
                System.out.println("Failed to get customization");
                e.printStackTrace();
                return null;
            }
            return customization;
        }else {
            System.out.println("ID is less than or equal to zero, such a user can't exist");
            return null;
        }
    }

    public static String retrieveProfilePic(int id){
        if(id > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT " + PROFILE_IMG_COLUMN + " FROM " + CUSTOMIZATIONS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id;
                System.out.println("Query: " + queryString);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryString);
                if(resultSet != null && resultSet.next()){
                    String img = resultSet.getString(PROFILE_IMG_COLUMN);
                    System.out.println("IMG: " + img);
                    return (img != null && !img.isEmpty()) ? img : DEFAULT_PROFILE_IMG;
                }else {
                    return DEFAULT_PROFILE_IMG;
                }
            }catch (SQLException e){
                System.out.println("Failed to get profile img");
                e.printStackTrace();
                return DEFAULT_PROFILE_IMG;
            }
        }else {
            return DEFAULT_PROFILE_IMG;
        }
    }

    public static String retrieveBackgroundPic(int id){
        if(id > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String queryString = "SELECT " + BACKGROUND_IMG_COLUMN + " FROM " + CUSTOMIZATIONS_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + id;
                System.out.println("Back Query: " + queryString);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryString);
                if(resultSet != null && resultSet.next()){
                    String img = resultSet.getString(BACKGROUND_IMG_COLUMN);
                    return (img != null) ? img : DEFAULT_BACK_IMG;
                }else {
                    return DEFAULT_BACK_IMG;
                }
            }catch (SQLException e){
                System.out.println("Failed to get background img");
                e.printStackTrace();
                return DEFAULT_BACK_IMG;
            }
        }else {
            return DEFAULT_BACK_IMG;
        }
    }

    @Override
    public String toString(){
        return String.format("%s:%s,%s:%s,%s:%s",PROFILE_IMG_COLUMN,profileImage,BACKGROUND_IMG_COLUMN,backgroundImage,USER_ID_COLUMN,userID);
    }

}

