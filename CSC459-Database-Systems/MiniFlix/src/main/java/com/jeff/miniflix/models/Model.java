package com.jeff.miniflix.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.sql.*;
import java.util.List;

public abstract class Model {

    protected static final String ID_ID = "ID";
    protected static final String ID_DATE_CREATED = "DateCreated";

    private static final String DB_LINK = String.format("jdbc:mysql://%s:%d/%s",
            "ec2-52-36-2-80.us-west-2.compute.amazonaws.com", 3306, "hw1");
    private static final String DB_USER = "appman";
    private static final String DB_PASS = "password";

    public static <T extends Model> JsonArray fromList(List<T> models) {
        JsonArray array = new JsonArray();
        models.forEach(t -> array.add(t.toJson()));
        return array;
    }

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
    }

    protected static void close(AutoCloseable...closeables) {
        if (closeables != null && closeables.length > 0) {
            try {
                for (AutoCloseable closeable : closeables) {
                    if(closeable != null){
                        closeable.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract JsonObject toJson();

}