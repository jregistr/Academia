package Models

import com.google.gson.JsonObject

class Comment extends  Model{

    //static hasOne = [poster:User, target:User]

    //static belongsTo = [target:User]
    //static hasOne = [poster:User]
    //User target
   // User poster

    //static belongsTo = [target:User,poster:User]

   /* User target
    static hasOne = [poster:User]
    static belongsTo = [User]*/

   // static hasOne = [poster:User,target:User]

    User target
    User poster
    String msg
    Date timePosted

    static constraints = {
        msg blank: false
    }

    @Override
    JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.addProperty("Poster",poster.username)
        jsonObject.addProperty("Target",target.username)
        jsonObject.addProperty("Msg",msg)
        jsonObject.addProperty("Time",timePosted.toString())
        return jsonObject
    }

    @Override
    String toJson() {
        toJsonObject().toString()
    }

    @Override
    public String toString() {
        return "Comment{" + "poster=" + poster + ", target=" + target + ", msg='" + msg + '\'' + ", timePosted=" + timePosted + '}';
    }
}
