package com.jeff.miniflix.config;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jeff.miniflix.models.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Optional;

import static com.jeff.miniflix.config.Constants.TYPE_JSON;
import static spark.Spark.get;

public class RestMapping {

    public static void makeMap() {
        get(Constants.Routes.USERS, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(User.getAll()).toString();
        });

        get(Constants.Routes.MOVIES, (request, response) -> {
            String cat = request.queryParams(Constants.Keys.CATEGORY);
            response.type(TYPE_JSON);
            List<JsonObject> movies = cat != null && NumberUtils.isNumber(cat) ?
                    Movie.getByCategory(Integer.parseInt(cat)) :
                    Movie.getAll();
            return Model.fromList(movies).toString();
        });

        get(Constants.Routes.CATEGORY, (request, response) -> {
            String cat = request.queryParams(Constants.Keys.CATEGORY);
            response.type(TYPE_JSON);
            JsonElement out;
            if (cat != null) {
                Optional<JsonObject> category = Category.getByName(cat);
                out = category.isPresent() ? category.get() : new JsonObject();
            } else {
                out = Category.fromList(Category.getAll());
            }
            return out.toString();
        });

        get(Constants.Routes.HISTORIES, (request, response) -> {
            response.type(TYPE_JSON);
            /*String user = request.queryParams(U_ID);
            JsonElement data = new JsonObject();
            if(user != null && NumberUtils.isNumber(user)){
                data = History.fromList(History.getForUser(Integer.parseInt(user)));
            }*/
            return Model.fromList(History.getAll()).toString();
        });

        get(Constants.Routes.PREFERENCES, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(Preference.getAll()).toString();
        });

        get(Constants.Routes.RATING, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(Rating.getAll()).toString();
        });


    }

}
