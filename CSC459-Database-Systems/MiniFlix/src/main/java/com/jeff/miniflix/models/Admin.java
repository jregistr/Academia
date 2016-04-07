package com.jeff.miniflix.models;

import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.empty;

public class Admin extends Model {

    public static Optional<JsonObject> getByUnameAndPass(String uname, String pass){
        Optional<JsonObject> object = empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement("SELECT ID, UserName, PassWord FROM AdminUsers WHERE UserName = ? AND PassWord = ?");
            statement.setString(1, uname);
            statement.setString(2, pass);
            r = statement.executeQuery();
            if (r.next()) {
                JsonObject temp = new JsonObject();
                temp.addProperty("U", r.getString("UserName"));
                temp.addProperty("P", r.getString("PassWord"));
                object = of(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return object;
    }

}
