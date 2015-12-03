package models;

import com.google.gson.JsonObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CUSTOMIZATIONS")
public class Customization extends Base{

    public static final String DEFAULT_BACK = "http://i626.photobucket.com/albums/tt345/thedragonblade/introBack.png";
    public static final String DEFAULT_PROF = "http://i626.photobucket.com/albums/tt345/thedragonblade/guy1.png";

    @Column(name = "profpic")
    private String profPic;

    @Column(name = "backpic")
    private String backPic;

    public Customization(){

    }

    public Customization(String userName, String profPic, String backPic) {
        this.setUsername(userName);
        this.profPic = profPic;
        this.backPic = backPic;
        this.setTimestamp(new Date());
    }

    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public static Customization getForUser(String userName) {
        return new Finder<>(String.class, Customization.class).byId(userName);
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserID",this.getUsername());
        jsonObject.addProperty("ProfPic",this.getProfPic());
        jsonObject.addProperty("BackPic",this.getBackPic());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Customization{username='" + this.getUsername() + '\'' + ", profPic='" + profPic + '\'' + ", backPic='" + backPic + '\'' + '}';
    }
}
