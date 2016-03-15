package com.jeff.miniflix.controllers.base;


import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public abstract class Rest extends Controller {

    @Override
    public final Object get(Request request, Response response) {
        response.type("application/json");
        return process(request).toString();
    }

    @Override
    public final Object post(Request request, Response response) {
        response.type("application/json");
        return process(request).toString();
    }

    protected abstract JsonObject process(Request request);

}
