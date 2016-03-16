package com.jeff.miniflix.config;

import com.google.gson.JsonObject;
import com.jeff.miniflix.controllers.Welcome;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.net.URL;

import static spark.Spark.*;

public class UrlMappings {
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.setClassForTemplateLoading(UrlMappings.class, "/");

        get("/", (request, response) -> {
            return new Welcome().get(request, response);
        }, new FreeMarkerEngine(conf));

    }
}