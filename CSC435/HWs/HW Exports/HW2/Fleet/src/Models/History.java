package Models;

import java.sql.*;

public class History extends Model {

    public static final String HISTORIES_TABLE_NAME = "histories";
   /* public static final String BATTLES_FOUGHT_COLUMN = "battles_fought";
    public static final String WON_COLUMN = "won";*/
    public static final String RATING_COLUMN = "rating";
    public static final String USER_ID_COLUMN = "user_id";

    //private int battlesFought;
    //private int won;
    private int rating;
    private int userID;

    public History(int rate,int uID){
       // battlesFought = battlesF;
       // won = battlesWon;
        rating = rate;
        userID = uID;
    }

    public int getRating() {
        return rating;
    }

    /*public int getWon() {
        return won;
    }

    public int getBattlesFought() {
        return battlesFought;
    }*/

    public int getUserID() {
        return userID;
    }

    public boolean save(){
        if(userID > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String testQuery = "SELECT * FROM " + HISTORIES_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + userID;
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if(newEntry) {
                    String queryString = "INSERT INTO " + HISTORIES_TABLE_NAME + "(" + buildCommaSeperated(RATING_COLUMN, USER_ID_COLUMN)
                            + ") VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    /*preparedStatement.setInt(1, battlesFought);
                    preparedStatement.setInt(2, won);*/
                    preparedStatement.setInt(1, rating);
                    preparedStatement.setInt(2, userID);
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }else {
                    String queryString = "UPDATE " + HISTORIES_TABLE_NAME + " SET " + RATING_COLUMN + " = ?"
                            + " WHERE " + USER_ID_COLUMN + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                   /* preparedStatement.setInt(1, battlesFought);
                    preparedStatement.setInt(2, won);*/
                    preparedStatement.setInt(1, rating);
                    preparedStatement.setInt(2, userID);
                    System.out.println("UPDATE: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean save(History history){
        if(history.getUserID() > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                String testQuery = "SELECT * FROM " + HISTORIES_TABLE_NAME + " WHERE " + USER_ID_COLUMN + " = " + history.getUserID();
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(testQuery);
                boolean newEntry = !set.next();
                if(newEntry) {
                    String queryString = "INSERT INTO " + HISTORIES_TABLE_NAME + "(" + buildCommaSeperated(RATING_COLUMN, USER_ID_COLUMN)
                            + ") VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    /*preparedStatement.setInt(1, battlesFought);
                    preparedStatement.setInt(2, won);*/
                    preparedStatement.setInt(1, history.getRating());
                    preparedStatement.setInt(2, history.getUserID());
                    System.out.println("PREPARED: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }else {
                    String queryString = "UPDATE " + HISTORIES_TABLE_NAME + " SET " + RATING_COLUMN + " = ?"
                            + " WHERE " + USER_ID_COLUMN + " = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                   /* preparedStatement.setInt(1, battlesFought);
                    preparedStatement.setInt(2, won);*/
                    preparedStatement.setInt(1, history.getRating());
                    preparedStatement.setInt(2, history.getUserID());
                    System.out.println("UPDATE: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                return false;
            }
        }else {
            return false;
        }
    }

    public static History retrieve(int id){
        if(id > 0){
            try{
                History history = null;
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                String queryString = "SELECT " + buildCommaSeperated(RATING_COLUMN,USER_ID_COLUMN) + " FROM " + HISTORIES_TABLE_NAME  +
                        " WHERE " + USER_ID_COLUMN  + " = " + id;
                System.out.println(queryString);
                ResultSet resultSet = statement.executeQuery(queryString);
                if(resultSet != null && resultSet.next()){
                    /*int f = resultSet.getInt(BATTLES_FOUGHT_COLUMN);
                    int w = resultSet.getInt(WON_COLUMN);*/
                    int r = resultSet.getInt(RATING_COLUMN);
                    int i = resultSet.getInt(USER_ID_COLUMN);
                    if(i > 0){
                        history = new History(r,i);
                    }
                }
                return history;
            }catch (SQLException e){
                return null;
            }
        }else {
            System.out.println("ID is less than or equal to zero, such a user can't exist");
            return null;
        }
    }

    public String rank(){
        return "A New Rank";
    }

    @Override
    public String toString() {
        return "History{" +
                "rating=" + rating +
                ", userID=" + userID +
                '}';
    }
}
