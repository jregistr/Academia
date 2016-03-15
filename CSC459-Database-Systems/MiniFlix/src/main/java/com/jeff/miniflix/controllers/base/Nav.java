package com.jeff.miniflix.controllers.base;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public abstract class Nav extends Controller{

    @Override
    protected abstract ModelAndView get(Request request, Response response);

    @Override
    protected abstract ModelAndView post(Request request, Response response);

}
