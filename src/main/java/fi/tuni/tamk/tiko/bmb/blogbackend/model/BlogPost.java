package fi.tuni.tamk.tiko.bmb.blogbackend.model;

import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
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
    private String text;
    @OneToMany
    private List<Comment> comments;
    @ElementCollection
    private List<String> likes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    @Transactional
    public List<String> addLike(String userName) {
        likes.add(userName);
        blogPostDB.save(this);
        return likes;
    }
}
