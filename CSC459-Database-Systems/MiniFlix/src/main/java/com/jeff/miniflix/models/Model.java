package com.jeff.miniflix.models;

import com.google.gson.JsonObject;

import java.sql.*;

public abstract class Model {

    private static final String DB_LINK = String.format("jdbc:mysql://%s:%d/%s",
            "ec2-52-36-2-80.us-west-2.compute.amazonaws.com", 3306, "hw1");
    private static final String DB_USER = "appman";
    private static final String DB_PASS = "password";

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
    }

    protected static void closeAll(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        connection.close();
        statement.close();
        resultSet.close();
    }

    public abstract JsonObject toJson();

}