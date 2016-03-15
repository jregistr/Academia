package com.jeff.miniflix.controllers.base;

import spark.Request;
import spark.Response;

abstract class Controller {

    public Object get(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    public Object post(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

}
