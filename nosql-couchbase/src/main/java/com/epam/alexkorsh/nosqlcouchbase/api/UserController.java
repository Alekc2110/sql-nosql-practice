package com.epam.alexkorsh.nosqlcouchbase.api;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/sport/{sportName}")
    public List<User> getUserBySportName(@PathVariable String sportName) {
        return userService.getUserBySportName(sportName);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/sport")
    public ResponseEntity<User> addSportToUser(@PathVariable String id, @RequestBody Sport sport) {
        User updatedUser = userService.addSportToUser(id, sport);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/search/user")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("q") String query) {
        List<User> foundUsersIds = userService.search(query);
        return ResponseEntity.ok(foundUsersIds);
    }

}
