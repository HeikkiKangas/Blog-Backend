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

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    BlogPostRepository blogPostDB;
    @Autowired
    CommentRepository commentDB;

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

    @GetMapping("/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostDB.findById(id);
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostDB.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/like")
    @Transactional
    public Map<String, Integer> addLike(@PathVariable long id) {
        Optional<BlogPost> b = blogPostDB.findById(id);
        b.ifPresent(post -> {
            post.addLike();
            blogPostDB.save(post);
        });
        return Collections.singletonMap("likes", b.map(BlogPost::getLikes).orElse(-1));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostDB.save(b), headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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
