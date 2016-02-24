package Models;
import org.hibernate.annotations.GenericGenerator;
import play.db.jpa.JPA;
import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public abstract class Model {

    private Long id;
    private Date timeStamp;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TimeCreated")
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Transient
    public void save(){
        if(this.getId()==null)
            JPA.em().persist(this);
        else
            JPA.em().merge(this);
    }

    public abstract String toJson();

    @Override
    public String toString() {
        return "id=" + id + " timeStamp= " + timeStamp;
    }
}

