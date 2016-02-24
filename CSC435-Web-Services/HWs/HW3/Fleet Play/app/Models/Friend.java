package Models;

import com.google.gson.JsonObject;
import play.db.jpa.JPA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Friend")
public class Friend extends Model{

    private String friendOne;
    private String friendTwo;

    public Friend(){

    }

    public Friend(String friendOne, String friendTwo) {
        this.friendOne = friendOne;
        this.friendTwo = friendTwo;
        setTimeStamp(new Date());
    }

    @Column(name = "FriendOne")
    public String getFriendOne() {
        return friendOne;
    }

    public void setFriendOne(String friendOne) {
        this.friendOne = friendOne;
    }

    @Column(name = "FriendTwo")
    public String getFriendTwo() {
        return friendTwo;
    }

    public void setFriendTwo(String friendTwo) {
        this.friendTwo = friendTwo;
    }

    public static List<Friend> getFriendsForUser(String uName){
        try {
            return JPA.em().createQuery("from Friend f where f.friendOne = '" + uName + "'" + " or f.friendTwo = '" + uName + "'", Friend.class).getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("FriendOne",this.getFriendOne());
        jsonObject.addProperty("FriendTwo",this.getFriendTwo());
        jsonObject.addProperty("Time",this.getTimeStamp().toString());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendOne=" + friendOne +
                ", friendTwo=" + friendTwo + super.toString() + "}";
    }
}
