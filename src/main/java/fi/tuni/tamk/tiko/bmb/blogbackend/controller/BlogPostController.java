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
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    private final String CORS = "*";
    @Autowired
    BlogPostRepository blogPostDB;
    @Autowired
    CommentRepository commentDB;

    @CrossOrigin(origins = CORS)
    @GetMapping("")
    @Transactional
    public Iterable<BlogPost> getPosts() {
        return blogPostDB.findAll();
    }

    @CrossOrigin(origins = CORS)
    @GetMapping("/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostDB.findById(id);
    }

    @CrossOrigin(origins = CORS)
    @PostMapping("")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostDB.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = CORS)
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

    @CrossOrigin(origins = CORS)
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

    @CrossOrigin(origins = CORS)
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostDB.save(b), headers, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = CORS)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
