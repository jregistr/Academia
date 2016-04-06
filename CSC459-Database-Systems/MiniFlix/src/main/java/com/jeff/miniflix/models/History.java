package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class History extends Model {

    private static final String ID_USER = "User";
    private static final String ID_MOVIE = "Movie";
    private static final String ID_PROGRESS = "Progress";


    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT * FROM Histories";

            statement = connection.createStatement();
            r = statement.executeQuery(query);
            while (r.next()) {
                array.add(from(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return array.build();
    }

    public static Optional<JsonObject> getForUserAndMovie(int uID, int movieID) {
        Optional<JsonObject> output = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT * FROM Histories WHERE User = ? AND Movie = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, uID);
            statement.setInt(2, movieID);
            r = statement.executeQuery();
            if (r.next()) {
                output = Optional.of(from(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return output;
    }

    public static boolean addHistory(int uID, int movie, float progress) {
        boolean success = true;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            String query = "INSERT INTO Histories(User,Movie,Progress) " +
                    "VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, uID);
            statement.setInt(2, movie);
            statement.setFloat(3, progress);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            close(connection, statement);
        }
        return success;
    }

    public static boolean updateHistory(int uID, int movie, float progress){
        boolean success = true;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            String query = "UPDATE Histories " +
                    "SET Progress=? " +
                    "WHERE User = ? AND Movie = ?";
            statement = connection.prepareStatement(query);
            statement.setFloat(1, progress);
            statement.setInt(2, uID);
            statement.setInt(3, movie);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            close(connection, statement);
        }
        return success;
    }

    private static JsonObject from(ResultSet r) throws SQLException {
        JsonObject object = new JsonObject();
        object.addProperty(ID_ID, r.getInt(ID_ID));
        object.addProperty(ID_USER, r.getInt(ID_USER));
        object.addProperty(ID_MOVIE, r.getInt(ID_MOVIE));
        object.addProperty(ID_PROGRESS, r.getFloat(ID_PROGRESS));
        return object;
    }
}
