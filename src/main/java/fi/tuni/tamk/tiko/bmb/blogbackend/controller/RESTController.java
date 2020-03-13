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
@RequestMapping("/api")
public class RESTController {
    private int counter = 0;

    @Autowired
    UserRepository userDB;

    @Autowired
    BlogPostRepository blogPostDB;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/posts")
    @Transactional
    public Iterable<BlogPost> getPosts() {
        return blogPostDB.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/posts/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostDB.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/posts")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostDB.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/posts/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostDB.save(b), headers, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/posts/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/generateposts")
    @Transactional
    public ResponseEntity<BlogPost> generateBlogPosts() {
        User u = userDB.findAll().iterator().next();
        counter++;

        for (int i = 0; i < 5; i++) {
            BlogPost post = new BlogPost();
            post.setAuthor(u);
            post.setTitle("Test Post #" + counter + i);
            post.setText(LoremIpsum.loremIpsum);
            post.setTimestamp("2020-03-09 15:07");
            post.setLikes(new ArrayList<>());
            post.setTags(new ArrayList<>());
            post.setComments(new ArrayList<>());
            blogPostDB.save(post);
        }

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
