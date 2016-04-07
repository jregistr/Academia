package com.jeff.miniflix.config;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Constants {

    public class Pages {
        public static final String WELCOME = "htmls/templates/welcome.html.ftl";
        public static final String PROFILE = "htmls/templates/profile.html.ftl";
        public static final String TABLES = "htmls/templates/tables.html.ftl.html";
        public static final String HISTORY = "htmls/templates/history.html.ftl";
        public static final String RECOMEND = "htmls/templates/recommends.html.ftl";
        public static final String ADMIN_PROFILE = "htmls/templates/admin.html.ftl";
        public static final String ADMIN_STATS = "htmls/templates/adminstats.html.ftl";
    }

    public class Keys {
        public static final String UNAME = "uname";
        public static final String PASS = "pass";
        public static final String CONF_PASS = "confPass";
        public static final String EMAIL = "email";
        public static final String CATEGORY = "category";
        public static final String U_ID = "uid";
        public static final String M_ID = "mid";
        public static final String PROG = "progress";
        public static final String RATING = "rating";
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

        public static final String ADD_HISTORY = "/addhistory";
        public static final String UPDATE_HISTORY = "/updatehistory";
        public static final String HIST_MOVIE_AND_USER = "/histmovieuser";

        public static final String ADD_RATING = "/addrating";
        public static final String UPDATE_RATING = "/updaterating";
        public static final String RATING_MOVIE_AND_USER = "/ratingmovieuser";

        public static final String MOVIES_WITH_USER = "/movieswithuser";
        public static final String MOVIES_FOR_USER_HISTORY = "/moviesforuserhist";

        public static final String PROFILE_HISTORY = "/profile/myhistory";
        public static final String PROFILE_RECOMMENDS = "/profile/myrecommendations";

        public static final String RECOMMEND_MOVIES = "/recommendforthisuser";

        public static final String ADMIN_LOGIN = "/adminlogin";
        public static final String ADMIN_PROFILE = "/adminprofile";
        public static final String ADMIN_STATS = "/adminstats";

    }

    public static String TYPE_JSON = "application/json";

    public static JsonObject output(JsonElement element) {
        JsonObject object = new JsonObject();
        object.add("Data", element);
        return object;
    }

    public static JsonObject succFailOpt(boolean success){
        JsonObject object = new JsonObject();
        object.addProperty("Success", success);
        return object;
    }

    public static void notNull(Object...objects){
        for (Object object : objects) {
            Preconditions.checkNotNull(object);
            Preconditions.checkArgument(!object.toString().isEmpty());
        }
    }

}
