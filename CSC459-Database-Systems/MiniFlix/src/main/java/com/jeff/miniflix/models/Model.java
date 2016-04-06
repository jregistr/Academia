package com.jeff.miniflix.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class Model {

    public static final String ID_ID = "ID";
    protected static final String ID_DATE_CREATED = "DateCreated";

    private static final String DB_LINK = String.format("jdbc:mysql://%s:%d/%s",
            "ec2-52-36-2-80.us-west-2.compute.amazonaws.com", 3306, "hw1");
    private static final String DB_USER = "appman";
    private static final String DB_PASS = "password";

    public static JsonArray fromList(List<JsonObject> models) {
        JsonArray array = new JsonArray();
        models.forEach(array::add);
        return array;
    }

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
    }

    protected static void close(AutoCloseable... closeables) {
        if (closeables != null && closeables.length > 0) {
            try {
                for (AutoCloseable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}