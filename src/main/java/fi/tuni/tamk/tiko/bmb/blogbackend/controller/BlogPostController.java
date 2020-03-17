package fi.tuni.tamk.tiko.bmb.blogbackend.controller;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.LoremIpsum;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.User;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    BlogPostRepository blogPostDB;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("")
    @Transactional
    public Iterable<BlogPost> getPosts() {
        return blogPostDB.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostDB.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostDB.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostDB.save(b), headers, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
