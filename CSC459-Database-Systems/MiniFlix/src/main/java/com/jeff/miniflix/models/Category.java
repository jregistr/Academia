package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Category extends Model {

    private static final String ID_NAME = "Name";

    public static Optional<JsonObject> getByName(String name) {
        Optional<JsonObject> catOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement("SELECT ID, Name FROM Categories WHERE Name = ?");
            statement.setString(1, name);
            r = statement.executeQuery();
            if (r.next()) {
                catOptional = Optional.of(from(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return catOptional;
    }

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.createStatement();
            r = statement.executeQuery("SELECT ID, Name FROM Categories");
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
        JsonObject object = new JsonObject();
       // object.addProperty(r.getString(ID_ID), r.getString(ID_NAME));
        object.addProperty(ID_ID, r.getInt(ID_ID));
        object.addProperty(ID_NAME, r.getString(ID_NAME));
        return object;
    }

    /*public final int id;
    public final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public JsonObject toJson() {
        JsonObject ob = new JsonObject();
        ob.addProperty(ID_ID, id);
        ob.addProperty(ID_NAME, name);
        return ob;
    }*/
}
