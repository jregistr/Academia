package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Rating extends Model {

    private static final String ID_USER = "User";
    private static final String ID_MOVIE = "Movie";
    private static final String ID_RATING = "Rating";

    public static boolean addRating(int uID, int movieID, int rating) {
        return History.getForUserAndMovie(uID, movieID).isPresent() && makeRatingUpdate(uID, movieID, rating, true);
    }

    public static boolean updateRating(int uID, int movieID, int rating) {
        return History.getForUserAndMovie(uID, movieID).isPresent() && makeRatingUpdate(uID, movieID, rating, false);
    }

    public static Optional<JsonObject> getByUserAndMovie(int user, int movie) {
        Optional<JsonObject> optional = java.util.Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT * FROM Ratings WHERE User = ? AND  Movie = ?"
            );
            statement.setInt(1, user);
            statement.setInt(2, movie);
            r = statement.executeQuery();
            if (r.next()) {
                optional = Optional.of(from(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return optional;
    }

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT " +
                    "Ratings.ID AS " + "Something" + "," +
                    "Ratings.User," +
                    "Movies.ID AS " + "SomethingElse" + "," +
                    "Movies.Title " +
                    "FROM Ratings " +
                    "INNER JOIN Movies " +
                    "ON Ratings.Movie = Movies.ID " +
                    "ORDER BY Ratings.User";
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


    private static boolean makeRatingUpdate(int uID, int movieID, int rating, boolean insert) {
        boolean success = true;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            statement = insert ?
                    insertRatingQuery(uID, movieID, rating, connection)
                    : updateRatingQuery(uID, movieID, rating, connection);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            close(connection, statement);
        }
        return success;
    }

    private static PreparedStatement updateRatingQuery(int uID, int movieID, int rating, Connection connection) throws SQLException {
        String query = "UPDATE Ratings " +
                "SET Rating=? " +
                "WHERE User=? AND Movie=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, rating);
        statement.setInt(2, uID);
        statement.setInt(3, movieID);
        return statement;
    }

    private static PreparedStatement insertRatingQuery(int uID, int movieID, int rating, Connection connection) throws SQLException {
        String query = "INSERT INTO Ratings(User,Movie,Rating) " +
                "VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, uID);
        statement.setInt(2, movieID);
        statement.setInt(3, rating);
        return statement;
    }

    private static JsonObject from(ResultSet r) throws SQLException {
        JsonObject object = new JsonObject();
        object.addProperty(ID_ID, r.getInt(ID_ID));
        object.addProperty(ID_USER, r.getInt(ID_USER));
        object.addProperty(ID_MOVIE, r.getInt(ID_MOVIE));
        object.addProperty(ID_RATING, r.getInt(ID_RATING));
        return object;
    }

}
