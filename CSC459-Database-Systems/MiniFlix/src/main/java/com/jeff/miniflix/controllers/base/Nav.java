package com.jeff.miniflix.controllers.base;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public abstract class Nav extends Controller{

    @Override
    public abstract ModelAndView get(Request request, Response response);

    @Override
    public abstract ModelAndView post(Request request, Response response);

}
