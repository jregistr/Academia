package com.jeff.miniflix.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.jeff.miniflix.config.Constants.Keys;
import com.jeff.miniflix.config.Constants.Pages;
import com.jeff.miniflix.config.Constants.Routes;
import com.jeff.miniflix.models.Admin;
import com.jeff.miniflix.models.User;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Optional;

import static com.jeff.miniflix.config.Constants.notNull;
import static spark.Spark.*;

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
            return new ModelAndView(ImmutableMap.of("name", uName), Pages.PROFILE);
        }), new FreeMarkerEngine(conf));

        get(Routes.PROFILE_HISTORY, ((request, response) -> {
            String uName = request.cookie(Keys.UNAME);
            if (uName == null || (!User.getByUserName(uName).isPresent())) {
                response.redirect(Routes.BASE);
                halt();
            }
            return new ModelAndView(ImmutableMap.of("name", uName), Pages.HISTORY);
        }), new FreeMarkerEngine(conf));

        get(Routes.PROFILE_RECOMMENDS, ((request, response) -> {
            String uName = request.cookie(Keys.UNAME);
            if (uName == null || (!User.getByUserName(uName).isPresent())) {
                response.redirect(Routes.BASE);
                halt();
            }
            return new ModelAndView(ImmutableMap.of("name", uName), Pages.RECOMEND);
        }), new FreeMarkerEngine(conf));

        get(Constants.Routes.TABLES, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), Pages.TABLES);
        }, new FreeMarkerEngine(conf));

        get(Routes.ADMIN_LOGIN, ((request, response) -> {
            String uName = request.queryParams(Keys.UNAME);
            String pass = request.queryParams(Keys.PASS);
            if (Admin.getByUnameAndPass(uName, pass).isPresent()) {
                response.cookie("adminuname", uName);
                response.redirect(Routes.ADMIN_PROFILE);
                return response;
            } else {
                halt("Wrong login info");
                return response;
            }
        }));

        get(Routes.ADMIN_PROFILE, (request, response) -> {
            String uname = request.cookie("adminuname");
            if (uname == null) {
                halt(404, "go login first boi");
            }
            return new ModelAndView(ImmutableMap.of(), Pages.ADMIN_PROFILE);
        }, new FreeMarkerEngine(conf));

        get(Routes.ADMIN_STATS, (request, response) -> {
            String uname = request.cookie("adminuname");
            if (uname == null) {
                halt(404, "go login first boi");
            }
            return new ModelAndView(ImmutableMap.of(), Pages.ADMIN_STATS);
        }, new FreeMarkerEngine(conf));

        RestMapping.makeMap();

    }
}