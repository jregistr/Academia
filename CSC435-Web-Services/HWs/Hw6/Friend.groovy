package Models

import com.google.gson.JsonObject

class Friend extends Model{

    static belongsTo = [user:User, friend:User]

    static constraints = {

    }

    @Override
    String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FriendOne",user.username);
        jsonObject.addProperty("FriendTwo",friend.username);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Friend{" + "friend=" + friend +", user=" + user +'}';
    }
}
