package com.jeff.miniflix.models;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.Optional;

public class Category extends Model {

    private static final String ID_NAME = "ID_NAME";
    private static final String ID_ID = "ID_ID";

    public static Optional<Category> getByName(String name) {
        Optional<Category> catOptional = Optional.empty();
        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("SELECT ID, Name FROM Categories WHERE Name = ?");
            statement.setString(1, name);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                catOptional = Optional.of(new Category(r.getInt(ID_ID), r.getString(ID_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catOptional;
    }

    public static JsonArray getAll(){
        JsonArray array = new JsonArray();
        try{
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet r = statement.executeQuery("SELECT ID, Name FROM Categories");
            while (r.next()){
                array.add(new Category(r.getInt(ID_ID), r.getString(ID_NAME)).toJson());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    public final int id;
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
    }
}
