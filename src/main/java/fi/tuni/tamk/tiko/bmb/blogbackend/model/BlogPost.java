package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.CommentRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Represents a single blog post in the blogging site.
 */
@Entity
public class BlogPost {
    @Autowired
    @Transient
    BlogPostRepository blogPostDB;
    @Autowired
    @Transient
    CommentRepository commentDB;

    @CreationTimestamp
    private Date timestamp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User author;
    private String title;
    @Column(length = 10000)
    private String text;
    private long likes;
    @OneToMany
    private List<Comment> comments;
    @ElementCollection
    private List<String> tags;

    /**
     * Class constructor.
     */
    public BlogPost() {}

    /**
     * Returns the id of the blog post.
     *
     * @return  id of the blog post
     */
    public long getId() { return id; }

    /**
     * Assigns an id to the blog post.
     *
     * @param id    post id
     */
    public void setId(long id) { this.id = id; }

    public User getAuthor() { return author; }

    /**
     * Assigns an author to the blog post.
     * Author is given as a User object.
     *
     * @param author    User object
     */
    public void setAuthor(User author) { this.author = author; }

    public String getText() { return text; }

    /**
     * Assigns the written text to the blog post.
     *
     * @param text  the written blog post
     */
    public void setText(String text) { this.text = text; }

    public List<Comment> getComments() { return comments; }

    /**
     * Assigns a list of comments to the blog post.
     *
     * @param comments  list of Comment objects
     */
    public void setComments(List<Comment> comments) { this.comments = comments; }

    /**
     * Returns the amount of likes the blog post has.
     *
     * @return  amount of likes
     */
    public long getLikes() { return likes; }

    /**
     * Assigns wanted amount of likes to the blog post.
     *
     * @param likes amount of likes
     */
    public void setLikes(long likes) { this.likes = likes; }

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }

    public String getTitle() {
        return title;
    }

    /**
     * Assigns a title to the blog post.
     *
     * @param title chosen post title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Adds another like to the blog post and returns the amount of likes.
     *
     * @return  amount of likes
     */
    @Transactional
    public long addLike() {
        return ++likes;
    }

    /**
     * Adds new comment to the list of comments and returns the list.
     *
     * @param c comment for the post
     * @return  list of comments
     */
    @Transactional
    public List<Comment> addComment(Comment c) {
        comments.add(c);
        return comments;
    }
}
