package fi.tuni.tamk.tiko.bmb.blogbackend.controller;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RESTController {
    @Autowired
    BlogPostRepository blogPostRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/posts")
    @Transactional
    public Iterable<BlogPost> getPosts() {
        return blogPostRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/posts/{id}")
    @Transactional
    public Optional<BlogPost> getPost(@PathVariable long id) {
        return blogPostRepository.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/posts")
    @Transactional
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        blogPostRepository.save(b);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/posts/{id}")
    @Transactional
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost b, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/posts/{id}").buildAndExpand(b.getId()).toUri());
        return new ResponseEntity<>(blogPostRepository.save(b), headers, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/posts/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBlogPost(@PathVariable long id) {
        blogPostRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
