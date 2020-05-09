package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Class represents a single comment in a blog posts.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long postID;
    private String author;
    private String text;

    private long likes;

    @CreationTimestamp
    private Date timestamp;

    /**
     * Returns the id of the comment in long format.
     *
     * @return  id of the comment
     */
    public long getId() {
        return id;
    }

    /**
     * Assigns an id to the comment.
     *
     * @param id    id for the comment
     */
    public void setId(long id) {
        this.id = id;
    }

    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the amount of likes a comment has.
     *
     * @return  amount of likes
     */
    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    /**
     * Adds another like to the comment
     * and returns the new amount.
     *
     * @return  new amount of likes
     */
    public long addLike() {
        return ++likes;
    }
}
