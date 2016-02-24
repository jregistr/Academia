package Dijks

import com.google.gson.JsonObject

class Step {

    private String url
    private double distance

    static constraints = {
    }

    public Step(String url, double distance){
        this.url = url
        this.distance = distance
    }

    public JsonObject toJsonObject(){
        JsonObject object = new JsonObject()
        object.addProperty("url",url)
        object.addProperty("distance",distance)
        object
    }

}
