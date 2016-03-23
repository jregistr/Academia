package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;

public class History extends Model {

    private static final String ID_HIST_ID = "HistID";
    private static final String ID_USER = "User";
    private static final String ID_PROGRESS = "Progress";

    private static final String ID_MOVIE = "Movie";
    private static final String ID_MOVIE_ID = "MovieID";
    private static final String ID_MOVIE_TITLE = "Title";
    private static final String ID_MOVIE_LENGTH = "Length";

    public static List<JsonObject> getForUser(int user) {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT Histories.ID AS " + ID_HIST_ID + ", Histories.User, Histories.Progress," +
                    "Movies.ID AS " + ID_MOVIE_ID + ", Movies.Title, Movies.Length " +
                    "FROM Movies " +
                    "INNER JOIN Histories " +
                    "ON Movies.ID = Histories.Movie " +
                    "WHERE Histories.User = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1, user);

            r = statement.executeQuery();
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

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT Histories.ID AS " + ID_HIST_ID + ", Histories.User, Histories.Progress," +
                    "Movies.ID AS " + ID_MOVIE_ID + ", Movies.Title, Movies.Length " +
                    "FROM Movies " +
                    "INNER JOIN Histories " +
                    "ON Movies.ID = Histories.Movie";

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

    private static JsonObject from(ResultSet r) throws SQLException {
        //JsonObject movie = new JsonObject();
        JsonObject object = new JsonObject();
     //   movie.addProperty(ID_MOVIE_ID, r.getInt(ID_MOVIE_ID));
     //   movie.addProperty(ID_MOVIE_TITLE, r.getString(ID_MOVIE_TITLE));
       // movie.addProperty(ID_MOVIE_LENGTH, r.getInt(ID_MOVIE_LENGTH));

        object.addProperty(ID_HIST_ID, r.getInt(ID_HIST_ID));
        object.addProperty(ID_USER, r.getInt(ID_USER));
        object.addProperty(ID_PROGRESS, r.getFloat(ID_PROGRESS));

        object.addProperty(ID_MOVIE_ID, r.getInt(ID_MOVIE_ID));
        object.addProperty(ID_MOVIE_TITLE, r.getString(ID_MOVIE_TITLE));
      //  object.add(ID_MOVIE, movie);
        return object;
    }
}
