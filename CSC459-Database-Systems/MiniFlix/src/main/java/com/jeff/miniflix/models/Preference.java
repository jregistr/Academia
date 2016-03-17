package com.jeff.miniflix.models;


import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Preference extends Model {

    private static final String ID_USER = "User";
    private static final String ID_CATEGORY = "Category";

    public static Optional<Preference> getByUserAndCat(int user, int cat) {
        Optional<Preference> optional = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(
                    "SELECT User, Category FROM Preferences WHERE User = ? AND Category = ?");
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

    private static Preference from(ResultSet r) throws SQLException {
        return new Preference(r.getInt(ID_USER), r.getInt(ID_CATEGORY));
    }

    public final int user;
    public final int category;

    public Preference(int user, int category) {
        this.user = user;
        this.category = category;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(ID_USER, user);
        object.addProperty(ID_CATEGORY, category);
        return object;
    }
}
