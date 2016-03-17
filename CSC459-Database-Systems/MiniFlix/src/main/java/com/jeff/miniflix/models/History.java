package com.jeff.miniflix.models;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.List;

public class History extends Model {

    private static final String ID_USER = "User";
    private static final String ID_MOVIE = "Movie";
    private static final String ID_PROGRESS = "Progress";

    public static List<History> getForUser(int user){
        ImmutableList.Builder<History> array = new ImmutableList.Builder<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet r = null;
        try {
            connection = connect();
            statement = connection.prepareStatement("SELECT * FROM Histories WHERE User = ?");
            statement.setInt(1, user);
            r = statement.executeQuery();
            while (r.next()) {
                array.add(from(r));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(connection, statement, r);
        }
        return array.build();
    }

    private static History from(ResultSet r) throws SQLException{
        return new History(r.getInt(ID_ID), r.getTimestamp(ID_DATE_CREATED),
                r.getInt(ID_USER), r.getInt(ID_MOVIE), r.getFloat(ID_PROGRESS));
    }

    public final int id;
    public Timestamp dateCreated;
    public final int user;
    public final int movie;
    public float progress;

    public History(int id, Timestamp dateCreated, int user, int movie, float progress) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.user = user;
        this.movie = movie;
        this.progress = progress;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(ID_ID, id);
        object.addProperty(ID_DATE_CREATED, dateCreated.getTime());
        object.addProperty(ID_USER, user);
        object.addProperty(ID_MOVIE, movie);
        object.addProperty(ID_PROGRESS, progress);
        return object;
    }
}
