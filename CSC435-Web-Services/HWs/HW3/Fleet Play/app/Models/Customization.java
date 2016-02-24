package Models;

import com.google.gson.JsonObject;
import play.db.jpa.JPA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import java.util.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Customization")
public class Customization extends Model{

    private String userName;
    private String profPic;
    private String backPic;

    public Customization(){

    }

    public Customization(String userName, String profPic, String backPic) {
        this.userName = userName;
        this.profPic = profPic;
        this.backPic = backPic;
        setTimeStamp(new Date());
    }

    @Column(name = "UserID")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "ProfPic")
    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    @Column(name = "BackPic")
    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public static Customization getForUser(Long id){
        try {
            return JPA.em().createQuery("from Customization c where c.userName = '" + id + "'", Customization.class).setMaxResults(1).getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserID",this.getUserName());
        jsonObject.addProperty("ProfPic",this.getProfPic());
        jsonObject.addProperty("BackPic",this.getBackPic());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Customization{" +
                "userID=" + userName +
                ", profPic='" + profPic + '\'' +
                ", backPic='" + backPic + '\'' + super.toString() + "}";
    }
}
