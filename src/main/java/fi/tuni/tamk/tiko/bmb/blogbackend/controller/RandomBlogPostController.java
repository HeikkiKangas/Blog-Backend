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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/generateposts")
public class RandomBlogPostController {
    @Autowired
    UserRepository userDB;

    @Autowired
    BlogPostRepository blogPostDB;

    private int counter = 0;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("")
    @Transactional
    public ResponseEntity<BlogPost> generateBlogPosts() {
        User u = userDB.findAll().iterator().next();
        counter++;

        for (int i = 0; i < 5; i++) {
            BlogPost post = new BlogPost();
            post.setAuthor(u);
            post.setTitle("Test Post #" + counter + i);
            post.setText(LoremIpsum.loremIpsum[0]);
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
