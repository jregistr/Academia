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
        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("SELECT UserName, Password, Email  FROM Users WHERE UserName = ?");
            statement.setString(1, userName);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                userOptional = Optional.of(new User(r.getString(ID_USER_NAME), r.getString(ID_PASSWORD),
                        r.getString(ID_EMAIL)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userOptional;
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
