package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;

public class Rating extends Model {

   /* private static final String ID_USER = "User";
    private static final String ID_MOVIE = "Movie";
    private static final String ID_RATING = "Rating";*/

    private static final String ID_RATE_ID = "RateID";
    private static final String ID_RATE_USER = "User";
    private static final String ID_MOVIE_ID = "MovieID";
    private static final String ID_MOVIE_TITLE = "Title";

   /* public static Optional<JsonObject> getByUserAndMovie(int user, int movie){
        Optional<JsonObject> optional = java.util.Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT User,Movie,Rating FROM Ratings WHERE User = ? AND  Movie = ?"
            );
            statement.setInt(1, user);
            statement.setInt(2, movie);
            r = statement.executeQuery();
            if(r.next()){
                optional = Optional.of(from(r));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return optional;
    }

    public static List<JsonObject> getForMovie(int movie){
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT User,Movie,Rating FROM Ratings WHERE Movie = ?"
            );
            statement.setInt(1, movie);
            r = statement.executeQuery();
            while (r.next()) {
                array.add(from(r));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return array.build();
    }*/

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT " +
                    "Ratings.ID AS " + ID_RATE_ID + "," +
                    "Ratings.User," +
                    "Movies.ID AS " + ID_MOVIE_ID + "," +
                    "Movies.Title " +
                    "FROM Ratings " +
                    "INNER JOIN Movies " +
                    "ON Ratings.Movie = Movies.ID " +
                    "ORDER BY Ratings.User";
            statement = connection.createStatement();
            r = statement.executeQuery(query);
            while (r.next()){
                array.add(from(r));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return array.build();
    }

    private static JsonObject from(ResultSet r) throws SQLException {
        JsonObject object = new JsonObject();
        object.addProperty(ID_RATE_ID, r.getInt(ID_RATE_ID));
        object.addProperty(ID_RATE_USER, r.getInt(ID_RATE_USER));
        object.addProperty(ID_MOVIE_ID, r.getInt(ID_MOVIE_ID));
        object.addProperty(ID_MOVIE_TITLE, r.getString(ID_MOVIE_TITLE));
        return object;
    }

}
