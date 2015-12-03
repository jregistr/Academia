package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Base extends Model{

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "timestamp")
    private Date timestamp;

    public abstract String toJson();

    public abstract String toString();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
