package com.jeff.miniflix.config;

import com.google.gson.JsonObject;
import com.jeff.miniflix.controllers.Welcome;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.*;

public class UrlMappings {
    public static void main(String[] args) {
        get("/", (request, response) -> {
            /*JsonObject object = new JsonObject();
            object.addProperty("Cool", "Jeff");
            response.body(object.toString());*/

            return new Welcome().get(request, response);
        }, new FreeMarkerEngine());

    }
}