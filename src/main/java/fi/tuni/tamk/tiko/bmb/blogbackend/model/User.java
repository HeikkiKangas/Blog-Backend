package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Every blog post has a user as an author.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    @JsonIgnore
    private String password;
    private boolean admin;

    public User() {}

    public User(@NotBlank String username, @NotBlank String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

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

    /**
     * Getter for password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     * @param password new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admin=" + admin +
                '}';
    }
}
