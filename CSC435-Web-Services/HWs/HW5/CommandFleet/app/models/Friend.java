package models;


import com.avaje.ebean.Expr;
import com.google.gson.JsonObject;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "FRIENDS")
public class Friend extends Model {

    @Column(name = "FriendOne")
    private String friendOne;

    @Column(name = "FriendTwo")
    private String friendTwo;

    public Friend(){

    }

    public Friend(String friendOne, String friendTwo) {
        this.friendOne = friendOne;
        this.friendTwo = friendTwo;
    }

    public String getFriendOne() {
        return friendOne;
    }

    public void setFriendOne(String friendOne) {
        this.friendOne = friendOne;
    }

    public String getFriendTwo() {
        return friendTwo;
    }

    public void setFriendTwo(String friendTwo) {
        this.friendTwo = friendTwo;
    }

    public static List<Friend> getFriendsForUser(String uName) {
        return new Finder<>(String.class, Friend.class).where().or(Expr.eq("FriendOne", uName), Expr.eq("FriendTwo", uName)).findList();
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FriendOne",this.getFriendOne());
        jsonObject.addProperty("FriendTwo",this.getFriendTwo());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Friend{" +"friendOne='" + friendOne + '\'' +", friendTwo='" + friendTwo + '\'' + '}';
    }
}
