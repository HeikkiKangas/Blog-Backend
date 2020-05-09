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
        Collections.sort(list, compareByTimestamp.reversed());
        return list;
    }

    private Comparator<BlogPost> compareByTimestamp = (BlogPost p1, BlogPost p2) ->
            p1.getTimestamp().compareTo(p2.getTimestamp());

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
    public Comment addComment(@RequestBody Comment c, @PathVariable long id) {
        Optional<BlogPost> b = blogPostDB.findById(id);
        b.ifPresent(post -> {
            commentDB.save(c);
            post.addComment(c);
            blogPostDB.save(post);
        });
        return b.isPresent() ? c : null;
    }

    /**
     * Adds like to blog post.
     *
     * @param id Id of the blog post in which the like will be added.
     * @return Returns the amount of likes.
     */
    //@CrossOrigin(origins = CORS)
    @PostMapping("/{id}/like")
    @Transactional
    public Map<String, Long> addLike(@PathVariable long id) {
        Optional<BlogPost> b = blogPostDB.findById(id);
        b.ifPresent(post -> {
            post.addLike();
            blogPostDB.save(post);
        });
        return Collections.singletonMap("likes", b.map(BlogPost::getLikes).orElse((long) -1));
    }

    /**
     * Updates blog post data with given data.
     * @param b The blog post which will be updated.
     * @param uri Gives URL to updated blog post.
     * @return ResponseEntity which contains blog post, headers and HTTP Status.
     */
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostDB.save(b), headers, HttpStatus.ACCEPTED);
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
     *  Adds a like to a selected comment on a selected post.
     *
     * @param postId    id of the post
     * @param commentId id of the liked comment
     * @return          mapped likes
     */
    @PostMapping("/{postId}/comment/{commentId}/like")
    @Transactional
    public Map<String, Long> addCommentLike(@PathVariable long postId, @PathVariable long commentId) {
        Optional<Comment> c = commentDB.findById(commentId);
        c.ifPresent(comment -> {
            comment.addLike();
            commentDB.save(comment);
        });
        return Collections.singletonMap("likes", c.map(Comment::getLikes).orElse((long) -1));
    }

    /**
     *  Removes selected comment from selected post
     *  and returns http status code 204.
     *
     * @param postId        id of the post
     * @param commentId     id of the chosen comment
     * @return              http status 204
     */
    @DeleteMapping("/{postId}/comment/{commentId}")
    @Transactional
    public ResponseEntity<Void> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        BlogPost post = blogPostDB.findById(postId).get();
        Comment comment = commentDB.findById(commentId).get();
        post.getComments().remove(comment);
        commentDB.delete(comment);
        blogPostDB.save(post);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
