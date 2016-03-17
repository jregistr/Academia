package com.jeff.miniflix.models;


import com.google.gson.JsonObject;

import java.sql.*;
import java.util.Optional;

public class User extends Model {

    private static final String ID_USER_NAME = "UserName";
    private static final String ID_PASSWORD = "Password";
    private static final String ID_EMAIL = "Email";

    public static Optional<User> getByUserName(String userName) {
        Optional<User> userOptional = Optional.empty();
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
        }finally {
            close(connection, statement, r);
        }
        return userOptional;
    }

    private static User from(ResultSet r)throws SQLException {
        return new User(r.getString(ID_USER_NAME), r.getString(ID_PASSWORD),
                r.getString(ID_EMAIL));
    }

    public String userName;
    public String password;
    public String email;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(ID_USER_NAME, userName);
        object.addProperty(ID_PASSWORD, password);
        object.addProperty(ID_EMAIL, email);
        return object;
    }
}
