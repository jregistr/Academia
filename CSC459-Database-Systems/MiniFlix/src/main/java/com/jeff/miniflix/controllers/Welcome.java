package com.jeff.miniflix.controllers;


import com.google.gson.JsonObject;
import com.jeff.miniflix.controllers.base.Nav;
import com.jeff.miniflix.models.Model;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Welcome extends Nav {

    @Override
    public ModelAndView get(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "The title");
       /* try {
            new Model(){

                @Override
                public JsonObject toJson() {
                    return null;
                }
            }.connect();
        }catch (SQLException s){
            s.printStackTrace();
        }*/
        return new ModelAndView(model, "htmls/templates/welcome.html.ftl");
    }

    @Override
    public ModelAndView post(Request request, Response response) {
        return null;
    }
}
