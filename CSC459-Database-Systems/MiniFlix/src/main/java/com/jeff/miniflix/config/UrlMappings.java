package com.jeff.miniflix.config;

import com.google.common.base.Preconditions;
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

import com.jeff.miniflix.config.Constants.Keys;
import com.jeff.miniflix.config.Constants.Routes;
import com.jeff.miniflix.config.Constants.Pages;

import static spark.Spark.*;
import static com.jeff.miniflix.config.Constants.notNull;

public class UrlMappings {


    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.setClassForTemplateLoading(UrlMappings.class, "/");
        Spark.staticFileLocation("/public");
        port(4567);

        get(Routes.BASE, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), Pages.WELCOME);
        }, new FreeMarkerEngine(conf));

        post(Routes.LOGIN, (req, res) -> {
            String uName = req.queryParams(Keys.UNAME);
            String pass = req.queryParams(Keys.PASS);
            if (uName != null && pass != null) {
                Optional<JsonObject> user = User.getByUserName(uName);
                if (user.isPresent() && user.get().get(User.ID_PASSWORD).getAsString().equals(pass)) {
                    res.cookie(Keys.UNAME, user.get().get(User.ID_USER_NAME).getAsString());
                    res.cookie(Keys.U_ID, String.valueOf(user.get().get(User.ID_ID).getAsInt()));
                    res.redirect(Routes.PROFILE);
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

        post(Routes.REGISTER, (req, res) -> {
            String uName = req.queryParams(Keys.UNAME);
            String pass = req.queryParams(Keys.PASS);
            String confPass = req.queryParams(Keys.CONF_PASS);
            String email = req.queryParams(Keys.EMAIL);
            notNull(uName, pass, confPass, email);
            if (confPass.equals(pass)) {
                boolean success = User.makeUser(uName, pass, email);
                Optional<JsonObject> user = User.getByUserName(uName);
                if (success && user.isPresent()) {
                    res.cookie(Keys.UNAME, uName);
                    res.cookie(Keys.U_ID, String.valueOf(user.get().get(User.ID_ID).getAsInt()));
                    res.redirect(Routes.PROFILE);
                } else {
                    halt(404, "BAD Info.");
                }
                return res;
            } else {
                res.redirect(Routes.BASE);
                return res;
            }
        });

        get(Routes.PROFILE, ((request, response) -> {
            String uName = request.cookie(Keys.UNAME);
            if (uName == null || (!User.getByUserName(uName).isPresent())) {
                response.redirect(Routes.BASE);
                halt();
            }
            return new ModelAndView(new HashMap<String, String>(), Pages.PROFILE);
        }), new FreeMarkerEngine(conf));

        get(Constants.Routes.TABLES, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), Pages.TABLES);
        }, new FreeMarkerEngine(conf));

        RestMapping.makeMap();

    }
}