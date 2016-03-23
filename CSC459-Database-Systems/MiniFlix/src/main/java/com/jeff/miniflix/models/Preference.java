package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Preference extends Model {

    public static final String ID_PREF_ID = "PrefID";
    public static final String ID_USER = "User";
    public static final String ID_CATEGORY = "Category";
    public static final String ID_CATEGORY_ID = "CategoryID";
    public static final String ID_CATEGORY_NAME = "Name";

    public static Optional<JsonObject> getByUserAndCat(int user, int cat) {
        Optional<JsonObject> optional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT Preferences.ID AS " + ID_PREF_ID + "," +
                    "Preferences.User," +
                    "Categories.ID AS " + ID_CATEGORY_ID + "," +
                    "Categories.Name " +
                    "FROM Preferences " +
                    "INNER JOIN Categories " +
                    "ON Preferences.Category = Categories.ID " +
                    "WHERE Preferences.User = ? AND Preferences.Category = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            statement.setInt(2, cat);
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

    public static List<JsonObject> getAll(){
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT Preferences.ID AS " + ID_PREF_ID + "," +
                    "Preferences.User," +
                    "Categories.ID AS " + ID_CATEGORY_ID + "," +
                    "Categories.Name " +
                    "FROM Preferences " +
                    "INNER JOIN Categories " +
                    "ON Preferences.Category = Categories.ID " +
                    "ORDER BY Preferences.User";
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
        object.addProperty(ID_PREF_ID, r.getInt(ID_PREF_ID));
        object.addProperty(ID_USER, r.getInt(ID_USER));
        object.addProperty(ID_CATEGORY_ID, r.getInt(ID_CATEGORY_ID));
        object.addProperty(ID_CATEGORY_NAME, r.getString(ID_CATEGORY_NAME));
        return object;
    }
}
