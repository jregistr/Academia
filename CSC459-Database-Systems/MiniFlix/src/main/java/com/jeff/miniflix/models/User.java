package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class User extends Model {

    public static final String ID_USER_NAME = "UserName";
    public static final String ID_PASSWORD = "Password";
    public static final String ID_EMAIL = "Email";

    public static Optional<JsonObject> getByUserName(String userName) {
        Optional<JsonObject> userOptional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement("SELECT UserName, Password, Email  FROM Users WHERE UserName = ?");
            statement.setString(1, userName);
            r = statement.executeQuery();
            if (r.next()) {
                userOptional = Optional.of(from(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return userOptional;
    }

    public static List<JsonObject> getAll() {
        ImmutableList.Builder<JsonObject> array = new ImmutableList.Builder<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.createStatement();
            String query = "SELECT UserName, Password, Email  FROM Users";
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
        JsonObject object = new JsonObject();
        object.addProperty(ID_USER_NAME, r.getString(ID_USER_NAME));
        object.addProperty(ID_PASSWORD, r.getString(ID_PASSWORD));
        object.addProperty(ID_EMAIL, r.getString(ID_EMAIL));
        return object;
    }

    public static boolean makeUser(String uName, String pass, String email) {
        boolean success = true;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            String query = "INSERT INTO Users (UserName,Password,Email) " +
                    "VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, uName);
            statement.setString(2, pass);
            statement.setString(3, email);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        } finally {
            close(connection, statement);
        }
        return success;
    }

}
