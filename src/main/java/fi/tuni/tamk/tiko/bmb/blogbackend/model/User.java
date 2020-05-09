package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Every blog post has a user as an author.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private boolean admin;

    /**
     * Returns the id of user in long format.
     *
     * @return  id of user
     */
    public long getId() {
        return id;
    }

    /**
     * Assigns id to the user.
     *
     * @param id    id in long format
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user's chosen name in string format.
     *
     * @return  name of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Assigns a username to the user.
     *
     * @param userName  user's chosen name
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * If user is admin, returns true, if not, returns false.
     *
     * @return  true if user is admin, otherwise false
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Assigns true or false to user's admin status.
     *
     * @param admin true or false
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
