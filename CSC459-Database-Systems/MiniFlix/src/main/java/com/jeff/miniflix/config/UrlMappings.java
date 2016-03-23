package com.jeff.miniflix.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jeff.miniflix.models.*;
import freemarker.template.Configuration;
import org.apache.commons.lang3.math.NumberUtils;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.jeff.miniflix.config.Constants.Keys.*;
import static com.jeff.miniflix.config.Constants.Routes.*;
import static com.jeff.miniflix.config.Constants.TYPE_JSON;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;

public class UrlMappings {


    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.setClassForTemplateLoading(UrlMappings.class, "/");
        Spark.staticFileLocation("/public");
        port(80);

        get(ROUTE_BASE, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), "htmls/templates/tables.html.ftl.html");
        }, new FreeMarkerEngine(conf));

        get(ROUTE_LOGIN, (req, res) -> {
            String uName = req.queryParams(KEY_UNAME);
            String pass = req.queryParams(KEY_PASS);
            if (uName != null && pass != null) {
                Optional<JsonObject> user = User.getByUserName(uName);
                if (user.isPresent() && user.get().get(User.ID_PASSWORD).getAsString().equals(pass)) {
                    res.cookie(KEY_UNAME, user.get().get(User.ID_USER_NAME).getAsString());
                    res.redirect(ROUTE_PROFILE);
                    return res;
                } else {
                    halt(404, "Invalid login");
                    return res;
                }
            } else {
                halt(404, "No such user");
                return res;
            }
        });

        get(ROUTE_PROFILE, ((request, response) -> {
            String uName = request.cookie(KEY_UNAME);
            if (uName == null || (!User.getByUserName(uName).isPresent())) {
                response.redirect(ROUTE_BASE);
                halt();
            }
            return new ModelAndView(new HashMap<String, String>(), "htmls/templates/profile.html.ftl");
        }), new FreeMarkerEngine(conf));

        get(ROUTE_USERS, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(User.getAll()).toString();
        });

        get(ROUTE_MOVIES, (request, response) -> {
            String cat = request.queryParams(KEY_CATEGORY);
            response.type(TYPE_JSON);
            List<JsonObject> movies = cat != null && NumberUtils.isNumber(cat) ?
                    Movie.getByCategory(Integer.parseInt(cat)) :
                    Movie.getAll();
            return Model.fromList(movies).toString();
        });

        get(ROUTE_CATEGORY, (request, response) -> {
            String cat = request.queryParams(KEY_CATEGORY);
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

        get(ROUTE_HISTORIES, (request, response) -> {
            response.type(TYPE_JSON);
            /*String user = request.queryParams(KEY_U_ID);
            JsonElement data = new JsonObject();
            if(user != null && NumberUtils.isNumber(user)){
                data = History.fromList(History.getForUser(Integer.parseInt(user)));
            }*/
            return Model.fromList(History.getAll()).toString();
        });

        get(ROUTE_PREFERENCES, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(Preference.getAll()).toString();
        });

        get(ROUTE_RATING, (request, response) -> {
            response.type(TYPE_JSON);
            return Model.fromList(Rating.getAll()).toString();
        });

        get(ROUTE_TABLES, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), "htmls/templates/tables.html.ftl.html");
        }, new FreeMarkerEngine(conf));

    }
}