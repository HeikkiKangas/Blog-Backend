package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BlogPost {
    @Autowired
    @Transient
    BlogPostRepository blogPostDB;
    @Autowired
    @Transient
    CommentRepository commentDB;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User author;
    private String title;
    @Column(length = 10000)
    private String text;
    private String timestamp;
    @OneToMany
    private List<Comment> comments;
    @ElementCollection
    private List<String> likes;
    @ElementCollection
    private List<String> tags;

    public BlogPost() {}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }

    public List<String> getLikes() { return likes; }

    public void setLikes(List<String> likes) { this.likes = likes; }

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Transactional
    public List<String> addLike(String userName) {
        likes.add(userName);
        blogPostDB.save(this);
        return likes;
    }
}
