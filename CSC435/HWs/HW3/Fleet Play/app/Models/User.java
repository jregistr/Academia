package Models;
import com.google.gson.JsonObject;
import play.db.jpa.JPA;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "User")
public class User extends Model{

    private String userName;
    private String displayName;
    private String password;

    public User (){

    }

    public User(String userName, String displayName, String password) {
        this.userName = userName;
        this.displayName = displayName;
        this.password = password;
        setTimeStamp(new Date());
    }

    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public static List<User> getAll() {
        return JPA.em().createQuery("from User", User.class).getResultList();
    }

    @Transient
    public static User getByUserName(String uName){
        //String query = "from User u where u.name = '" + uName + "'";
        try {
            return JPA.em().createQuery("from User u where u.userName = '" + uName + "'", User.class).setMaxResults(1).getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Transient
    public static User getByID(Long id) {
        return JPA.em().getReference(User.class, id);
    }


    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName",this.getUserName());
        jsonObject.addProperty("displayName",this.getUserName());
        jsonObject.addProperty("id",this.getId());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "User{ userName='" + userName + '\'' + ", displayName='" + displayName + '\'' + ", password='" + password + '\''  + super.toString() + "}";
    }
}
