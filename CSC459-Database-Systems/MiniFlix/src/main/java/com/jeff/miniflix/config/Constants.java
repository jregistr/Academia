package com.jeff.miniflix.config;


import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Constants {

    public class Pages {
        public static final String WELCOME = "htmls/templates/welcome.html.ftl";
        public static final String PROFILE = "htmls/templates/profile.html.ftl";
    }

    public class Keys {
        public static final String UNAME = "uname";
        public static final String PASS = "pass";
        public static final String CONF_PASS = "confPass";
        public static final String EMAIL = "email";
        public static final String CATEGORY = "category";
        public static final String U_ID = "uid";
    }

    public class Routes {
        public static final String BASE = "/";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
        public static final String PROFILE = "/profile";
        public static final String TABLES = "/tables";

        public static final String USERS = "/users";
        public static final String MOVIES = "/movies";
        public static final String CATEGORY = "/categories";
        public static final String HISTORIES = "/histories";
        public static final String PREFERENCES = "/preferences";
        public static final String RATING = "/ratings";
    }

    public static String TYPE_JSON = "application/json";

    public static JsonObject output(JsonElement element) {
        JsonObject object = new JsonObject();
        object.add("Data", element);
        return object;
    }

    public static void notNull(Object...objects){
        for (Object object : objects) {
            Preconditions.checkNotNull(object);
            Preconditions.checkArgument(!object.toString().isEmpty());
        }
    }

}
