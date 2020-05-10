package fi.tuni.tamk.tiko.bmb.blogbackend.controller;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.Comment;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Controls blog posts and comments and likes in blog posts.
 */
@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    BlogPostRepository blogPostDB;
    @Autowired
    CommentRepository commentDB;

    /**
     * Returns all blog posts.
     *
     * @return List of all blog posts in the database.
     */
    @GetMapping("")
    @Transactional
    public Iterable<BlogPost> getPosts() {
        List<BlogPost> list = new ArrayList<>();
        blogPostDB.findAll().forEach(p -> list.add(p));
        Collections.sort(list, sortById.reversed());
        return list;
    }

    /**
     * Comparator for sorting blog posts by id.
     */
    private Comparator<BlogPost> sortById = (BlogPost p1, BlogPost p2) ->
            Long.compare(p1.getId(), p2.getId());

    /**
     * Returns one blog post with given id.
     *
     * @param id Post's id.
     * @return blog post given id.
     */
    @GetMapping("/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostDB.findById(id);
    }

    /**
     * Adds blog post to database.
     * @param b Blog post which will be added.
     * @param uri Builds URL for new blog post.
     * @return ResponseEntity which contains blog post, headers and HTTP Status.
     */
    @PostMapping("")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostDB.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    /**
     * Adds comment to blog post.
     *
     * @param c Comment
     * @param id Id of the blog post in which the comment will be added.
     * @return The added comment or null.
     */
    @PostMapping("/{id}/comment")
    @Transactional
    public Optional<BlogPost> addComment(@RequestBody Comment c, @PathVariable long id) {
        System.out.println("Add comment, postId: " + id);
        System.out.println(c);
        Optional<BlogPost> b = blogPostDB.findById(id);
        b.ifPresent(post -> {
            c.setPostID(id);
            commentDB.save(c);
            post.addComment(c);
            blogPostDB.save(post);
            System.out.println("After saving:\n" + c);
            post.getComments().forEach(System.out::println);
        });
        return b;
    }

    /**
     * Adds like to blog post.
     *
     * @param id Id of the blog post in which the like will be added.
     * @return Returns the amount of likes.
     */
    @PostMapping("/{id}/like")
    @Transactional
    public Optional<BlogPost> addLike(@PathVariable long id) {
        Optional<BlogPost> b = blogPostDB.findById(id);
        b.ifPresent(post -> {
            post.addLike();
            blogPostDB.save(post);
        });
        return b;
    }

    /**
     * Updates blog post data with given data.
     * @param b The blog post which will be updated.
     * @return ResponseEntity which contains blog post and HTTP Status.
     */
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b) {
        BlogPost original = blogPostDB.findById(b.getId()).get();
        original.setTitle(b.getTitle());
        original.setText(b.getText());
        blogPostDB.save(original);
        return new ResponseEntity<>(original, HttpStatus.ACCEPTED);
    }

    /**
     * Deletes blog post from database.
     *
     * @param id The id of the blog post which will be deleted.
     * @return Returns response code 204 if successful.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Adds like to a comment.
     * @param postId id of the blog post that has the comment
     * @param commentId id of the comment
     * @return updated state of the affected blog post
     */
    @PostMapping("/{postId}/comment/{commentId}/like")
    @Transactional
    public Optional<BlogPost> addCommentLike(@PathVariable long postId, @PathVariable long commentId) {
        Optional<Comment> c = commentDB.findById(commentId);
        c.ifPresent(comment -> {
            comment.addLike();
            commentDB.save(comment);
        });
        return blogPostDB.findById(postId);
    }

    /**
     * Deletes comment from database.
     * @param postId id of the blog post
     * @param commentId id of the comment
     * @return updated state of the affected blog post
     */
    @DeleteMapping("/{postId}/comment/{commentId}")
    @Transactional
    public BlogPost deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        BlogPost post = blogPostDB.findById(postId).get();
        Comment comment = commentDB.findById(commentId).get();
        post.getComments().remove(comment);
        commentDB.delete(comment);
        return blogPostDB.save(post);
    }
}
