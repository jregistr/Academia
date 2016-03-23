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

    public static Optional<JsonObject> getById(int id) {
        Optional<JsonObject> movieOptional = Optional.empty();
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

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> builder = new ImmutableList.Builder<>();
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

    public static List<JsonObject> getByCategory(int catID) {
        ImmutableList.Builder<JsonObject> builder = new ImmutableList.Builder<>();
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

    public static JsonObject fromRes(ResultSet set) throws SQLException {
        JsonObject object = new JsonObject();
        object.addProperty(ID_ID, set.getInt(ID_ID));
        object.addProperty(ID_TITLE, set.getString(ID_TITLE));
        object.addProperty(ID_LENGTH, set.getInt(ID_LENGTH));
        object.addProperty(ID_DESC, set.getString(ID_DESC));
        return object;
    }

}
