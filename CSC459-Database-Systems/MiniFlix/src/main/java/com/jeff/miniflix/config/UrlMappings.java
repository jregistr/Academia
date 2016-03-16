package com.jeff.miniflix.config;

import com.jeff.miniflix.models.User;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Optional;

import static com.jeff.miniflix.config.Constants.Keys.KEY_PASS;
import static com.jeff.miniflix.config.Constants.Keys.KEY_UNAME;
import static com.jeff.miniflix.config.Constants.Routes.*;
import static spark.Spark.get;
import static spark.Spark.halt;

public class UrlMappings {


    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.setClassForTemplateLoading(UrlMappings.class, "/");

        get(ROUTE_BASE, (request, response) -> {
            return new ModelAndView(new HashMap<String, String>(), "htmls/templates/welcome.html.ftl");
        }, new FreeMarkerEngine(conf));

        get(ROUTE_LOGIN, (req, res) -> {
            String uName = req.queryParams(KEY_UNAME);
            String pass = req.queryParams(KEY_PASS);
            if (uName != null && pass != null) {
                Optional<User> user = User.getByUserName(uName);
                if (user.isPresent() && user.get().password.equals(pass)) {
                    res.cookie(KEY_UNAME, user.get().userName);
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
            if(uName == null || (!User.getByUserName(uName).isPresent())){
                response.redirect(ROUTE_BASE);
                halt();
            }
            return new ModelAndView(new HashMap<String, String>(), "htmls/templates/profile.html.ftl");
        }), new FreeMarkerEngine(conf));

    }
}