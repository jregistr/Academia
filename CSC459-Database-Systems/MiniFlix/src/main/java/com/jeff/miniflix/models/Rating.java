package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Rating extends Model {

    private static final String ID_USER = "User";
    private static final String ID_MOVIE = "Movie";
    private static final String ID_RATING = "Rating";

    public final int user;
    public final int movie;
    public final int rating;

    public static Optional<Rating> getByUserAndMovie(int user, int movie){
        Optional<Rating> optional = java.util.Optional.empty();
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

    public static List<Rating> getForMovie(int movie){
        ImmutableList.Builder<Rating> array = new ImmutableList.Builder<>();
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
    }

    private static Rating from(ResultSet r) throws SQLException {
        return new Rating(r.getInt(ID_USER), r.getInt(ID_MOVIE), r.getInt(ID_RATING));
    }

    public Rating(int user, int movie, int rating) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(ID_USER, user);
        object.addProperty(ID_MOVIE, movie);
        object.addProperty(ID_RATING, rating);
        return object;
    }
}
