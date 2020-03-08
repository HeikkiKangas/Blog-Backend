package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private boolean admin;
    private long likesGiven;
    private long likesReceived;
    private String avatarPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public long getLikesGiven() {
        return likesGiven;
    }

    public void setLikesGiven(long likesGiven) {
        this.likesGiven = likesGiven;
    }

    public long getLikesReceived() {
        return likesReceived;
    }

    public void setLikesReceived(long likesReceived) {
        this.likesReceived = likesReceived;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
