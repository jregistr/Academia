package Models

import com.google.gson.JsonObject

class User extends Model{

    String username
    String displayName
    String password

    static hasOne = [customization:Customization]
    static hasMany = [comments:Comment,posted:Comment,friends:User,blueBattles:Battle,redBattles:Battle]
    static mappedBy = [comments: 'target',posted: 'poster',blueBattles: 'blue',redBattles: 'red']

    public User(){

    }

    public User(String username,String displayname,String password){
        this.username = username
        this.displayName = displayname
        this.password = password
        customization = Customization.randomCustomization(this)
    }

    static constraints = {
        username size: 2..20, blank: false, unique: true
        displayName minSize: 5
        password blank: false
        customization nullable: true
    }

    @Override
    JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName",username);
        jsonObject.addProperty("displayName",displayName);
        return jsonObject
    }

    @Override
    String toJson() {
        toJsonObject().toString()
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' +", displayName='" + displayName + '\'' +", password='" + password + '\'' +'}';
    }
}
