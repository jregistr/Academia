package com.jeff.miniflix.controllers.base;

import spark.Request;
import spark.Response;

abstract class Controller {

    protected Object get(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

    protected Object post(Request request, Response response) {
        throw new UnsupportedOperationException();
    }

}
