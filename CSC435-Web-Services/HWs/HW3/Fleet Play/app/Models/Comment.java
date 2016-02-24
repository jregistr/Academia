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
@Table(name = "Comment")
public class Comment extends Model{

    private String poster;
    private String target;
    private String msg;

    public Comment(){

    }

    public Comment(String poster, String target, String msg) {
        this.poster = poster;
        this.target = target;
        this.msg = msg;
        setTimeStamp(new Date());
    }

    @Column(name = "Poster")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Column(name = "Target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Column(name = "Msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static List<Comment> getAllByUser(String uName){
        try {
            return JPA.em().createQuery("from Comment c where c.poster = '" +  uName + "'", Comment.class).getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    public static List<Comment> getAllToUser(String uName) {
        try {
            return JPA.em().createQuery("from Comment c where c.target = '" + uName + "'", Comment.class).getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Poster",this.getPoster());
        jsonObject.addProperty("Target",this.getTarget());
        jsonObject.addProperty("Msg",msg);
        jsonObject.addProperty("Time",this.getTimeStamp().toString());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "poster=" + poster +
                ", target=" + target +
                ", msg='" + msg + '\'' + super.toString() + "}";
    }
}
