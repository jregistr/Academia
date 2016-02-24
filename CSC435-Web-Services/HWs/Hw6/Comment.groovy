package Models

import com.google.gson.JsonObject

class Comment extends Model {

    static hasOne = [poster:User, target:User]

    String msg
    Date timePosted

    static constraints = {
        msg blank: false
    }

    @Override
    String toJson() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.addProperty("Poster",poster.username)
        jsonObject.addProperty("Target",target.username)
        jsonObject.addProperty("Msg",msg)
        jsonObject.addProperty("Time",timePosted.toString())
        return jsonObject.toString()
    }

    @Override
    public String toString() {
        return "Comment{" + "poster=" + poster + ", target=" + target + ", msg='" + msg + '\'' + ", timePosted=" + timePosted + '}';
    }
}
