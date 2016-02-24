package Models

import com.google.gson.JsonObject

abstract class Model {

    static constraints = {
    }

    //public abstract String toString()
    public abstract String toJson()
    public abstract JsonObject toJsonObject()

}
