package models;

import com.google.gson.JsonObject;
import play.api.libs.json.Json;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "COMMENTS")
public class Comment extends Model {

    @Column(name = "Poster")
    private String poster;

    @Column(name = "Target")
    private String target;

    @Column(name = "Timestamp")
    private Date timestamp;

    @Column(name = "Msg")
    private String msg;

    public Comment(){

    }

    public Comment(String poster, String target, String msg) {
        this.poster = poster;
        this.target = target;
        this.msg = msg;
        timestamp = new Date();
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Transient
    public static List<Comment> getAllToUser(String uName) {
        return new Finder<>(String.class, Comment.class).where().eq("Target", uName).findList();
    }

    public static List<Comment> getAllByUser(String uName) {
        return new Finder<>(String.class, Comment.class).where().eq("Poster", uName).findList();
    }

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Poster",this.getPoster());
        jsonObject.addProperty("Target",this.getTarget());
        jsonObject.addProperty("Msg",msg);
        jsonObject.addProperty("Time",this.getTimestamp().toString());
        return jsonObject.toString();
    }

   /// public JsonObject

    @Override
    public String toString() {
        return "Comment{" + "poster='" + poster + '\'' + ", target='" + target + '\'' + ", msg='" + msg + '\'' + '}';
    }
}
