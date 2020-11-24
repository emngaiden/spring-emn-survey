package com.emnsoft.emnsurvey.controller;

import com.emnsoft.emnsurvey.domain.User;
import com.emnsoft.emnsurvey.repository.UserRepository;
import com.emnsoft.emnsurvey.utils.ResponseEntityUtils;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntityUtils.wrapOrNotFound(userRepository.findById(new ObjectId(id)));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //validations
        if (user.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User created = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
