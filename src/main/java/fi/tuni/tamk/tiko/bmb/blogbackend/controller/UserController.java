package fi.tuni.tamk.tiko.bmb.blogbackend.controller;

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

/**
 *  Class controls the blog site users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userDB;

    /**
     * Calls the UserRepository and gets all users in the database.
     *
     * @return  list of all users
     */
    @GetMapping("/")
    @Transactional
    public Iterable<User> getUsers() {
        return userDB.findAll();
    }

    /**
     * Method adds a new user to the user repository.
     *
     * @param u     a User object
     * @param uri   a UriComponentsBuilder
     * @return      url to the created user
     */
    @PostMapping("/")
    @Transactional
    public ResponseEntity<User> addUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        userDB.save(u);
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(u, headers, HttpStatus.CREATED);
    }

    /**
     * Finds the user of the specified id in the database
     * and returns the user.
     *
     * @param id    id of the wanted user
     * @return      a User object
     */
    @GetMapping("/{id}")
    @Transactional
    public Optional<User> getUser(@PathVariable long id) {
        return userDB.findById(id);
    }

    /**
     * Method updates the user in the database
     * and return a url to the updated user.
     *
     * @param u     a User object
     * @param uri   a UriComponentsBuilder
     * @return      url to the updated user
     */
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(userDB.save(u), headers, HttpStatus.ACCEPTED);
    }

    /**
     * Deletes user with the specified id from the database.
     *
     * @param id    id of the user to be deleted
     * @return      no content if user was deleted successfully
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *  Logs user in if they match the username,
     *  otherwise returns an error code.
     *
     * @param userName  username used for login
     * @return          user matching the username or error code
     */
    @GetMapping("/login")
    public ResponseEntity<Optional<User>> loginUser(@RequestHeader("username") String userName) {
        return new ResponseEntity<>(userDB.findByUserName(userName), HttpStatus.OK);
    }
}
