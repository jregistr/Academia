package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Movie extends Model {

    private static final String ID_TITLE = "Title";
    private static final String ID_LENGTH = "Length";
    private static final String ID_DESC = "Description";

    public static Optional<Movie> getById(int id) {
        Optional<Movie> movieOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT ID, Title, Length, Description FROM Movies WHERE ID = ?"
            );
            statement.setInt(1, id);

            r = statement.executeQuery();
            if (r.next()) {
                movieOptional = Optional.of(fromRes(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return movieOptional;
    }

    public static List<Movie> getAll() {
        ImmutableList.Builder<Movie> builder = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT ID, Title, Length, Description FROM Movies"
            );
            r = statement.executeQuery();
            while (r.next()) {
                builder.add(fromRes(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return builder.build();
    }

    public static List<Movie> getByCategory(int catID) {
        ImmutableList.Builder<Movie> builder = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT ID, Title, Length, Description FROM Movies WHERE Category = ?"
            );
            statement.setInt(1, catID);
            r = statement.executeQuery();
            while (r.next()) {
                builder.add(fromRes(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return builder.build();
    }

    private static Movie fromRes(ResultSet set) throws SQLException {
        return new Movie(set.getInt(ID_ID), set.getString(ID_TITLE), set.getInt(ID_LENGTH),
                set.getString(ID_DESC));
    }

    public final int id;
    public final String title;
    public final int length;
    public final String description;

    public Movie(int id, String title, int length, String description) {
        this.id = id;
        this.title = title;
        this.length = length;
        this.description = description;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(ID_ID, id);
        object.addProperty(ID_TITLE, title);
        object.addProperty(ID_LENGTH, length);
        object.addProperty(ID_DESC, description);
        return object;
    }

}
