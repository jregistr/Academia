package com.jeff.miniflix.config;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Constants {

    public class Keys {
        public static final String KEY_UNAME = "uname";
        public static final String KEY_PASS = "pass";
        public static final String KEY_CATEGORY = "category";
    }

    public class Routes {
        public static final String ROUTE_BASE = "/";
        public static final String ROUTE_LOGIN = "/login";
        public static final String ROUTE_PROFILE = "/profile";

        public static final String ROUTE_MOVIES = "/movies";
        //public static final String ROUTE_

    }

    public static String TYPE_JSON = "application/json";

    public static JsonObject output(JsonElement element) {
        JsonObject object = new JsonObject();
        object.add("Data", element);
        return object;
    }

}
