package com.jeff.miniflix.config;


import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Constants {

    public class Keys {
        public static final String KEY_UNAME = "uname";
        public static final String KEY_PASS = "pass";
        public static final String KEY_CATEGORY = "category";
        public static final String KEY_U_ID = "uid";
    }

    public class Routes {
        public static final String ROUTE_BASE = "/";
        public static final String ROUTE_LOGIN = "/login";
        public static final String ROUTE_PROFILE = "/profile";
        public static final String ROUTE_TABLES = "/tables";

        public static final String ROUTE_USERS = "/users";
        public static final String ROUTE_MOVIES = "/movies";
        public static final String ROUTE_CATEGORY = "/categories";
        public static final String ROUTE_HISTORIES = "/histories";
        public static final String ROUTE_PREFERENCES = "/preferences";
        public static final String ROUTE_RATING = "/ratings";
    }

    public class Columns{
        public static final String ID = "ID";
        public static final String DATE_CREATED = "DateCreated";
        public static final String NAME = "Name";

    }

    public static String TYPE_JSON = "application/json";

    public static JsonObject output(JsonElement element) {
        JsonObject object = new JsonObject();
        object.add("Data", element);
        return object;
    }

}
