package fi.tuni.tamk.tiko.bmb.blogbackend.controller;

import fi.tuni.tamk.tiko.bmb.blogbackend.model.BlogPost;
import fi.tuni.tamk.tiko.bmb.blogbackend.model.User;
import fi.tuni.tamk.tiko.bmb.blogbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final String CORS = "*";
    @Autowired
    UserRepository userDB;

    @CrossOrigin(origins = CORS)
    @GetMapping("/")
    @Transactional
    public Iterable<User> getUsers() {
        return userDB.findAll();
    }

    @CrossOrigin(origins = CORS)
    @PostMapping("/")
    @Transactional
    public ResponseEntity<User> addUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        userDB.save(u);
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(u, headers, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = CORS)
    @GetMapping("/{id}")
    @Transactional
    public Optional<User> getUser(@PathVariable long id) {
        return userDB.findById(id);
    }

    @CrossOrigin(origins = CORS)
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(userDB.save(u), headers, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = CORS)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
