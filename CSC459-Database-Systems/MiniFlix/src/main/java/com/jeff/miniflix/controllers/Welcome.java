package com.jeff.miniflix.controllers;


import com.jeff.miniflix.controllers.base.Nav;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Welcome extends Nav {

    @Override
    public ModelAndView get(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "The title");
        return new ModelAndView(model, getClass().getClassLoader().
                getResource("htmls/templates/login.html.ftl").toString());
    }

    @Override
    public ModelAndView post(Request request, Response response) {
        return null;
    }
}
