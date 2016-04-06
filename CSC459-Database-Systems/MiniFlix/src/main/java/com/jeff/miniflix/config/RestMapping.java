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

        get(Constants.Routes.ADD_HISTORY, (request, response) -> {
            String rawUID = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            String progVal = request.queryParams(Constants.Keys.PROG);
            if (rawUID != null && movID != null && progVal != null) {
                if (NumberUtils.isNumber(rawUID) && NumberUtils.isNumber(movID) && NumberUtils.isNumber(progVal)) {
                    //return Constants.succFailOpt(true);
                    return Constants.succFailOpt(History.addHistory(Integer.parseInt(rawUID), Integer.parseInt(movID), Float.parseFloat(progVal)));
                } else {
                    return Constants.succFailOpt(false);
                }
            } else {
                return Constants.succFailOpt(false);
            }
        });

        get(Constants.Routes.UPDATE_HISTORY, (request, response) -> {
            response.type(TYPE_JSON);
            String rawUID = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            String progVal = request.queryParams(Constants.Keys.PROG);
            if (rawUID != null && movID != null && progVal != null) {
                if (NumberUtils.isNumber(rawUID) && NumberUtils.isNumber(movID) && NumberUtils.isNumber(progVal)) {
                    //return Constants.succFailOpt(true);
                    return Constants.succFailOpt(History.updateHistory(Integer.parseInt(rawUID), Integer.parseInt(movID), Float.parseFloat(progVal)));
                } else {
                    return Constants.succFailOpt(false);
                }
            } else {
                return Constants.succFailOpt(false);
            }
        });

        get(Constants.Routes.HIST_MOVIE_AND_USER, (request, response) -> {
            response.type(TYPE_JSON);
            String rawUID = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            System.out.println("RAWUID:" + rawUID + " MOVIEID:" + movID);
            if (rawUID != null && movID != null) {
                if (NumberUtils.isNumber(rawUID) && NumberUtils.isNumber(movID)) {
                    Optional<JsonObject> optional = History.getForUserAndMovie(Integer.parseInt(rawUID), Integer.parseInt(movID));
                    if(optional.isPresent()){
                        return optional.get().toString();
                    }else {
                        return Constants.succFailOpt(false);
                    }
                    //return History.getForUserAndMovie(Integer.parseInt(rawUID), Integer.parseInt(movID)).get().toString();
                } else {
                    return Constants.succFailOpt(false);
                }
            } else {
                return Constants.succFailOpt(false);
            }
        });

        get(Constants.Routes.ADD_RATING, (request, response) -> {
            response.type(TYPE_JSON);
            String uid = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            String rating = request.queryParams(Constants.Keys.RATING);
            if(uid != null && movID != null && rating != null){
                if(NumberUtils.isNumber(uid) && NumberUtils.isNumber(movID) && NumberUtils.isNumber(rating)){
                   return Rating.addRating(Integer.parseInt(uid), Integer.parseInt(movID), Integer.parseInt(rating));
                    //return Constants.succFailOpt(true);
                }else {
                    return Constants.succFailOpt(false);
                }
            }else {
                return Constants.succFailOpt(false);
            }
        });

        get(Constants.Routes.UPDATE_RATING, (request, response) -> {
            response.type(TYPE_JSON);
            String uid = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            String rating = request.queryParams(Constants.Keys.RATING);
            if(uid != null && movID != null && rating != null){
                if(NumberUtils.isNumber(uid) && NumberUtils.isNumber(movID) && NumberUtils.isNumber(rating)){
                    System.out.println("HERE");
                    return Rating.updateRating(Integer.parseInt(uid), Integer.parseInt(movID), Integer.parseInt(rating));
                   // return Constants.succFailOpt(true);
                }else {
                    return Constants.succFailOpt(false);
                }
            }else {
                return Constants.succFailOpt(false);
            }
        });

        get(Constants.Routes.RATING_MOVIE_AND_USER, (request, response) -> {
            response.type(TYPE_JSON);
            String rawUID = request.queryParams(Constants.Keys.U_ID);
            String movID = request.queryParams(Constants.Keys.M_ID);
            if (rawUID != null && movID != null) {
                if (NumberUtils.isNumber(rawUID) && NumberUtils.isNumber(movID)) {
                    Optional<JsonObject> optional = Rating.getByUserAndMovie(Integer.parseInt(rawUID), Integer.parseInt(movID));
                    if(optional.isPresent()){
                        return optional.get().toString();
                    }else {
                        return Constants.succFailOpt(false);
                    }
                } else {
                    return Constants.succFailOpt(false);
                }
            } else {
                return Constants.succFailOpt(false);
            }
        });

    }

}
