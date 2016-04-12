package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Recommendation extends Model {

    public static List<JsonObject> whoWasRecommendedTo(int movieID) {
        ImmutableList.Builder<JsonObject> builder = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            String query = "SELECT User FROM Recommendations WHERE Movie = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, movieID);
            r = statement.executeQuery();
            while (r.next()) {
                JsonObject object = new JsonObject();
                object.addProperty("ID", r.getInt("User"));
                builder.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, r);
        }
        return builder.build();
    }

}
