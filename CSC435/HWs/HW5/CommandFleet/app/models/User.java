package models;

import com.google.gson.JsonObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends Base{

    @Column(name = "display")
    private String displayName;

    @Column(name = "password")
    private String password;

    public User(){

    }

    public User(String userName, String displayName, String password) {
        this.setUsername(userName);
        this.displayName = displayName;
        this.password = password;
        this.setTimestamp(new Date());
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public static List<User> getAll() {
        return new Finder<>(String.class, User.class).all();
    }

    @Transient
    public static User getByUserName(String userName) {
        return new Finder<>(String.class, User.class).byId(userName);
    }

    public static List<User> getLike(String like) {
        return new Finder<>(String.class, User.class).where().ilike("username", "%" + like + "%").findList();
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName",this.getUsername());
        jsonObject.addProperty("displayName", this.getDisplayName());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "User{username='" + this.getUsername() + '\''  + ", displayName='" + displayName + '\'' + ", password='" + password + '\'' + '}';
    }
}
